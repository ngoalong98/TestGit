package com.example.loginapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginapp.R;
import com.example.loginapp.helpers.InputValidation;
import com.example.loginapp.sql.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private Button btnLogin;
    private TextView txtLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {
        nestedScrollView = findViewById( R.id.nestedScrollView );
        textInputLayoutEmail = findViewById( R.id.textInputLayoutEmail );
        textInputLayoutPassword = findViewById( R.id.textInputLayoutPassword );
        textInputEditTextEmail = findViewById( R.id.textInputEditTextEmail );
        textInputEditTextPassword = findViewById( R.id.textInputEditTextPassword );
        btnLogin = findViewById( R.id.btnLogin );
        txtLinkRegister = findViewById( R.id.txtLinkRegister );
    }

    private void initListeners() {
        btnLogin.setOnClickListener( this );
        txtLinkRegister.setOnClickListener( this );
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper( activity );
        inputValidation = new InputValidation( activity );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                verifyFromSQLite();
                break;
            case R.id.txtLinkRegister:
                Intent intentRegister = new Intent( getApplicationContext(), RegisterActivity.class );
                startActivity( intentRegister );
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled( textInputEditTextEmail, textInputLayoutEmail,
                getString( R.string.error_message_email ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail,
                getString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled( textInputEditTextPassword, textInputLayoutPassword,
                getString( R.string.error_message_password ) )) {
            return;
        }
        if (databaseHelper.checkUser( textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim() )){
            Intent intentAccouts = new Intent( activity,UsersListActivity.class );
            intentAccouts.putExtra( "EMAIL",textInputEditTextEmail.getText().toString().trim() );
            emptyInputEditText();
            startActivity( intentAccouts );
        }
        else {
            Snackbar.make( nestedScrollView,getString( R.string.error_valid_email_password ),Snackbar.LENGTH_LONG ).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

}
