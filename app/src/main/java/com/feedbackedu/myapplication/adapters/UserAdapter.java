package com.feedbackedu.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feedbackedu.myapplication.R;
import com.feedbackedu.myapplication.model.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

    private ArrayList<User> mUsers = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_user, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        User u = mUsers.get(i);
        holder.name.setText(u.getId());
        holder.passcode.setText(u.getPasscode());
        Picasso.get().load(new File(u.getPicture())).into(holder.photo);
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void addUser(User u) {
        this.mUsers.add(u);
        this.notifyItemInserted(mUsers.size());
//        notifyDataSetChanged();
    }

    public void setUsers(ArrayList<User> users) {
        this.mUsers = users;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView name, passcode;
        ImageView photo;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_tv);
            passcode = itemView.findViewById(R.id.passcode_tv);
            photo = itemView.findViewById(R.id.image_view);
        }
    }
}
