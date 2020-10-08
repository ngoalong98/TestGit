package com.example.loginapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginapp.R;
import com.example.loginapp.helpers.InputValidation;
import com.example.loginapp.models.User;
import com.example.loginapp.sql.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private Button btnRegister;
    private TextView txtLinkLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        getSupportActionBar().hide();
        initViews();
        initListener();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = findViewById( R.id.nestedScrollViewRegister );
        textInputLayoutName = findViewById( R.id.textInputLayoutName );
        textInputLayoutEmail = findViewById( R.id.textInputLayoutEmailRegister );
        textInputLayoutPassword = findViewById( R.id.textInputLayoutPasswordRegister );
        textInputLayoutConfirmPassword = findViewById( R.id.textInputLayoutConfirmPasswordRegister );
        textInputEditTextName = findViewById( R.id.textInputEditTextName );
        textInputEditTextEmail = findViewById( R.id.textInputEditTextEmailRegister );
        textInputEditTextPassword = findViewById( R.id.textInputEditTextPasswordRegister );
        textInputEditTextConfirmPassword = findViewById( R.id.textInputEditTextConfirmPasswordRegister );
        btnRegister = findViewById( R.id.btnRegister );
        txtLinkLogin = findViewById( R.id.txtLoginLink );
    }

    private void initListener() {
        btnRegister.setOnClickListener( this );
        txtLinkLogin.setOnClickListener( this );
    }

    private void initObjects() {
        inputValidation = new InputValidation( activity );
        databaseHelper = new DatabaseHelper( activity );
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                postDataToSQLite();
                break;
            case R.id.txtLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled( textInputEditTextName, textInputLayoutName, getString( R.string.error_message_name ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled( textInputEditTextEmail, textInputLayoutEmail, getString( R.string.error_message_email ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail( textInputEditTextEmail, textInputLayoutEmail, getString( R.string.error_message_email ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled( textInputEditTextPassword, textInputLayoutPassword, getString( R.string.error_message_password ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches( textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString( R.string.error_password_match ) )) {
            return;
        }

        if (!databaseHelper.checkUser( textInputEditTextEmail.getText().toString().trim() )) {

            user.setName( textInputEditTextName.getText().toString().trim() );
            user.setEmail( textInputEditTextEmail.getText().toString().trim() );
            user.setPassword( textInputEditTextPassword.getText().toString().trim() );

            databaseHelper.addUser( user );

            // Snack Bar to show success message that record saved successfully
            Snackbar.make( nestedScrollView, getString( R.string.success_message ), Snackbar.LENGTH_LONG ).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make( nestedScrollView, getString( R.string.error_email_exists ), Snackbar.LENGTH_LONG ).show();
        }


    }


    private void emptyInputEditText() {
        textInputEditTextName.setText( null );
        textInputEditTextEmail.setText( null );
        textInputEditTextPassword.setText( null );
        textInputEditTextConfirmPassword.setText( null );
    }

}
