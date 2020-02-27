package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BeautifulCard extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "beautiful_card.db";
    private static final int SCHEMA_VERSION = 1;

    public BeautifulCard(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE beautiful_card (gender TEXT, birthhour TEXT, birthday TEXT, telephonenumber TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String gender, String birthhour, String birthday, String telephonenumber) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM beautiful_card WHERE"
                        + " gender = '" + gender
                        + "' AND birthhour = '" + birthhour
                        + "' AND birthday = '" + birthday
                        + "' AND telephonenumber = '" + telephonenumber + "'",
                        null));
    }

    public void insert(String gender, String birthhour, String birthday, String telephonenumber, String result) {
        ContentValues cv = new ContentValues();

        cv.put("gender", gender);
        cv.put("birthhour", birthhour);
        cv.put("birthday", birthday);
        cv.put("telephonenumber", telephonenumber);
        cv.put("result", result);

        getWritableDatabase().insert("beautiful_card", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(4));
    }
}