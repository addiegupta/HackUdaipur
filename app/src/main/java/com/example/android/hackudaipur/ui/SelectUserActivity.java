package com.example.android.hackudaipur.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.adapter.UserAdapter;
import com.example.android.hackudaipur.data.UserColumns;
import com.example.android.hackudaipur.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.hackudaipur.data.generated.UserProvider.AUTHORITY;

public class SelectUserActivity extends AppCompatActivity implements UserAdapter.UserOnClickHandler{

    @BindView(R.id.rv_select_user)
    RecyclerView mRVSelectUser;

    private UserAdapter mUserAdapter;
    private ArrayList<User> mUsersList;

    public static final Uri URI_USERS = Uri.parse("content://" + AUTHORITY + "/users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        ButterKnife.bind(this);

        mUsersList = new ArrayList<>();

        try {
            Cursor cursor = getContentResolver().query(URI_USERS, null, null, null, null);
            if (cursor != null) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns.NAME));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserColumns._ID));

                    mUsersList.add(new User(userName,id));

                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mUserAdapter = new UserAdapter(this,mUsersList,this);

        mRVSelectUser.setLayoutManager(new GridLayoutManager(this,2));
        mRVSelectUser.setAdapter(mUserAdapter);
        mRVSelectUser.setHasFixedSize(true);

    }

    @Override
    public void onClick(User selectedUser) {

        PreferenceManager.getDefaultSharedPreferences(SelectUserActivity.this).edit().putInt(getString(R.string.SELECTED_USER_ID), selectedUser.getmId()).apply();
        PreferenceManager.getDefaultSharedPreferences(SelectUserActivity.this).edit().putString(getString(R.string.SELECTED_USER_NAME), selectedUser.getName()).apply();
        finish();
        startActivity(new Intent(SelectUserActivity.this,SymptomsActivity.class));

    }
}
