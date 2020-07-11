package com.example.travel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TravelDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "TravelDB";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String ITEM_TITLE = "itemTitle";
    public static String ITEM_IMAGE = "itemImage";
    public static String FAVORITE_STATUS = "fStatus";

    private static String TABLE_NAME1 = "cityTable";
    public static String KEY_ID1 = "id";
    public static String ITEM_TITLE1 = "itemTitle1";
    public static String ITEM_IMAGE1 = "itemImage1";
    public static String POPULATION_TITLE = "populationTitle";
    public static String CITY_DESC = "cityDescription";
    public static String AIRPORT_TITLE = "airportTitle";
    public static String COUNTRY_NAME = "countryName";


    public static final String TABLE_NAME3 ="registeruser";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="username";
    public static final String COL_3 ="password";

    // spaces
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT," + ITEM_TITLE+ " TEXT,"
            + ITEM_IMAGE + " TEXT," + FAVORITE_STATUS+" TEXT)";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_NAME1 + "("
            + KEY_ID1 + " TEXT," + ITEM_TITLE1+ " TEXT," +COUNTRY_NAME+ " TEXT,"
            + ITEM_IMAGE1 + " TEXT," + POPULATION_TITLE +" TEXT," +CITY_DESC+" TEXT," + AIRPORT_TITLE +" TEXT)";


    public TravelDB(Context context) { super(context,DATABASE_NAME,null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE1);
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(sqLiteDatabase);
    }

    // create empty table
    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 60; x++) {
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            cv.put(KEY_ID1, x);

            db.insert(TABLE_NAME,null, cv);
            db.insert(TABLE_NAME1,null, cv);
        }
    }



    // insert data into database
    public void insertIntoTheDatabase(String item_title, int item_image, String id, String fav_status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGE, item_image);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        db.insert(TABLE_NAME,null, cv);
        Log.d("FavDB Status", item_title + ", favstatus - "+fav_status+" - . " + cv);
    }

    public void insertIntoTheDatabase(String item_title1,String country_title, int item_image1, String id, String population_title, String city_desc,String airport_title) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE1, item_title1);
        cv.put(COUNTRY_NAME,country_title);
        cv.put(ITEM_IMAGE1, item_image1);
        cv.put(KEY_ID1, id);
        cv.put(POPULATION_TITLE, population_title);
        cv.put(CITY_DESC,city_desc);
        cv.put(AIRPORT_TITLE,airport_title);
        db.insert(TABLE_NAME1,null, cv);
        Log.d("TravelDB Status", item_title1 + ", city - "+population_title+" - . " + cv);
    }



    // read all fav data
    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID+"="+id+"";
        return db.rawQuery(sql,null,null);

    }
    // read all city data
    public Cursor read_all_city_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME1 + " where " + KEY_ID1+"="+id+"";
       // String sql = "select * from " + TABLE_NAME1 + " where " + KEY_ID1+"="+id+" ORDER BY "+ITEM_TITLE1+"";

        return db.rawQuery(sql,null,null);
    }





    // remove line from database
    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  "+ FAVORITE_STATUS+" ='0' WHERE "+KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", id.toString());

    }

    // select all favorite list

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FAVORITE_STATUS+" ='1'";
        return db.rawQuery(sql,null,null);
    }







    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME3,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }



}
