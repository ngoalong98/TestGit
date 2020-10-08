package com.example.loginapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.loginapp.R;
import com.example.loginapp.adapters.UsersRecyclerAdapter;
import com.example.loginapp.models.User;
import com.example.loginapp.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {
    private AppCompatActivity activity = UsersListActivity.this;

    private TextView txtName;
    private RecyclerView recyclerViewUser;
    private List<User>listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_list );
        getSupportActionBar().setTitle( "" );
        initViews();
        initObject();
    }

    private void initViews() {
        txtName = findViewById( R.id.textViewName );
        recyclerViewUser = findViewById( R.id.recyclerViewUsers );
    }

    private void initObject(){
        listUsers = new ArrayList<>(  );
        usersRecyclerAdapter = new UsersRecyclerAdapter( listUsers );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerViewUser.setLayoutManager( mLayoutManager );
        recyclerViewUser.setItemAnimator( new DefaultItemAnimator() );
        recyclerViewUser.setHasFixedSize( true );
        recyclerViewUser.setAdapter( usersRecyclerAdapter );
        databaseHelper = new DatabaseHelper( activity );
        String emailFromIntent = getIntent().getStringExtra( "EMAIL" );
        txtName.setText( emailFromIntent );
        getDataFromSQLite();
    }

    private void getDataFromSQLite(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                listUsers.clear();
                listUsers.addAll( databaseHelper.getAllUser() );
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute( aVoid );
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute(  );
    }
}
