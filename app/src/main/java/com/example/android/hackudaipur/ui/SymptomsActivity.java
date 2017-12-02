package com.example.android.hackudaipur.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.hackudaipur.data.UserProvider.AUTHORITY;


public class SymptomsActivity extends AppCompatActivity {

    private static final Uri URI_USERS = Uri.parse("content://" + AUTHORITY + "/users");
    public static final Uri URI_SYMPTOMS = Uri.parse("content://" + AUTHORITY + "/symptoms");

    @BindView(R.id.rv_symptoms)
    RecyclerView mRVSymptoms;
    @BindView(R.id.btnShow)
    Button mShowButton;

    private ArrayList<Symptom> mSymptomsList;
    private SymptomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        ButterKnife.bind(this);

        String selectedUserName = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.SELECTED_USER_NAME), "");
        setTitle("Hi " + selectedUserName + "!");

        mRVSymptoms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mSymptomsList = new ArrayList<>();

        Cursor cursor = getContentResolver().query(URI_SYMPTOMS, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(SymptomColumns.NAME));
                mSymptomsList.add(new Symptom(userName, false));
                cursor.moveToNext();
            }
            cursor.close();
        }

        mRVSymptoms.setHasFixedSize(true);

        mAdapter = new SymptomAdapter(mSymptomsList);

        mRVSymptoms.setAdapter(mAdapter);
        mShowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //TODO Change symptoms selection mechanism and place it in a method
                /*
                String selectedSymptoms = "";
                List<Symptom> stList = ((SymptomAdapter) mAdapter).getSymptomList();

                for (int i = 0; i < stList.size(); i++) {
                    Symptom symptom = stList.get(i);
                    if (symptom.isSelected()) {

                        selectedSymptoms += "\n" + symptom.getSymptomName();
                    }

                }*/
                showDiagnosisSelectionDialogBox();

            }
        });
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
        //TODO Put symptoms data

        if (diagnose) {
            startActivity(new Intent(SymptomsActivity.this, DiagnosisActivity.class));
        } else {
            startActivity(new Intent(SymptomsActivity.this, PostDiagnosisActivity.class));
        }

    }
}
