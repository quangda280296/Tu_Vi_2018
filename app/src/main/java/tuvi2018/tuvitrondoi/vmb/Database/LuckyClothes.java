package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LuckyClothes extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lucky_clothes.db";
    private static final int SCHEMA_VERSION = 1;

    public LuckyClothes(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lucky_clothes (gender TEXT, birthday TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String gender, String birthday) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM lucky_clothes WHERE"
                                + " gender = '" + gender
                                + "' AND birthday = '" + birthday + "'",
                        null));
    }

    public void insert(String gender, String birthday, String result) {
        ContentValues cv = new ContentValues();

        cv.put("gender", gender);
        cv.put("birthday", birthday);
        cv.put("result", result);

        getWritableDatabase().insert("lucky_clothes", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(2));
    }
}