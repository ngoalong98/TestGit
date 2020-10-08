package com.example.loginapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.models.User;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {
    private List<User> listUsers;

    public UsersRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.item_user_recycler, parent, false );
        return new UserViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.txtName.setText( listUsers.get( position ).getName() );
        holder.txtEmail.setText( listUsers.get( position ).getEmail() );
        holder.txtPassword.setText( listUsers.get( position ).getPassword() );
    }

    @Override
    public int getItemCount() {
        Log.v( UsersRecyclerAdapter.class.getSimpleName(), "" + listUsers );
        return listUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtEmail;
        public TextView txtPassword;

        public UserViewHolder(View view) {
            super( view );
            txtName = view.findViewById( R.id.txtName );
            txtEmail = view.findViewById( R.id.txtEmail );
            txtPassword = view.findViewById( R.id.txtPassword );
        }
    }
}
