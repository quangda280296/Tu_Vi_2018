package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CheckDestiny extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "check_destiny.db";
    private static final int SCHEMA_VERSION = 1;

    public CheckDestiny(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE check_destiny (check_year TEXT, birthday TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String check_year, String birthday) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM check_destiny WHERE"
                                + " check_year = '" + check_year
                                + "' AND birthday = '" + birthday + "'",
                        null));
    }

    public void insert(String check_year, String birthday, String result) {
        ContentValues cv = new ContentValues();

        cv.put("check_year", check_year);
        cv.put("birthday", birthday);
        cv.put("result", result);

        getWritableDatabase().insert("check_destiny", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(2));
    }
}