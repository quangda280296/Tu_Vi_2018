package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HouseDirection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "house_direction.db";
    private static final int SCHEMA_VERSION = 1;

    public HouseDirection(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE house_direction (gender TEXT, house_direction TEXT, birthyear TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String gender, String house_direction, String birthyear) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM house_direction WHERE"
                                + " gender = '" + gender
                                + "' AND house_direction = '" + house_direction
                                + "' AND birthyear = '" + birthyear + "'",
                        null));
    }

    public void insert(String gender, String house_direction, String birthyear, String result) {
        ContentValues cv = new ContentValues();

        cv.put("gender", gender);
        cv.put("house_direction", house_direction);
        cv.put("birthyear", birthyear);
        cv.put("result", result);

        getWritableDatabase().insert("house_direction", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(3));
    }
}