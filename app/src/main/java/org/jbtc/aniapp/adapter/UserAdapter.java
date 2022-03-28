package org.jbtc.aniapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jbtc.aniapp.R;
import org.jbtc.aniapp.model.Anime;
import org.jbtc.aniapp.model.User;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> items = new ArrayList<>();
    private User item= new User();
    private Context context;
    public void setItems(List<User> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public void setItem(User item) {
        this.item = item;
        notifyDataSetChanged();
    }
    public UserAdapter(Context context){
    this.context= context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_user,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(v);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder: se ejecuto el on bind");
        holder.name.setText( items.get(position).getUsername());
        int roleValue=items.get(position).getRole();
        if(roleValue==0){
        holder.role.setText("BASIC");}
        if(roleValue==1){
            holder.role.setText("MODERATOR");}
        if(roleValue==2){
            holder.role.setText("ADMINISTRATOR");}

        int genderValue=items.get(position).getGender();
        if(genderValue==0){
        holder.gender.setText("UNKNOWN");}
        if(genderValue==1){
            holder.gender.setText("MALE");}
        if(genderValue==2){
            holder.gender.setText("FEMALE");}
        if (items.get(position).getAvatar() != null) {

            //new UserViewHolder.DownLoadImageTask(holder.avatar).execute(items.get(position).getAvatar());
             Glide.with(context).load(items.get(position).getAvatar()).into(holder.avatar);
            //Picasso.get().load(items.get(position).getAvatar()).into(holder.avatar);
        }
        else {
           holder.avatar.setImageResource(R.drawable.ic_user);
                /* items.get(position).setAvatar("https://flyclipart.com/thumb2/avatar-business-businessman-male-man-silhouette-user-icon-19795.png");
            new UserViewHolder.DownLoadImageTask(holder.avatar).execute(items.get(position).getAvatar());*/
        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView role;
        TextView gender;
        ShapeableImageView avatar;

        public UserViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.cv_user_name);
            role= itemView.findViewById(R.id.cv_user_role);
            gender= itemView.findViewById(R.id.cv_user_gender);
            avatar= itemView.findViewById(R.id.cv_user_avatar);

        }





    }
}