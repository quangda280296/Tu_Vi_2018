package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuildingYear extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "building_year.db";
    private static final int SCHEMA_VERSION = 1;

    public BuildingYear(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE building_year (birthyear TEXT, building_year TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String birthyear, String building_year) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM building_year WHERE"
                                + " birthyear = '" + birthyear
                                + "' AND building_year = '" + building_year + "'",
                        null));
    }

    public void insert(String birthyear, String building_year, String result) {
        ContentValues cv = new ContentValues();

        cv.put("birthyear", birthyear);
        cv.put("building_year", building_year);
        cv.put("result", result);

        getWritableDatabase().insert("building_year", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(2));
    }
}