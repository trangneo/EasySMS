package com.example.poiuyt.easysms.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.model.MessageModel;

import java.util.List;

/**
 * Created by poiuyt on 9/13/16.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MessageModel> mListOfFireChat;
    public static final int SENDER=0;
    public static final int RECIPIENT=1;

    public MessageAdapter(  List<MessageModel> listOfFireChats) {
        mListOfFireChat = listOfFireChats;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case SENDER:
                View viewSender = inflater.inflate(R.layout.sender_message, viewGroup, false);
                viewHolder= new ViewHolderSender(viewSender);
                break;
            case RECIPIENT:
                View viewRecipient = inflater.inflate(R.layout.recipient_message, viewGroup, false);
                viewHolder=new ViewHolderRecipient(viewRecipient);
                break;
            default:
                View viewSenderDefault = inflater.inflate(R.layout.sender_message, viewGroup, false);
                viewHolder= new ViewHolderSender(viewSenderDefault);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SENDER:
                ViewHolderSender viewHolderSender=(ViewHolderSender)holder;
                configureSenderView(viewHolderSender,position);
                break;
            case RECIPIENT:
                ViewHolderRecipient viewHolderRecipient=(ViewHolderRecipient)holder;
                configureRecipientView(viewHolderRecipient,position);
                break;
        }
    }

    public void configureSenderView(ViewHolderSender viewHolderSender, int position) {
        MessageModel senderFireMessage=mListOfFireChat.get(position);
        viewHolderSender.getSenderMessageTextView().setText(senderFireMessage.getMessage());
    }
    public void configureRecipientView(ViewHolderRecipient viewHolderRecipient, int position) {
        MessageModel recipientFireMessage=mListOfFireChat.get(position);
        viewHolderRecipient.getRecipientMessageTextView().setText(recipientFireMessage.getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        if(mListOfFireChat.get(position).getRecipientOrSenderStatus()==SENDER){
            return SENDER;
        }else {
            return RECIPIENT;
        }
    }

    @Override
    public int getItemCount() {
        return mListOfFireChat.size();
    }
    public void refillAdapter(MessageModel newFireChatMessage){

        mListOfFireChat.add(newFireChatMessage);
        notifyItemInserted(getItemCount()-1);
    }

    public void refillFirsTimeAdapter(List<MessageModel> newFireChatMessage){

        mListOfFireChat.clear();
        mListOfFireChat.addAll(newFireChatMessage);
        notifyItemInserted(getItemCount()-1);
    }

    public void cleanUp() {
        mListOfFireChat.clear();
    }
    /*-------------------------------*/

    public class ViewHolderSender extends RecyclerView.ViewHolder {

        public TextView mSenderMessageTextView;

        public ViewHolderSender(View itemView) {
            super(itemView);
            mSenderMessageTextView=(TextView)itemView.findViewById(R.id.senderMessage);
        }

        public TextView getSenderMessageTextView() {
            return mSenderMessageTextView;
        }

        public void setSenderMessageTextView(TextView senderMessage) {
            mSenderMessageTextView = senderMessage;
        }
    }
    public class ViewHolderRecipient extends RecyclerView.ViewHolder {

        public TextView mRecipientMessageTextView;

        public ViewHolderRecipient(View itemView) {
            super(itemView);
            mRecipientMessageTextView=(TextView)itemView.findViewById(R.id.recipientMessage);
        }

        public TextView getRecipientMessageTextView() {
            return mRecipientMessageTextView;
        }

        public void setRecipientMessageTextView(TextView recipientMessage) {
            mRecipientMessageTextView = recipientMessage;
        }
    }
}
