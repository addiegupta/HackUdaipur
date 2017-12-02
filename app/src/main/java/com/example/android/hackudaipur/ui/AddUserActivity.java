package com.example.android.hackudaipur.ui;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.data.UserColumns;
import com.example.android.hackudaipur.data.UserProvider;
import com.example.android.hackudaipur.utils.QueryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddUserActivity extends AppCompatActivity {


    @BindView(R.id.et_phone)
    EditText mPhoneEditText;
    @BindView(R.id.et_user_name)
    EditText mNameEditText;
    @BindView(R.id.radio_group_gender)
    RadioGroup mGenderRadioGroup;
    @BindView(R.id.btn_add_user)
    Button mAddUserButton;
    @BindView(R.id.et_age)
    EditText mAgeEditText;
    @BindView(R.id.et_weight)
    EditText mWeightEditText;
    @BindView(R.id.et_allergy)
    EditText mAllergiesEditText;
    @BindView(R.id.et_aadhaar)
    EditText mAadhaarEditText;

    private static final String BASE_URL = "https://finalapo.herokuapp.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        setTitle(R.string.create_a_user);
        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUserInDb();
            }
        });
    }

    private void insertUserInDb() {

        String name = mNameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString();
        boolean gender = (mGenderRadioGroup.getCheckedRadioButtonId() == R.id.rb_male);
        int age = Integer.valueOf(mAgeEditText.getText().toString().trim());
        int weight = Integer.valueOf(mWeightEditText.getText().toString().trim());
        String allergies = mAllergiesEditText.getText().toString().trim();
        String aadhaar = mAadhaarEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(UserColumns.NAME, name);
        values.put(UserColumns.PHONE, phone);
        values.put(UserColumns.GENDER, gender);
        values.put(UserColumns.AGE,age);
        values.put(UserColumns.WEIGHT,weight);
        values.put(UserColumns.ALLERGY,allergies);
        values.put(UserColumns.AADHAAR,aadhaar);
        Uri insertUri = getContentResolver().insert(UserProvider.Users.URI_USERS, values);

        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt(getString(R.string.SELECTED_USER_ID), Integer.valueOf(insertUri.getLastPathSegment())).apply();

        String url = BASE_URL +
                "patients/new?name=" + name
                + "&phonenumber=" + phone
                + "&gender=" + gender
                + "&age=" + age
                + "&weight=" + weight
                + "&allergy=" + allergies
                + "&aadharnumber=" + aadhaar;
        Log.d(AddUserActivity.class.getSimpleName(),url);
        RequestQueue queue = Volley.newRequestQueue(this);
        QueryUtils.addVolleyHttpRequest(queue, false, url);

        finish();
    }
}
