package com.example.poiuyt.easysms.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.model.MemberModel;
import com.example.poiuyt.easysms.ui.fragment.ListFragment;
import com.example.poiuyt.easysms.util.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class MainActivity extends AfterSignInBaseActivity {
    static final String TAG = "Trang";
    ViewPager viewPager;
    TabLayout tabLayout;
    PagerAdapter adapter;
    int[] imageResId = {R.mipmap.group, R.mipmap.friend, R.mipmap.chat,};

    Firebase mFirebaseChatRef;  /* Reference to firebase  root */
    FirebaseUser firebaseUser; /* Reference to users in firebase   getcurrentuser*/
    Firebase mFireChatUsersRef;/* lay danh sach user        root.user*/
    Firebase myConnectionsStatusRef; /* Updating connection status */
    public Firebase.AuthStateListener mAuthStateListener;  /* Listener for Firebase session changes */
    AuthData mAuthData;     /* Data from the authenticated user */
    String yourId, yourName, yourEmail, yourCreate;
    ChildEventListener mListenerUsers;
    ValueEventListener mConnectedListener;
    List<String> mUsersKeyList = new ArrayList<String>();
    List<MemberModel> listUser = new ArrayList<MemberModel>();
    MemberModel your;
    public Callback callback = new Callback() {
        @Override
        public void startChatScreen(MemberModel sender) {
            Intent intent = new Intent(MainActivity.this, ChatScreenActivity.class);
            intent.putExtra(Constants.YOUR, your);
            intent.putExtra(Constants.SENDER, sender);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    void initLayout() {
        getLayoutInflater().inflate(R.layout.activity_list_fragment, coordinatorLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        yourId = firebaseUser.getUid();

        yourEmail = firebaseUser.getEmail();
        yourName = firebaseUser.getDisplayName();
        Log.d(TAG + "user", yourName + "----" + yourEmail + "--" + yourId);
        tvUserName.setText(yourName);
        mFirebaseChatRef = new Firebase(Constants.URL);
        mFireChatUsersRef = new Firebase(Constants.URL).child(Constants.USER);
        listUser = new ArrayList<MemberModel>();
        mUsersKeyList = new ArrayList<String>();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        queryFireChatUsers();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
        showProgressDialog();
    }

    void setAuthenticatedUser(AuthData authData) {
        if (authData == null) {
            Log.e(TAG, "authData null ");
            navigateToLogin();
        }
    }

    void queryFireChatUsers() {
        Query query = mFireChatUsersRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    String userId = dataSnapshot.getKey();
                    if (!userId.equals(yourId)) {
                        MemberModel user = dataSnapshot.getValue(MemberModel.class);
                        user.setmRecipientUid(userId);
                        mUsersKeyList.add(user.getYourName());
                        listUser.add(user);
                        adapter.update();
                    } else {
                        your = dataSnapshot.getValue(MemberModel.class);
//                        your.setCreated("");
//                        your.setEmail("");
//                        your.setUsername("");
//                        your.setPassword("");
//                        your.setmRecipientUid("");
                        your.setConnection("online");
                        your.setYourEmail(yourEmail);
                        your.setYourName(yourName);
                        your.setYourId(yourId);
                        your.setYourCreatedAt(your.getCreated());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    String userUid = dataSnapshot.getKey();
                    if (!userUid.equals(yourId)) {
                        MemberModel user = dataSnapshot.getValue(MemberModel.class);
                        user.setmRecipientUid(userUid);
                        user.setYourEmail(yourEmail);
                        user.setYourId(yourId);
                        int index = mUsersKeyList.indexOf(userUid);
                        listUser.add(index, user);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgressDialog();
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        myConnectionsStatusRef = mFireChatUsersRef.child(yourId).child(Constants.CONNECTION);

        // Indication of connection status
        mConnectedListener = mFirebaseChatRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    myConnectionsStatusRef.setValue(Constants.ONLINE);
                    myConnectionsStatusRef.onDisconnect().setValue(Constants.OFFLINE);
                } else {
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    void navigateToLogin() {
        Log.e(TAG, "navigate toLogin");
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "I am onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "I am onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "I am onDestroy");
        mUsersKeyList.clear();

        // Stop all listeners
        // Make sure to check if they have been initialized
        if (mListenerUsers != null) {
            mFireChatUsersRef.removeEventListener(mListenerUsers);
        }
        if (mConnectedListener != null) {
            mFirebaseChatRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        }
    }

    @Override
    public void LogOut() {
        if (this.mAuthData != null) {
            myConnectionsStatusRef.setValue(Constants.OFFLINE);
            mFirebaseChatRef.unauth();
            setAuthenticatedUser(null);
        }
        Log.d(TAG + "user2", yourName + "----" + yourEmail + "--" + yourId);
        Log.e("Trang", "Log out");
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);

    }

    void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListFragment(getApplicationContext(), listUser, mUsersKeyList, "online", callback));
        adapter.addFragment(new ListFragment(getApplicationContext(), listUser, mUsersKeyList, "online", callback));
        adapter.addFragment(new ListFragment(getApplicationContext(), listUser, mUsersKeyList, "online", callback));
        viewPager.setAdapter(adapter);
    }


    static class PagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragments = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void update() {
            for (int i = 0; i < getCount(); i++) {
                ((ListFragment) getItem(i)).update();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int index = intent.getIntExtra("index", 0);
        setViewPager(index);
    }

    @Override
    void setViewPager(int i) {
        super.setViewPager(i);
        viewPager.setCurrentItem(i);
    }

    public interface Callback {
        void startChatScreen(MemberModel sender);
    }

    @Override
    public void onBackPressed() {
    }
}
