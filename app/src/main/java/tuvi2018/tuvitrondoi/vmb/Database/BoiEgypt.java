package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BoiEgypt extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "boi_egypt.db";
    private static final int SCHEMA_VERSION = 1;

    public BoiEgypt(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE boi_egypt (name TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String name) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM boi_egypt WHERE" + " name = '" + name + "'",
                        null));
    }

    public void insert(String name, String result) {
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("result", result);

        getWritableDatabase().insert("boi_egypt", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(1));
    }
}