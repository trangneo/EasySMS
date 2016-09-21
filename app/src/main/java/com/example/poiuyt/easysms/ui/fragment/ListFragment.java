package com.example.poiuyt.easysms.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.model.MemberModel;
import com.example.poiuyt.easysms.ui.acitivity.MainActivity;
import com.example.poiuyt.easysms.ui.adapter.ListDataAdapter;

import java.util.List;

/**
 * Created by poiuyt on 8/4/16.
 */

public class ListFragment extends Fragment {

    Context context;
    public MainActivity.Callback callback;
    List<String> listLastMessage;
    List<MemberModel> listUser;
    String connection;
    private ListDataAdapter adapter;

    public ListFragment(Context context, List<MemberModel> listUser, List<String> listLastMessage, String connection, MainActivity.Callback callback) {
        this.context = context;
        this.listLastMessage = listLastMessage;
        this.listUser = listUser;
        this.connection = connection;
        this.callback = callback;
        adapter = new ListDataAdapter(context, listUser, listLastMessage, connection, callback);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_item, container, false);
        if(callback==null){
            Log.d("Trangfragment", "callback null");
        }
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
        return rv;
    }

    public void update() {
        adapter.notifyDataSetChanged();
    }
}

