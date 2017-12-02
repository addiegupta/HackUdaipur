package com.example.android.hackudaipur.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.adapter.SymptomAdapter;
import com.example.android.hackudaipur.data.ListColumns;
import com.example.android.hackudaipur.model.Symptom;

import java.util.ArrayList;
import java.util.List;

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

        /*Cursor cursor = getContentResolver().query(URI_USERS, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(ListColumns.NAME));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ListColumns._ID));

                mSymptomsList.add(new Symptom(userName, false));
                cursor.moveToNext();
            }
            cursor.close();
        }*/
        Cursor cursor = getContentResolver().query(URI_USERS, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(ListColumns.NAME));
                mSymptomsList.add(new Symptom(userName, false));
                cursor.moveToNext();
            }
            cursor.close();
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRVSymptoms.setHasFixedSize(true);

        // create an Object for Adapter
        mAdapter = new SymptomAdapter(mSymptomsList);

        // set the adapter object to the Recyclerview
        mRVSymptoms.setAdapter(mAdapter);
        mShowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String secemp = "";
                List<Symptom> stList = ((SymptomAdapter) mAdapter).getSymptomList();

                for (int i = 0; i < stList.size(); i++) {
                    Symptom symptom = stList.get(i);
                    if (symptom.isSelected()) {

                        secemp = secemp + "\n" + symptom.getSymptomName();
                    }

                }

                Toast.makeText(SymptomsActivity.this,
                        "Selected Employees: \n" + secemp, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

//    @Override
//    public void onClick(Symptom selectedSymptom) {
//
////        selectedSymptom.setSelected();
////
////        viewHolder.chkSelected.setTag(symptomList.get(position));
////        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
////            public void onClick(View v) {
////                CheckBox cb = (CheckBox) v;
////                Symptom symptom = (Symptom) cb.getTag();
////
////                symptom.setSelected(cb.isChecked());
////                symptomList.get(pos).setSelected(cb.isChecked());
////
////                Toast.makeText(
////                        v.getContext(),
////                        "Selected Symptoms: " + cb.getText() + " is "
////                                + cb.isChecked(), Toast.LENGTH_LONG).show();
////            }
////        });
//    }
}
