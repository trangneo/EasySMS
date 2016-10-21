package com.easySMS.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.easySMS.R;
import com.easySMS.model.Message;

/**
 * Created by poiuyt on 9/13/16.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Message> mListOfFireChat;
    public static final int SENDER = 0;
    public static final int RECIPIENT = 1;
    String yourEmail;
    RecyclerView.ViewHolder viewHolder;
    static String TAG = MessageAdapter.class.getSimpleName();

    public MessageAdapter(List<Message> listOfFireChats, String yourEmail) {
        mListOfFireChat = listOfFireChats;
        this.yourEmail = yourEmail;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        Log.d(TAG + "email", yourEmail);
        switch (viewType) {
            case SENDER:
                View viewSender = inflater.inflate(R.layout.sender_message, viewGroup, false);
                viewHolder = new ViewHolderSender(viewSender);
                break;
            case RECIPIENT:
                View viewRecipient = inflater.inflate(R.layout.recipient_message, viewGroup, false);
                viewHolder = new ViewHolderRecipient(viewRecipient);
                break;
            default:
                View viewSenderDefault = inflater.inflate(R.layout.recipient_message, viewGroup, false);
                viewHolder = new ViewHolderSender(viewSenderDefault);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SENDER:
                ViewHolderSender viewHolderSender = (ViewHolderSender) holder;
                configureSenderView(viewHolderSender, position);
                break;
            case RECIPIENT:
                ViewHolderRecipient viewHolderRecipient = (ViewHolderRecipient) holder;
                configureRecipientView(viewHolderRecipient, position);
                break;
        }
    }

    public void configureSenderView(ViewHolderSender viewHolderSender, int position) {
        Message senderFireMessage = mListOfFireChat.get(position);
        viewHolderSender.mSenderMessageTextView.setText(senderFireMessage.getMessage());
    }

    public void configureRecipientView(ViewHolderRecipient viewHolderRecipient, int position) {
        Message recipientFireMessage = mListOfFireChat.get(position);
        viewHolderRecipient.mRecipientMessageTextView.setText(recipientFireMessage.getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG + "position", position + ""+mListOfFireChat.get(position).getMessage());
        if (mListOfFireChat.get(position).getSender() == null) {
            Log.d(TAG + "position", position + "null");
            return RECIPIENT;
        } else {
            if (mListOfFireChat.get(position).getSender().equals(yourEmail.toString())) {
                return SENDER;
            } else
                return RECIPIENT;
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG + "size", mListOfFireChat.size() + "");
        return mListOfFireChat.size();
    }

    public void refillAdapter(Message newFireChatMessage) {
        mListOfFireChat.add(newFireChatMessage);
        notifyItemInserted(mListOfFireChat.size()-1);
        notifyDataSetChanged();
    }

    public void cleanUp() {
        mListOfFireChat.clear();
    }
    /*-------------------------------*/

    public class ViewHolderSender extends RecyclerView.ViewHolder {

        TextView mSenderMessageTextView;

        public ViewHolderSender(View itemView) {
            super(itemView);
            mSenderMessageTextView = (TextView) itemView.findViewById(R.id.senderMessage);
        }

    }

    public class ViewHolderRecipient extends RecyclerView.ViewHolder {

        TextView mRecipientMessageTextView;

        public ViewHolderRecipient(View itemView) {
            super(itemView);
            mRecipientMessageTextView = (TextView) itemView.findViewById(R.id.recipientMessage);
        }
    }
}
