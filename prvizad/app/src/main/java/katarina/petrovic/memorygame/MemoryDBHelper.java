package katarina.petrovic.memorygame;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MemoryDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memory_game.db";
    private static final Integer DATABASE_VERSION = 1;
    private final String TABLE_NAME = "GAMES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_POINTS = "Points";

    public MemoryDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_POINTS + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String username, String email, Integer points){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put (COLUMN_EMAIL, email);
        values.put (COLUMN_POINTS, points);

        db.insert(TABLE_NAME, null, values);//null posto rucno punimo vrednosti
        close();
    }
    public void deletePlayer(String username){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USERNAME + " =?", new String[] {username});
        close();
    }
    //...delete function for our local base SQL...
    public void deleteWholeLocalBase(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        close();
    }

    public Model[] readModel(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query (true, TABLE_NAME, null, null, null, COLUMN_USERNAME, null, null, null);
        //...check if empty...
        if (cursor.getCount()<= 0)
            return null;

        Model[] model = new Model[cursor.getCount()];

        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            model[i++] = createModel(cursor);

        db.close();
        return model;
    }

    private Model createModel(Cursor cursor){
        String username = cursor.getString (cursor.getColumnIndexOrThrow (COLUMN_USERNAME));
        String email = cursor.getString (cursor.getColumnIndexOrThrow (COLUMN_EMAIL));
        Integer points = cursor.getInt (cursor.getColumnIndexOrThrow (COLUMN_POINTS));

        return new Model (username, email, points);//...return whole model...
    }

    public Integer[] readResultForPlayer(String username){
        SQLiteDatabase db = getReadableDatabase();
        //...asc list of points...
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERNAME + "=?", new String[] {username}, null, null, COLUMN_POINTS + " ASC");
        //...check if empty...
        if (cursor.getCount()<= 0)
            return null;

        Integer[] results = new Integer[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            results[i++] = createPlayer(cursor);

        close();
        return results;
    }
    /*
    public String[] selectUsername(String points){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME, null, COLUMN_POINTS + "=?"+" OR "+COLUMN_POINTS+"=?", new String[] { "points==10,points==0" }, null, null, COLUMN_USERNAME,null);
        if (cursor.getCount()<= 0)
            return null;
        String[] username =new String[cursor.getCount()];
        int i=0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            username[i++] =Integer.toString(createPlayer(cursor));

        close();
        return username;
    }*/

    private Integer createPlayer(Cursor cursor){
        return cursor.getInt (cursor.getColumnIndexOrThrow (COLUMN_POINTS));
    }
}
