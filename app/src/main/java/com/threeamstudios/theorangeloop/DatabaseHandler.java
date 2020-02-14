package com.threeamstudios.theorangeloop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

// Abstract to ensure Singleton design. Only one instantiation is allowed for am SQLite DB.
public class DatabaseHandler extends SQLiteOpenHelper {

    // "Singleton" / Sole instantiation of DatabaseHandler/DataBase
    private static DatabaseHandler instance;

    // DataBase metadata
    private static final String DB_NAME = "organizationManager";
    private static final String TABLE_ORGANIZATIONS = "organizations";
    private static final String TABLE_REGISTERED = "registered";

    // Soley for the VIEW component, not for tracking data
    private static ArrayList<Organization> orgList = new ArrayList<Organization>();

    // ORGANIZATION Table fields/Columns
    private static final String ORG_ID = "id";
    protected static final String ORG_NAME = "name";
    protected static final String ORG_DESC = "desc";
    protected static final String ORG_URL = "url";

    // Retrieve following data from controller
    protected static final String ORG_SIZE = "size";

    // MEMBER Table fields/Columns
    private static final String TABLE_MEMBERS = "members";
    private static final String MEMBER_ID = "id";
    private static final String MEMBER_NAME = "name";
    // Add field for clubs participating in

    /* No Default constructor in SQLiteOpenHelper. Private for Singleton Design Pattern */
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, 1); // organizationManager
    }

    // Public getter for the sole instantiation of the DatabaseHandler
    public static synchronized DatabaseHandler getInstance(Context context) {

        // If there is no instantiation, execute the ONLY instantiation of DBHandler!
        if (instance == null) {
            instance = new DatabaseHandler(context.getApplicationContext());
        }
        return instance;
    }

    /* Establish Tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Total Organization Table. Replicate Server Call
        instantiateOrganizationTable(db);
        //instantiateRegisteredOrganizationsTable(db);
    }

    private void clearOldDB(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORGANIZATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
    }

    private void instantiateOrganizationTable(SQLiteDatabase db) {
        // SQLite command. Make Org table
        String CREATE_ORG_TABLE =
                "CREATE TABLE " + TABLE_ORGANIZATIONS
                        + "("
                        + ORG_ID + " INTEGER PRIMARY KEY, "
                        + ORG_NAME + " TEXT, "
                        + ORG_SIZE + " INTEGER, "
                        + ORG_DESC + " TEXT, "
                        + ORG_URL + " TEXT"
                        + ")";

        String CREATE_REG_TABLE =
                "CREATE TABLE " + TABLE_REGISTERED
                        + "("
                        + ORG_ID + " INTEGER PRIMARY KEY, "
                        + ORG_NAME + " TEXT, "
                        + ORG_SIZE + " INTEGER, "
                        + ORG_DESC + " TEXT, "
                        + ORG_URL + " TEXT"
                        + ")";
        // Execute commands
        db.execSQL(CREATE_ORG_TABLE);
        db.execSQL(CREATE_REG_TABLE);
    }

    public void instantiateRegisteredOrganizationsTable(SQLiteDatabase db) {
        // SQLite command. Make Org table
        String CREATE_ORG_TABLE =
                "CREATE TABLE " + TABLE_REGISTERED  // "registered"
                        + "("
                        + "id" + " INTEGER PRIMARY KEY, "
                        + "name" + " TEXT, "
                        //      + ORG_DESC + " TEXT, "
                        + "size" + " INTEGER, "
                        + "desc" + " TEXT, "
                        + "url" + "TEXT"
                        + ")";

        // Execute commands
        db.execSQL(CREATE_ORG_TABLE);
    }

    // For object params
    public boolean insertRegisteredOrganization(Organization org) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ORG_NAME, org.getOrgName());
        contentValues.put(ORG_SIZE, 0);
        contentValues.put(ORG_DESC, org.getOrgDesc());
        contentValues.put(ORG_URL, org.getImageURL());

        //db.insert(TABLE_ORGANIZATIONS, null, contentValues);
        db.insert(TABLE_REGISTERED, null, contentValues);
        return true;
    }

    // For object params
    public boolean insertOrganization(Organization org) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ORG_NAME, org.getOrgName());
        contentValues.put(ORG_SIZE, 0);
        contentValues.put(ORG_DESC, org.getOrgDesc());
        contentValues.put(ORG_URL, org.getImageURL());

        //db.insert(TABLE_ORGANIZATIONS, null, contentValues);
        db.insert(TABLE_ORGANIZATIONS, null, contentValues);
        return true;
    }

    public Cursor getDataOrganization(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from organizations where id = " + id + "", null);
        return res;
    }

    public Organization getOrgFromString (String orgName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor org = db.rawQuery("SELECT * FROM " + TABLE_ORGANIZATIONS + " WHERE " + ORG_NAME + " = '" + orgName + "'", null);

        Organization retOrg = new Organization();

        // Get url
        if(org.moveToFirst()) {
            retOrg.setOrgName(org.getString(org.getColumnIndex(ORG_NAME)));
            retOrg.setOrgDesc(org.getString(org.getColumnIndex(ORG_DESC)));
            retOrg.setImageURL(org.getString(org.getColumnIndex(ORG_URL)));
        }

        return retOrg;
    }
    public boolean deleteRegistered(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REGISTERED, ORG_NAME + " = '" + name + "'", null) > 0;
    }

    public Organization getRegisteredOrgFromString (String orgName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor org = db.rawQuery("SELECT * FROM " + TABLE_REGISTERED + " WHERE " + ORG_NAME + " = '" + orgName + "'", null);
        Organization retOrg = new Organization();

        // Get url
        if(org.moveToFirst()) {
            retOrg.setOrgName(org.getString(org.getColumnIndex(ORG_NAME)));
            retOrg.setOrgDesc(org.getString(org.getColumnIndex(ORG_DESC)));
            retOrg.setImageURL(org.getString(org.getColumnIndex(ORG_URL)));
        }

        return retOrg;
    }
    public boolean updateOrganization(Integer id, String name, String desc, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("size", size);
        contentValues.put("desc", desc);
        db.update(TABLE_ORGANIZATIONS, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public int numberOfRowsOrganization() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_ORGANIZATIONS);
        return numRows;
    }

    public Integer deleteOrganization(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORGANIZATIONS,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("organizations", null, null);
    }

    public ArrayList<Organization> getAllOrganization() {
        ArrayList<Organization> array_list = new ArrayList<Organization>();

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select name from organizations", null );
        Cursor res = null;
        String Query = "SELECT * FROM organizations";

        res = db.rawQuery(Query, null);
        if (res != null && res.moveToFirst()) {
            do {
                // Select particular Organization Data from DB
                Organization track = new Organization(
                        res.getString(res.getColumnIndex(ORG_NAME)),
                        res.getString(res.getColumnIndex(ORG_DESC)),
                        res.getString(res.getColumnIndex(ORG_URL)));

                array_list.add(track);
            } while (res.moveToNext());

        }
        res.close();

        return array_list;
    }

    public ArrayList<Organization> getAllRegisteredOrganization() {
        ArrayList<Organization> array_list = new ArrayList<Organization>();

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select name from organizations", null );
        Cursor res = null;
        String Query = "SELECT * FROM registered";

        res = db.rawQuery(Query, null);
        if (res != null && res.moveToFirst()) {
            do {
                // Select particular Organization Data from DB
                Organization track = new Organization(
                        res.getString(res.getColumnIndex(ORG_NAME)),
                        res.getString(res.getColumnIndex(ORG_DESC)),
                        res.getString(res.getColumnIndex(ORG_URL)));

                array_list.add(track);
            } while (res.moveToNext());

        }
        res.close();

        return array_list;
    }

    // Update Database when need be
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS organizations");
        db.setVersion(newVersion);
        onCreate(db);
    }

    public void resetDB(DatabaseHandler handler) {
        SQLiteDatabase db = handler.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORGANIZATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERED);
    }

}
