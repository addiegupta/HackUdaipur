package com.example.android.hackudaipur.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface UserColumns {

    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT)
    String NAME = "name";

    @DataType(TEXT)
    String PHONE = "phone";

    @DataType(INTEGER)
    String GENDER = "gender";

    @DataType(INTEGER)
    String AGE = "age";

    @DataType(REAL)
    String WEIGHT = "weight";

    @DataType(TEXT)
    String ALLERGY = "allergy";

    @DataType(TEXT)
    String AADHAAR = "aadhaar";

}
