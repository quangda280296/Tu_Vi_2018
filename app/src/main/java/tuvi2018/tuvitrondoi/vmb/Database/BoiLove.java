package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BoiLove extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "boi_love.db";
    private static final int SCHEMA_VERSION = 1;

    public BoiLove(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE boi_love (boy_name TEXT, boy_birthday TEXT, girl_name TEXT, girl_birthday TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String boy_name, String boy_birthday, String girl_name, String girl_birthday) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM boi_love WHERE"
                                + " boy_name = '" + boy_name
                                + "' AND boy_birthday = '" + boy_birthday
                                + "' AND girl_name = '" + girl_name
                                + "' AND girl_birthday = '" + girl_birthday + "'",
                        null));
    }

    public void insert(String boy_name, String boy_birthday, String girl_name, String girl_birthday, String result) {
        ContentValues cv = new ContentValues();

        cv.put("boy_name", boy_name);
        cv.put("boy_birthday", boy_birthday);
        cv.put("girl_name", girl_name);
        cv.put("girl_birthday", girl_birthday);
        cv.put("result", result);

        getWritableDatabase().insert("boi_love", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(2));
    }
}