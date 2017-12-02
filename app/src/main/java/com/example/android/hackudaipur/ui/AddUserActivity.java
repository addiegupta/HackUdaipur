package com.example.android.hackudaipur.ui;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.hackudaipur.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.hackudaipur.data.UserProvider.AUTHORITY;


public class AddUserActivity extends AppCompatActivity {


    @BindView(R.id.et_phone)
    EditText mPhoneEditText;
    @BindView(R.id.et_user_name)
    EditText mNameEditText;
    @BindView(R.id.radio_group_gender)
    RadioGroup mGenderRadioGroup;
    @BindView(R.id.btn_add_user)
    Button mAddUserButton;

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_GENDER = "gender";
    public static final Uri URI_USERS = Uri.parse("content://" + AUTHORITY + "/users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUserInDb();
            }
        });
    }

    private void insertUserInDb() {

        //TODO Create error handling
        String name = mNameEditText.getText().toString().trim();
        long phone = Long.valueOf(mPhoneEditText.getText().toString());
        boolean gender = (mGenderRadioGroup.getCheckedRadioButtonId() == R.id.rb_male);

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE, phone);
        values.put(KEY_GENDER, gender);
        Uri insertUri = getContentResolver().insert(URI_USERS, values);
        Toast.makeText(this, name + " inserted at " + insertUri.getPath(), Toast.LENGTH_SHORT).show();

        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt(getString(R.string.SELECTED_USER_ID), Integer.valueOf(insertUri.getLastPathSegment())).apply();

        finish();

    }


}
