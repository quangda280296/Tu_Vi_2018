package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BeautifulDay extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "beautiful_day.db";
    private static final int SCHEMA_VERSION = 1;

    public BeautifulDay(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE beautiful_day (job TEXT, execute_day TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String job, String execute_day) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM beautiful_day WHERE"
                                + " job = '" + job
                                + "' AND execute_day = '" + execute_day + "'",
                        null));
    }

    public void insert(String job, String execute_day, String result) {
        ContentValues cv = new ContentValues();

        cv.put("job", job);
        cv.put("execute_day", execute_day);
        cv.put("result", result);

        getWritableDatabase().insert("beautiful_day", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(2));
    }
}