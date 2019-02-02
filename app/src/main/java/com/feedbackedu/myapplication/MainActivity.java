package com.feedbackedu.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.feedbackedu.myapplication.adapters.UserAdapter;
import com.feedbackedu.myapplication.model.User;
import com.feedbackedu.myapplication.utils.DatabaseHandler;
import com.feedbackedu.myapplication.utils.FileHandler;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final int RQ_IMAGE = 0x123;
    public UserAdapter mUserAdapter;
    public DatabaseHandler databaseHandler;
    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerview);
        mUserAdapter = new UserAdapter();
        databaseHandler = new DatabaseHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mUserAdapter);
        ArrayList<User> users = databaseHandler.getAllUser();
        mUserAdapter.setUsers(users);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        FileHandler.createAppDirs();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RQ_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RQ_IMAGE: {
                if (resultCode == RESULT_OK) {
                    String name = "User "+ mUserAdapter.getItemCount();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    String pic = FileHandler.savePicture(photo, name);
                    String passcode = genPasscode();
                    User u = new User(name, pic, passcode);
                    if (name != null & pic != null & passcode != null) {
                        if (databaseHandler.addUser(u)) {
                            mUserAdapter.addUser(u);
                        }
                    }
                }
            }
        }
    }

    public String genPasscode() {
        String s = UUID.randomUUID().toString();
        ArrayList<Integer> passcode = new ArrayList<>();
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int x = ((int) c[i]) % 10;
            if (!passcode.contains(x)){
                if (passcode.size() < 6)
                    passcode.add(x);
            }
        }

        String p = "";
        for (int i : passcode) {
            p += i + "";
        }
        return p;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
