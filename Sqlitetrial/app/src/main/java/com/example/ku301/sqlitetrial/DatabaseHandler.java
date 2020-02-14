package com.example.ku301.sqlitetrial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;



// Abstract to ensure Singleton design. Only one instantiation is allowed for am SQLite DB.
public class DatabaseHandler extends SQLiteOpenHelper {

    public static int returnOne(){
        return 1;
    }

    // No Default constructor in SQLiteOpenHelper. Private for Singleton Design Pattern
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // Single instantiation of DatabaseHandler/DataBase
    private static DatabaseHandler instance;

    // Public getter for the sole instantiation of the DatabaseHandler
    public static synchronized DatabaseHandler getInstance(Context context) {

        // If there is no instantiation, execute the ONLY instantiation of DBHandler!
        if (instance == null) {
            instance = new DatabaseHandler(context.getApplicationContext());
        }
        return instance;
    }

    // DataBase metadata
    private static final String DB_NAME = "organizationManager";
    private static final String TABLE_ORGANIZATIONS = "organizations";
    private static final String TABLE_MEMBERS = "members";

    // ORGANIZATION Table fields/Columns
    private static final String ORG_ID = "id";
    protected static final String ORG_NAME = "name";
    protected static final String ORG_DESC = "desc";

    // Retrieve following data from controller
    protected static final String ORG_SIZE = "size";

    // MEMBER Table fields/Columns
    private static final String MEMBER_ID = "id";
    private static final String MEMBER_NAME = "name";
    // Add field for clubs participating in

    // Establish Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQLite command. Make Org table
        String CREATE_ORG_TABLE =
                "CREATE TABLE " + TABLE_ORGANIZATIONS
                        + "("
                        + "id" + " INTEGER PRIMARY KEY, "
                        + "name" + " TEXT, "
                  //      + ORG_DESC + " TEXT, "
                        + "size" + " INTEGER, "
                        + "desc" + " TEXT"
                        + ")";
  /*      String CREATE_MEMBERS_TABLE =
                "CREATE TABLE " + TABLE_MEMBERS
                        + "("
                        + MEMBER_ID + " INTEGER PRIMARY KEY, "
                        + MEMBER_NAME + " TEXT "
                        + ")";
*/
        // Execute commands
        db.execSQL(CREATE_ORG_TABLE);
//        db.execSQL(CREATE_MEMBERS_TABLE);
    }

    public boolean insertOrganization(String name, String desc, String size){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("size", size);
        contentValues.put("desc", desc);

        //db.insert(TABLE_ORGANIZATIONS, null, contentValues);
         db.insert("organizations", null, contentValues);
        return true;
    }

    public Cursor getDataOrganization(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from organizations where id="+id+"", null );
        return res;
    }
    public boolean updateOrganization (Integer id, String name, String desc, String size)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("size", size);
        contentValues.put("desc", desc);
        db.update(TABLE_ORGANIZATIONS, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public int numberOfRowsOrganization(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_ORGANIZATIONS);
        return numRows;
    }
    public Integer deleteOrganization (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORGANIZATIONS,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public void removeAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("organizations",null,null);
    }




    public ArrayList<String> getAllOrganization(){
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select name from organizations", null );
        Cursor res = null;
        String Query ="SELECT * FROM organizations";

        res = db.rawQuery(Query,null);
        if(res != null && res.moveToFirst()){
            do{
                array_list.add(res.getString(res.getColumnIndex(ORG_NAME)));
            }while(res.moveToNext());

        }
        res.close();

     /*   res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ORG_NAME)));
            res.moveToNext();
        }*/
        return array_list;
    }

    public int numberOfRowsMember(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_MEMBERS);
        return numRows;
    }
    public boolean insertMember(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert(TABLE_MEMBERS, null, contentValues);
        return true;
    }

    public Cursor getDataMember(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from members where id="+id+"", null );
        return res;
    }
    public boolean updateMember (Integer id, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.update(TABLE_MEMBERS, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteMember (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MEMBERS,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList<String> getAllMember(){
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from members", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MEMBER_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    // Update Database when need be
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    // Methods, CRUD operations for database.

}
