package com.example.android.hackudaipur.data;

import android.net.Uri;

import com.example.android.hackudaipur.BuildConfig;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = UserProvider.AUTHORITY, database = UserDatabase.class)
public class UserProvider {


    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;

    @TableEndpoint(table = UserDatabase.USERS)
    public static class Users {

        @ContentUri(
                path = "users",
                type = "vnd.android.cursor.dir/user",
                defaultSort = ListColumns.NAME + " ASC")
        public static final Uri URI_USERS = Uri.parse("content://" + AUTHORITY + "/users");

    }

    @TableEndpoint(table = UserDatabase.SYMPTOMS)
    public static class Symptoms{

        @ContentUri(
                path = "symptoms",
                type = "vnd.android.cursor.dir/symptom",
                defaultSort = SymptomColumns._ID + " ASC")
        public static final Uri URI_SYMPTOMS = Uri.parse("content://" + AUTHORITY + "/symptoms");

    }

}
