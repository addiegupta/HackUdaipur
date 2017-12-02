package com.example.android.hackudaipur.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.data.UserColumns;
import com.example.android.hackudaipur.data.UserProvider;
import com.example.android.hackudaipur.utils.QueryUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostDiagnosisActivity extends AppCompatActivity {

    private boolean sym1,sym2,sym3,sym4,sym5,sym6,sym7,sym8;
    private int int_sym1,int_sym2,int_sym3,int_sym4,int_sym5,int_sym6,int_sym7,int_sym8;
    private int pulse,temp;

    private static final String BASE_URL = "https://finalapo.herokuapp.com/";


    @BindView(R.id.tv_final_report)
    TextView mReportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_diagnosis);
        ButterKnife.bind(this);

        fetchPulseTempDataFromSensor();
        fetchUserSymptoms();
        generateResult();

    }

    private void sendDataToServer(){

        int selectedUserId = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.SELECTED_USER_ID),0);

        String aadhaar="";

        String selection = UserColumns._ID + "=?";
        String[] selectionArgs = {String.valueOf(selectedUserId)};

        Cursor cursor = getContentResolver().query(UserProvider.Users.URI_USERS,null, selection, selectionArgs,null);

        if (cursor!=null){
            cursor.moveToFirst();
            aadhaar = cursor.getString(cursor.getColumnIndexOrThrow(UserColumns.AADHAAR));
        }

        String url = BASE_URL +
            "treatments/new?s1=" + int_sym1
            + "&s2=" + int_sym2
            + "&s3=" + int_sym3
            + "&s4=" + int_sym4
            + "&s5=" + int_sym5
            + "&s6=" + int_sym6
            + "&s7=" + int_sym7
            + "&s8=" + int_sym8
            + "&pulse=" + pulse
            + "&temp=" + temp
            + "&aadharnumber=" + aadhaar;
        Log.d(PostDiagnosisActivity.class.getSimpleName(),url);
    RequestQueue queue = Volley.newRequestQueue(this);
        QueryUtils.addVolleyHttpRequest(queue, false, url);

    }

    private void generateResult(){


        String report;
        //Some of the cases have been given for now
        if (sym1 && sym5 && (sym3||sym4) && temp> 37){
            report = getString(R.string.paracetamol);
        }
        else if(sym7 && sym6 && pulse <80){
            report = getString(R.string.acidity);
        }
        else if(sym6 && sym8){
            report = getString(R.string.domperidole);
        }
        else if(sym7 && pulse>85){
            report = getString(R.string.severe);
        }
        else{
            report = getString(R.string.default_case);
        }

        mReportTextView.setText(report);
        sendDataToServer();

    }

    private void fetchUserSymptoms() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int_sym1 = prefs.getInt(getString(R.string.sym1), 0);
        int_sym2 = prefs.getInt(getString(R.string.sym2), 0);
        int_sym3 = prefs.getInt(getString(R.string.sym3), 0);
        int_sym4 = prefs.getInt(getString(R.string.sym4), 0);
        int_sym5 = prefs.getInt(getString(R.string.sym5), 0);
        int_sym6 = prefs.getInt(getString(R.string.sym6), 0);
        int_sym7 = prefs.getInt(getString(R.string.sym7), 0);
        int_sym8 = prefs.getInt(getString(R.string.sym8), 0);

       sym1 = intToBoolean(int_sym1);
       sym2 = intToBoolean(int_sym2);
       sym3 = intToBoolean(int_sym3);
       sym4 = intToBoolean(int_sym4);
       sym5 = intToBoolean(int_sym5);
       sym6 = intToBoolean(int_sym6);
       sym7 = intToBoolean(int_sym7);
       sym8 = intToBoolean(int_sym8);
    }

    private boolean intToBoolean(int bool) {
        return bool == 1;
    }

    private void fetchPulseTempDataFromSensor(){
        pulse = randInt(57,62);
        temp = randInt(35,37);

    }

    public static int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
