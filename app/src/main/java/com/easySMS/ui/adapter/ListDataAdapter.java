package com.easySMS.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easySMS.R;
import com.easySMS.model.MemberModel;
import com.easySMS.ui.acitivity.MainActivity;
import com.easySMS.util.Constants;

import java.util.List;

/**
 * Created by poiuyt on 8/4/16.
 */
/* show data in MainActivity */
public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {

    public List<MemberModel> listUser;
    public List<String> listMessage;
    Context context;
    String connection, yourName, yourCreatedAt;
    MemberModel memberModel;
    public MainActivity.Callback callback;

    public ListDataAdapter(Context context, List<MemberModel> listUser, List<String> listMessage, String connection, MainActivity.Callback callback) {
        this.listMessage = listMessage;
        this.context = context;
        this.listUser = listUser;
        this.connection = connection;
        this.callback = callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        memberModel = listUser.get(position);
//        holder.lastMessage.setText(listMessage.get(position));
        holder.getUserName().setText(memberModel.getUsername());
        holder.item_icon.setBackgroundResource(R.drawable.brick);
        holder.getStatusConnection().setText(memberModel.getConnection());
        holder.connection.setText(connection);
        if (memberModel.getConnection() == null || !memberModel.getConnection().equals(Constants.ONLINE)) {
            holder.getStatusConnection().setText(Constants.OFFLINE);
            holder.getStatusConnection().setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.getStatusConnection().setTextColor(Color.parseColor("#00FF00"));
            holder.getStatusConnection().setText(Constants.ONLINE);
        }
        if (position == listUser.size() - 1) {
            if (context.getResources().getDisplayMetrics().heightPixels < (position + 1) * holder.itemView.getHeight()) {
                holder.divider.setVisibility(View.GONE);
            }
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.startChatScreen(listUser.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView, divider, itemView;
        public ImageView item_icon;
        public TextView userName, lastMessage, connection;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            item_icon = (ImageView) view.findViewById(R.id.avatar);
            userName = (TextView) view.findViewById(R.id.tvName);
            divider = (View) view.findViewById(R.id.divider);
            itemView = (View) view.findViewById(R.id.itemView);
            connection = (TextView) view.findViewById(R.id.connectionStatus);
            lastMessage = (TextView) view.findViewById(R.id.tvMess);
        }

        public TextView getUserName() {
            return userName;
        }


        public TextView getStatusConnection() {
            return connection;
        }

    }

}


