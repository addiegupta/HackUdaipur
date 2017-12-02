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

    @Table(ListColumns.class)
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

        for (String symptom : mSymptomsList) {
            ContentValues values = new ContentValues();
            values.put(SymptomColumns.NAME, symptom);
            db.insert(SYMPTOMS, null, values);
        }
    }

}
