package com.example.android.hackudaipur.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.Table;

import java.util.ArrayList;

@Database(version = UserDatabase.VERSION)
public final class UserDatabase {


    public static final int VERSION = 1;

    @Table(UserColumns.class)
    public static final String USERS = "users";

    @Table(SymptomColumns.class)
    public static final String SYMPTOMS = "symptoms";

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {

        ArrayList<String> mSymptomsList = new ArrayList<>();
        mSymptomsList.add("Headache");
        mSymptomsList.add("Coughing");
        mSymptomsList.add("Sneezing");
        mSymptomsList.add("Runny Nose");
        mSymptomsList.add("Body pain");
        mSymptomsList.add("Stomachache");
        mSymptomsList.add("Chest pain");
        mSymptomsList.add("Vomiting");
        ArrayList<String> mSymptomsHindiList = new ArrayList<>();

        mSymptomsHindiList.add("सरदर्द");
        mSymptomsHindiList.add("खाँसी");
        mSymptomsHindiList.add("छींक आना");
        mSymptomsHindiList.add("बहती नाक");
        mSymptomsHindiList.add("बदन दर्द");
        mSymptomsHindiList.add("पेट दर्द");
        mSymptomsHindiList.add("छाती में दर्द");
        mSymptomsHindiList.add("उल्टी");

        for (int i = 0; i < mSymptomsList.size(); i++) {

            String symptom = mSymptomsList.get(i);
            String hindiSymptom = mSymptomsHindiList.get(i);

            ContentValues values = new ContentValues();
            values.put(SymptomColumns.NAME, symptom);
            values.put(SymptomColumns.NAME_HINDI, hindiSymptom);
            db.insert(SYMPTOMS, null, values);
        }
    }
}
