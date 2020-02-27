package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoupleAge extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "couple_age.db";
    private static final int SCHEMA_VERSION = 1;

    public CoupleAge(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE couple_age (gender TEXT, calendar_year TEXT, lunar_year TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String gender, String calendar_year, String lunar_year) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM couple_age WHERE"
                                + " gender = '" + gender
                                + "' AND calendar_year = '" + calendar_year
                                + "' AND lunar_year = '" + lunar_year + "'",
                        null));
    }

    public void insert(String gender, String calendar_year, String lunar_year, String result) {
        ContentValues cv = new ContentValues();

        cv.put("gender", gender);
        cv.put("calendar_year", calendar_year);
        cv.put("lunar_year", lunar_year);
        cv.put("result", result);

        getWritableDatabase().insert("couple_age", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(3));
    }
}