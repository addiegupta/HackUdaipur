package com.example.android.hackudaipur.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = UserDatabase.VERSION)
public final class UserDatabase {


    public static final int VERSION = 1;

    @Table(ListColumns.class)
    public static final String USERS = "users";

    @Table(SymptomColumns.class)
    public static final String SYMPTOMS = "symptoms";

}
