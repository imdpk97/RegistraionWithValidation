package com.example.registraionwithvalidation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registraionwithvalidation.databinding.ActivityViewuserdatailsBinding;

public class ViewUserDetails extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ActivityViewuserdatailsBinding binding;
    private Context context;
    //private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewuserdatails);

        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        context = ViewUserDetails.this;

        Intent intent = getIntent();
        String userId = intent.getStringExtra(AppConstants.USER_ID);
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        User user = dbHelper.userDao().getUserDetail(userId);

        binding.setUser(user);


    }
}