package com.example.registraionwithvalidation;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private Spinner sp;
    private RadioGroup radioGroup;
    private EditText UserId;
    private EditText UserName;
    private EditText UserPassword;
    private EditText UserContact;
    private EditText UserAddress;
    private Button SubmitSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sp = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radiogroup);
        UserId = findViewById(R.id.userId);
        UserName = findViewById(R.id.userName);
        UserPassword = findViewById(R.id.userPassword);
        UserContact = findViewById(R.id.userContact);
        UserAddress = findViewById(R.id.userAddress);

        List age = new ArrayList<Integer>();
        age.add("Select");
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(5);

        SubmitSave = findViewById(R.id.btnSubmit);
        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = UserId.getText().toString();
                String name = UserName.getText().toString();
                String password = UserPassword.getText().toString();
                String contact = UserContact.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String gender = null;
                if (selectedRadioButton != null) {
                    gender = selectedRadioButton.getText().toString();
                }
                String age = spinner.getSelectedItem().toString();
                String address = UserAddress.getText().toString();

                if (userid.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Pleas create the userid", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Pleas fill the Name field", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty() && password.length() >= 8) {
                    Toast.makeText(RegistrationActivity.this, "Please fill the password field", Toast.LENGTH_SHORT).show();
                } else if (UserContact.getText().toString().length() < 10 || contact.length() > 13) {
                    Toast.makeText(RegistrationActivity.this, "Please enter " + "\n" + " valid phone number", Toast.LENGTH_SHORT).show();
                } else if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegistrationActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                } else if (gender == null) {
                    Toast.makeText(RegistrationActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                } else if (sp.getSelectedItem().toString().trim().equals("Pick one")) {
                    Toast.makeText(RegistrationActivity.this, "Select your age", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill the Address field", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
                    User existingUser = dbHelper.userDao().getUserDetail(userid);
                    if (existingUser == null) {
                        User user = new User();
                        user.setUserId(userid.trim());
                        user.setName(name);
                        user.setPassword(password.trim());
                        user.setContactNo(contact);
                        user.setGender(gender);
                        user.setAge(age);
                        user.setAddress(address);

                        dbHelper.userDao().insert(user);
                        Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this, userid + " already exist", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
