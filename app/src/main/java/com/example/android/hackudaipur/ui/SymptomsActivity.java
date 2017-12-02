package com.example.android.hackudaipur.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.adapter.SymptomAdapter;
import com.example.android.hackudaipur.data.SymptomColumns;
import com.example.android.hackudaipur.model.Symptom;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.android.hackudaipur.data.UserProvider.Symptoms.URI_SYMPTOMS;


public class SymptomsActivity extends AppCompatActivity {


    @BindView(R.id.rv_symptoms)
    RecyclerView mRVSymptoms;
    @BindView(R.id.btn_continue_diagnosis)
    Button mContinueButton;

    private ArrayList<Symptom> mSymptomsList;
    private SymptomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        ButterKnife.bind(this);


        String selectedUserName = getDefaultSharedPreferences(this).getString(getString(R.string.SELECTED_USER_NAME), "");
        setTitle("Hi " + selectedUserName + "!");

        mRVSymptoms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mSymptomsList = new ArrayList<>();

        Cursor cursor = getContentResolver().query(URI_SYMPTOMS, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (Locale.getDefault().getDisplayLanguage().equals("English")) {
                while (!cursor.isAfterLast()) {
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(SymptomColumns.NAME));
                    mSymptomsList.add(new Symptom(userName, false));
                    cursor.moveToNext();
                }
                cursor.close();
            } else {
                while (!cursor.isAfterLast()) {
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(SymptomColumns.NAME_HINDI));
                    mSymptomsList.add(new Symptom(userName, false));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

        mRVSymptoms.setHasFixedSize(true);

        mAdapter = new SymptomAdapter(mSymptomsList);

        mRVSymptoms.setAdapter(mAdapter);
        mContinueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences prefs = PreferenceManager.
                        getDefaultSharedPreferences(SymptomsActivity.this);
                prefs.edit()
                        .putInt(getString(R.string.sym1), booleanToInt(mAdapter.getSymptomList().get(0).isSelected()))
                        .putInt(getString(R.string.sym2), booleanToInt(mAdapter.getSymptomList().get(1).isSelected()))
                        .putInt(getString(R.string.sym3), booleanToInt(mAdapter.getSymptomList().get(2).isSelected()))
                        .putInt(getString(R.string.sym4), booleanToInt(mAdapter.getSymptomList().get(3).isSelected()))
                        .putInt(getString(R.string.sym5), booleanToInt(mAdapter.getSymptomList().get(4).isSelected()))
                        .putInt(getString(R.string.sym6), booleanToInt(mAdapter.getSymptomList().get(5).isSelected()))
                        .putInt(getString(R.string.sym7), booleanToInt(mAdapter.getSymptomList().get(6).isSelected()))
                        .putInt(getString(R.string.sym8), booleanToInt(mAdapter.getSymptomList().get(7).isSelected())).apply();

              showDiagnosisSelectionDialogBox();

            }
        });
    }

    private int booleanToInt(boolean bool) {
        return (bool) ? 1 : 0;
    }

    private void showDiagnosisSelectionDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.continue_to_diagnosis)
                .setMessage(R.string.continue_to_diagnosis_message)
                .setPositiveButton(R.string.conti, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        continueWithDiagnosis(true);
                    }
                })
                .setNegativeButton(R.string.skip_diagnosis, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        continueWithDiagnosis(false);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void continueWithDiagnosis(boolean diagnose) {
        finish();

        if (diagnose) {
            startActivity(new Intent(SymptomsActivity.this, DiagnosisActivity.class));
        } else {
            startActivity(new Intent(SymptomsActivity.this, PostDiagnosisActivity.class));
        }

    }
}
