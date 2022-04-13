package com.example.registraionwithvalidation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserDetails extends AppCompatActivity {
    private EditText userIdEdt, userNameEdt, userPasswordEdt, userContactEdt, userRadiogroupEdt, userSpinnerEdt, userAddressEdt;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        // initializing all our variables.
        userIdEdt = findViewById(R.id.edtUserId);
        userNameEdt = findViewById(R.id.edtUserName);
        userPasswordEdt = findViewById(R.id.edtUserPassword);
        userContactEdt = findViewById(R.id.edtUserContact);
        userRadiogroupEdt = findViewById(R.id.edtUserRadiogroup);
        userSpinnerEdt = findViewById(R.id.edtUserSpinner);
        userAddressEdt = findViewById(R.id.edtUserAddress);
        btnUpdate = findViewById(R.id.btnUpdate);

        DBHelper dbHandler = DBHelper.getInstance(UpdateUserDetails.this);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(AppConstants.USER_ID);

        User user = dbHandler.userDao().getUserDetail(userId);


        userIdEdt.setText(user.getUserId());
        userNameEdt.setText(user.getName());
        userPasswordEdt.setText(user.getPassword());
        userContactEdt.setText(user.getContactNo());
        userAddressEdt.setText(user.getAddress());
        userRadiogroupEdt.setText(user.getGender());
        userSpinnerEdt.setText(user.getAge());
        if (userId != null) {
            Toast.makeText(getApplicationContext(), userId.toString(), Toast.LENGTH_SHORT).show();
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setUserId(userIdEdt.getText().toString());
                user.setName(userNameEdt.getText().toString());
                user.setPassword(userPasswordEdt.getText().toString());
                user.setContactNo(userContactEdt.getText().toString());
                user.setContactNo(userRadiogroupEdt.getText().toString());
                user.setGender(userSpinnerEdt.getText().toString());
                user.setAddress(userAddressEdt.getText().toString());

                dbHandler.userDao().updateData(user);


                Toast.makeText(UpdateUserDetails.this, "User Details Updated..", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ViewUserDetails.class);
                intent.putExtra(AppConstants.USER_ID, userIdEdt.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

}
