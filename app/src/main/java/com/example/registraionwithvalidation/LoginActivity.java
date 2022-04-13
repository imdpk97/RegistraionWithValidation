package com.example.registraionwithvalidation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUserId, etPassword;
    Button btnlocation;
    private Button btnRegistration;
    private Button btnLogin;
    private Button btnViewAllUsers;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = DBHelper.getInstance(getApplicationContext());
        initView();
        setListener();
    }

    private void initView() {
        etUserId = findViewById(R.id.et_userid);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegistration = findViewById(R.id.btn_reg);
        btnViewAllUsers = findViewById(R.id.btn_allusers);
        btnlocation = findViewById(R.id.bt_location);

    }

    private void setListener() {
        btnViewAllUsers.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnlocation.setOnClickListener(this);
    }

    private void onLoginButtonClicked() {

        if (TextUtils.isEmpty(etPassword.getText().toString()) && TextUtils.isEmpty(etUserId.getText().toString())) {
            etUserId.setError("You must enter password to login!");
            etPassword.setError("You must enter username to login!");
        } else if (TextUtils.isEmpty((etUserId.getText().toString()))) {
            etUserId.setError("You must enter username to login!");
        } else if (TextUtils.isEmpty(etPassword.getText().toString()) && etPassword.toString().length() >= 8) {
            etPassword.setError("You must enter password to login!");
        } else {
            checkUser();
        }
    }

    private void checkUser() {
//        if (dbHelper.userDao().isUserExists(etUserId.getText().toString()) || dbHelper.userDao().isPasswordMatch(etPassword.getText().toString())) {
        User user = dbHelper.userDao().getUserDetail(etUserId.getText().toString().trim());
        if (user != null && user.getPassword().equals(etPassword.getText().toString().trim())) {
            Intent intent = new Intent(getApplicationContext(), ViewUserDetails.class);
            intent.putExtra(AppConstants.USER_ID, etUserId.getText().toString());
            startActivity(intent);
            etUserId.getText().clear();
            etPassword.getText().clear();
        } else {
            Toast.makeText(LoginActivity.this, "User does not Exist!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_allusers:
                showUserList();
                break;
            case R.id.btn_reg:
                openRegistrationActivity();
                break;
            case R.id.btn_login:
                onLoginButtonClicked();
                break;
            case R.id.bt_location:
                Intent intent = new Intent(LoginActivity.this, CurrentLocation.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void showUserList() {
        Intent intent = new Intent(LoginActivity.this, UserListActivity.class);
        startActivity(intent);
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}