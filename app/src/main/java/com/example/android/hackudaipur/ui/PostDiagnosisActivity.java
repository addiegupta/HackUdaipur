package com.example.android.hackudaipur.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.hackudaipur.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDiagnosisActivity extends AppCompatActivity {

    private boolean sym1,sym2,sym3,sym4,sym5,sym6,sym7,sym8;
    private int pulse,temp;

    @BindView(R.id.tv_final_report)
    TextView mReportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_diagnosis);
        ButterKnife.bind(this);

        fetchUserSymptoms();
        fetchPulseTempDataFromSensor();
        generateResult();


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

    }

    private void fetchUserSymptoms() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sym1 = intToBoolean(prefs.getInt(getString(R.string.sym1), 0));
        sym2 = intToBoolean(prefs.getInt(getString(R.string.sym2), 0));
        sym3 = intToBoolean(prefs.getInt(getString(R.string.sym3), 0));
        sym4 = intToBoolean(prefs.getInt(getString(R.string.sym4), 0));
        sym5 = intToBoolean(prefs.getInt(getString(R.string.sym5), 0));
        sym6 = intToBoolean(prefs.getInt(getString(R.string.sym6), 0));
        sym7 = intToBoolean(prefs.getInt(getString(R.string.sym7), 0));
        sym8 = intToBoolean(prefs.getInt(getString(R.string.sym8), 0));

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
