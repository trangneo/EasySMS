package com.easySMS.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easySMS.R;
import com.easySMS.model.MemberModel;
import com.easySMS.model.Message;
import com.easySMS.model.MessageModel;
import com.easySMS.ui.adapter.MessageAdapter;
import com.easySMS.util.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenActivity extends AfterSignInBaseActivity {

    private static final String TAG = ChatScreenActivity.class.getSimpleName();
    MessageAdapter chatAdapter;
    Intent getUsersData;
    ActionBar actionBar;
    TextView tvTitle;
    Firebase mFirebaseChat;
    RecyclerView mChatRecyclerView;
    MemberModel sender, your;
    String senderMessage;
    Button btnSend;
    EditText edtChat;
    private ChildEventListener mMessageChatListener;
    private Toolbar toolbar;
    private List<Message> listMessage;
    LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initToolbar();
        fillData();
        bindHandler();
//        overridePendingTransition(R.anim.visi_in, R.anim.visi_out);
    }

    private void bindHandler() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageToFireChat();
            }
        });
    }

    private void fillData() {
        getUsersData = getIntent();
        sender = getUsersData.getParcelableExtra(Constants.SENDER);
        your = getUsersData.getParcelableExtra(Constants.YOUR);
        tvUserName.setText(your.getUsername());
        tvTitle.setText(sender.getUsername());
        listMessage = new ArrayList<>();
        chatAdapter = new MessageAdapter(listMessage, your.getEmail());
        mChatRecyclerView.setAdapter(chatAdapter);
        mFirebaseChat = new Firebase(Constants.URL).child(Constants.CHAT).child(makeKeyConversation(sender, your));
        addData();
    }

    private void addData() {
        mMessageChatListener = mFirebaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    MessageModel neww = dataSnapshot.getValue(MessageModel.class);
                    Message message = new Message(neww.sender, neww.message, neww.recipient);
                    chatAdapter.refillAdapter(message);
                    mChatRecyclerView.scrollToPosition(listMessage.size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


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

    }


    public void initLayout() {
        getLayoutInflater().inflate(R.layout.chats_screen_activity, coordinatorLayout);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        mChatRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        mChatRecyclerView.setLayoutManager(linearLayoutManager);
        btnSend = (Button) findViewById(R.id.btnSend);
        edtChat = (EditText) findViewById(R.id.edtChat);

    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        tvTitle.setGravity(Gravity.CENTER);

    }

    public String makeKeyConversation(MemberModel a, MemberModel b) {
        return a.createdAtYours() > b.createdAtYours() ? (a.createdAtYours() + "-" + b.createdAtYours()) : (b.createdAtYours() + "-" + a.createdAtYours());
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearLayoutManager.setStackFromEnd(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.e(TAG, " I am onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, " I am onStart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "I am onPause");
    }

    @Override
    public void signOut() {
        super.signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.visi_in, R.anim.visi_out);

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "I am onStop");

        // Remove listener
        if (mMessageChatListener != null) {
            // Remove listener
            mFirebaseChat.removeEventListener(mMessageChatListener);
        }
        // Clean chat message
        chatAdapter.cleanUp();
    }

    public void sendMessageToFireChat() {
        senderMessage = edtChat.getText().toString();
        senderMessage = senderMessage.trim();

        if (!senderMessage.isEmpty()) {
            Message message = new Message(your.getEmail(), senderMessage, sender.getEmail());
            mFirebaseChat.push().setValue(message);
            edtChat.setText("");
        }
    }

    @Override
    void setViewPager(int i) {
        super.setViewPager(i);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
        overridePendingTransition(R.anim.visi_in, R.anim.visi_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChatScreenActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.visi_in, R.anim.visi_out);

    }
}
