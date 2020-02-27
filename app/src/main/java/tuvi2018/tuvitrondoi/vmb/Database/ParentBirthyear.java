package tuvi2018.tuvitrondoi.vmb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParentBirthyear extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "parent_birthyear.db";
    private static final int SCHEMA_VERSION = 1;

    public ParentBirthyear(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE parent_birthyear (father_birthyear TEXT, mother_birthyear TEXT, children_birthyear TEXT, result TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getData(String father_birthyear, String mother_birthyear, String children_birthyear) {
        return (getReadableDatabase().rawQuery
                ("SELECT * FROM parent_birthyear WHERE"
                                + " father_birthyear = '" + father_birthyear
                                + "' AND mother_birthyear = '" + mother_birthyear
                                + "' AND children_birthyear = '" + children_birthyear + "'",
                        null));
    }

    public void insert(String father_birthyear, String mother_birthyear, String children_birthyear, String result) {
        ContentValues cv = new ContentValues();

        cv.put("father_birthyear", father_birthyear);
        cv.put("mother_birthyear", mother_birthyear);
        cv.put("children_birthyear", children_birthyear);
        cv.put("result", result);

        getWritableDatabase().insert("parent_birthyear", null, cv);
    }

    public String getResult(Cursor c) {
        return (c.getString(3));
    }
}