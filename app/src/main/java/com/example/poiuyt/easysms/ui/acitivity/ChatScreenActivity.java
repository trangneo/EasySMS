package com.example.poiuyt.easysms.ui.acitivity;

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

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.data.ChatMessage;
import com.example.poiuyt.easysms.model.MemberModel;
import com.example.poiuyt.easysms.model.MessageModel;
import com.example.poiuyt.easysms.ui.adapter.MessageAdapter;
import com.example.poiuyt.easysms.util.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenActivity extends AfterSignInBaseActivity {

    public static final String TAG = ChatScreenActivity.class.getSimpleName();
    MessageAdapter chatAdapter;
    public static final int SENDER_STATUS = 0;
    public static final int RECIPIENT_STATUS = 1;
    String mRecipientUid;
    String mSenderUid;
    public ChildEventListener mMessageChatListener;
    Intent getUsersData;
    private Toolbar toolbar;
    ActionBar actionBar;
    TextView tvTitle;
    Firebase mFirebaseChat;
    MessageModel newMessage;
    RecyclerView mChatRecyclerView;
    MemberModel sender, your;
    String senderMessage;
    ChatMessage chatMessage;
    Button btnSend;
    EditText edtChat;
    List<MessageModel> emptyMessageChat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initToolbar();
        fillData();
        bindHandler();
    }

    private void bindHandler() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtChat.setText("");
            }
        });

    }

    private void fillData() {
        getUsersData = getIntent();
        sender = getUsersData.getParcelableExtra(Constants.SENDER);
        your = getUsersData.getParcelableExtra(Constants.YOUR);
        tvUserName.setText(your.getYourName());
        tvTitle.setText(sender.getUsername());
        sender.getmRecipientUid();
        emptyMessageChat = new ArrayList<MessageModel>();
        chatAdapter = new MessageAdapter(emptyMessageChat);
        mChatRecyclerView.setAdapter(chatAdapter);

    }


    public void initLayout() {
        getLayoutInflater().inflate(R.layout.chats_screen_activity, coordinatorLayout);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        mChatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        edtChat = (EditText) findViewById(R.id.chat_user_message);
        btnSend = (Button) findViewById(R.id.btnSend);
        edtChat = (EditText) findViewById(R.id.edtChat);
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        tvTitle.setGravity(Gravity.CENTER_VERTICAL);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        mMessageChatListener = mFirebaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    // Log.e(TAG, "A new chat was inserted");
                    newMessage = dataSnapshot.getValue(MessageModel.class);
                    if (newMessage.getSender().equals(mSenderUid)) {
                        newMessage.setRecipientOrSenderStatus(SENDER_STATUS);
                    } else {
                        newMessage.setRecipientOrSenderStatus(RECIPIENT_STATUS);
                    }
                    chatAdapter.refillAdapter(newMessage);
                    mChatRecyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "I am onPause");
    }

    @Override
    public void LogOut() {
        super.LogOut();
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
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

    public void sendMessageToFireChat(View sendButton) {
        senderMessage = edtChat.getText().toString();
        senderMessage = senderMessage.trim();

        if (senderMessage.isEmpty()) {
            Log.e(TAG, "send message");

            // Send message to firebase
            chatMessage = new ChatMessage(mSenderUid, mRecipientUid, senderMessage);
            mFirebaseChat.push().setValue(chatMessage);
            edtChat.setText("");
        }
    }

    @Override
    void setViewPager(int i) {
        super.setViewPager(i);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
