package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarDestiny extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "star_destiny.db";
    private static final int SCHEMA_VERSION = 1;

    public StarDestiny(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE star_destiny (gender TEXT, birthyear TEXT, check_year TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String gender, String birthyear, String check_year) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM star_destiny WHERE"
                                + " gender = '" + gender
                                + "' AND birthyear = '" + birthyear
                                + "' AND check_year = '" + check_year + "'",
                        null));
    }

    public void insert(String gender, String birthyear, String check_year, String result) {
        ContentValues cv = new ContentValues();

        cv.put("gender", gender);
        cv.put("birthyear", birthyear);
        cv.put("check_year", check_year);
        cv.put("result", result);

        getWritableDatabase().insert("star_destiny", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(3));
    }
}