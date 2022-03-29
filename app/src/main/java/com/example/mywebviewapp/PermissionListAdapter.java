package com.example.mywebviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class PermissionListAdapter extends RecyclerView.Adapter<PermissionListAdapter.ViewHolder> {
    private List<PermissionData> listdata;

    // RecyclerView recyclerView;
    public PermissionListAdapter(List<PermissionData> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //final PermissionData myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getPermission());
        String upperString = String.valueOf(listdata.get(position).getisGranted()).substring(0, 1).toUpperCase() + String.valueOf(listdata.get(position).getisGranted()).substring(1).toLowerCase();
        holder.tv_status.setText( upperString);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView tv_status;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_status=(TextView) itemView.findViewById(R.id.tv_status);

        }
    }
}


