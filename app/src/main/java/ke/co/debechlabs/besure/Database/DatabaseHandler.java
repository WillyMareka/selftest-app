package ke.co.debechlabs.besure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.models.County;
import ke.co.debechlabs.besure.models.Facility;
import ke.co.debechlabs.besure.models.Pharmacy;

/**
 * Created by chriz on 6/6/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "besure_kenya";

    // County Table
    private static final String COUNTY_TABLE_NAME = "county";

    private static final String COUNTY_ID = "id";
    private static final String COUNTY_NAME = "county_name";

    // Pharmacy Table
    private static final String PHARMACY_TABLE_NAME = "pharmacy";

    private static final String PHARMACY_ID = "id";
    private static final String PHARMACY_NAME = "pharmacy_name";
    private static final String PHARMACY_LOCATION = "location";
    private static final String PHARMACY_LONGITUDE = "longitude";
    private static final String PHARMACY_LATITUDE = "latitude";
    private static final String PHARMACY_COUNTY_ID = "county_id";

    // Facility Table
    private static final String FACILITY_TABLE_NAME = "facility";

    private static final String FACILITY_ID = "id";
    private static final String FACILITY_CODE = "facility_code";
    private static final String FACILITY_NAME = "facility_name";
    private static final String FACILITY_LONGITUDE = "longitude";
    private static final String FACILITY_LATITUDE = "latitude";
    private static final String FACILITY_COUNTY_NAME = "county_name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTY_TABLE = "CREATE TABLE " + COUNTY_TABLE_NAME + "("
                + COUNTY_ID + " INTEGER PRIMARY KEY,"
                + COUNTY_NAME + " TEXT"
                + ")";

        String CREATE_FACILITY_TABLE = "CREATE TABLE " + FACILITY_TABLE_NAME + "("
                + FACILITY_ID  + " INTEGER PRIMARY KEY,"
                + FACILITY_CODE + " TEXT,"
                + FACILITY_NAME + " TEXT,"
                + FACILITY_LATITUDE + " TEXT,"
                + FACILITY_LONGITUDE + " TEXT,"
                + FACILITY_COUNTY_NAME + " TEXT"
                + ")";

        String CREATE_PHARMACY_TABLE = "CREATE TABLE " + PHARMACY_TABLE_NAME + "("
                + PHARMACY_ID  + " INTEGER PRIMARY KEY,"
                + PHARMACY_NAME + " TEXT,"
                + PHARMACY_LATITUDE + " TEXT,"
                + PHARMACY_LONGITUDE + " TEXT,"
                + PHARMACY_LOCATION + " TEXT,"
                + PHARMACY_COUNTY_ID + " INTEGER"
                + ")";

        db.execSQL(CREATE_COUNTY_TABLE);
        db.execSQL(CREATE_FACILITY_TABLE);
        db.execSQL(CREATE_PHARMACY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PHARMACY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FACILITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COUNTY_TABLE_NAME);

        onCreate(db);
    }

    public void clearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + PHARMACY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FACILITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COUNTY_TABLE_NAME);

        onCreate(db);
    }

    // Add County
    public void addCounty(County county){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COUNTY_ID, county.get_id());
        values.put(COUNTY_NAME, county.getCounty_name());

        db.insert(COUNTY_TABLE_NAME, null, values);
        db.close();
    }

    public void addCounties(List<County> counties){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (County county : counties){
                values.put(COUNTY_ID, county.get_id());
                values.put(COUNTY_NAME, county.getCounty_name());

                db.insert(COUNTY_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public County getCounty(int id){
        County county = new County();
        SQLiteDatabase db = this.getReadableDatabase();
        if(id != 0) {
            Cursor cursor = db.query(
                    COUNTY_TABLE_NAME,
                    null,
                    COUNTY_ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null,
                    null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    System.out.println("Cursor Index:::" + cursor.getInt(0));
                    county.set_id(cursor.getInt(0));
                    county.setCounty_name(cursor.getString(1));
                }
            }
        }else{
            county.setCounty_name("Unknown");
            county.set_id(0);
        }

        return county;
    }

    // Get all counties
    public List<County> getAllCounties(){
        List<County> countyList = new ArrayList<County>();
        String selectQuery = "SELECT  * FROM " + COUNTY_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.set_id(Integer.parseInt(cursor.getString(0)));
                county.setCounty_name(cursor.getString(1));

                countyList.add(county);
            }while(cursor.moveToNext());
        }
        return countyList;
    }

    public int getCountiesCount(){
        String countQuery = "SELECT * FROM " + COUNTY_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }
    // Add Facility
    public void addFacility(Facility facility){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FACILITY_ID, facility.get_id());
        values.put(FACILITY_NAME, facility.get_facility_name());
        values.put(FACILITY_CODE, facility.get_facility_code());
        values.put(FACILITY_LATITUDE, facility.get_latitude());
        values.put(FACILITY_LONGITUDE, facility.get_longitude());
        values.put(FACILITY_COUNTY_NAME, facility.get_county_name());

        db.insert(FACILITY_TABLE_NAME, null, values);
        db.close();
    }

    public void addFacilities(List<Facility> facilities){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Facility facility : facilities){
                values.put(FACILITY_ID, facility.get_id());
                values.put(FACILITY_NAME, facility.get_facility_name());
                values.put(FACILITY_CODE, facility.get_facility_code());
                values.put(FACILITY_LATITUDE, facility.get_latitude());
                values.put(FACILITY_LONGITUDE, facility.get_longitude());
                values.put(FACILITY_COUNTY_NAME, facility.get_county_name());

                db.insert(FACILITY_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public List<Facility> getAllFacilities(){
        List<Facility> facilityList = new ArrayList<Facility>();
        String selectQuery = "SELECT  * FROM " + FACILITY_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Facility facility = new Facility();

                facility.set_id(Integer.parseInt(cursor.getString(0)));
                facility.set_facility_code(cursor.getString(1));
                facility.set_facility_name(cursor.getString(2));
                facility.set_latitude(cursor.getString(3));
                facility.set_longitude(cursor.getString(4));
                facility.set_county_name(cursor.getString(5));

                facilityList.add(facility);
            }while(cursor.moveToNext());
        }
        return facilityList;
    }

    public int getFacilitiesCount(){
        String countQuery = "SELECT * FROM " + FACILITY_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    public List<Facility> getFacilities(String county_name){
        List<Facility> facilityList = new ArrayList<Facility>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                FACILITY_TABLE_NAME,
                null,
                FACILITY_COUNTY_NAME + " = ?",
                new String[]{county_name},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()){
            do {
                Facility facility = new Facility();

                facility.set_id(Integer.parseInt(cursor.getString(0)));
                facility.set_facility_code(cursor.getString(1));
                facility.set_facility_name(cursor.getString(2));
                facility.set_latitude(cursor.getString(3));
                facility.set_longitude(cursor.getString(4));
                facility.set_county_name(cursor.getString(5));

                facilityList.add(facility);
            }while(cursor.moveToNext());
        }

        return facilityList;
    }

    // Add Pharmacy
    public void addPharmacy(Pharmacy pharmacy){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PHARMACY_ID, pharmacy.get_id());
        values.put(PHARMACY_NAME, pharmacy.get_pharmacy_name());
        values.put(PHARMACY_LATITUDE, pharmacy.get_latitude());
        values.put(PHARMACY_LONGITUDE, pharmacy.get_longitude());
        values.put(PHARMACY_LOCATION, pharmacy.get_location());
        values.put(PHARMACY_COUNTY_ID, pharmacy.get_county_id());

        db.insert(PHARMACY_TABLE_NAME, null, values);
        db.close();
    }

    public void addPharmacies(List<Pharmacy> pharmacies){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Pharmacy pharmacy : pharmacies){
                values.put(PHARMACY_ID, pharmacy.get_id());
                values.put(PHARMACY_NAME, pharmacy.get_pharmacy_name());
                values.put(PHARMACY_LATITUDE, pharmacy.get_latitude());
                values.put(PHARMACY_LONGITUDE, pharmacy.get_longitude());
                values.put(PHARMACY_LOCATION, pharmacy.get_location());
                values.put(PHARMACY_COUNTY_ID, pharmacy.get_county_id());

                db.insert(PHARMACY_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public List<Pharmacy> getAllPharmacies(){
        List<Pharmacy> pharmacyList = new ArrayList<Pharmacy>();

        String selectQuery = "SELECT  * FROM " + PHARMACY_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Pharmacy pharmacy = new Pharmacy();

                pharmacy.set_id(Integer.parseInt(cursor.getString(0)));
                pharmacy.set_pharmacy_name(cursor.getString(1));
                pharmacy.set_latitude(cursor.getString(2));
                pharmacy.set_longitude(cursor.getString(3));
                pharmacy.set_location(cursor.getString(4));
                pharmacy.set_county_id(Integer.parseInt(cursor.getString(5)));

                pharmacyList.add(pharmacy);

            }while(cursor.moveToNext());
        }

        return pharmacyList;
    }

    public int getPharmaciesCount(){
        String countQuery = "SELECT * FROM " + PHARMACY_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }
}
