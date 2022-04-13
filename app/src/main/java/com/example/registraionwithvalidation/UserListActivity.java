package com.example.registraionwithvalidation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView rvUserList;
    private ArrayList<String> user = new ArrayList<>();
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcl_view);
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        List<User> user = dbHelper.userDao().getAllUserDetail();
        userAdapter = new UserAdapter(this, user);
        rvUserList = (RecyclerView) findViewById(R.id.rvUserList);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        rvUserList.setAdapter(userAdapter);

    }
}