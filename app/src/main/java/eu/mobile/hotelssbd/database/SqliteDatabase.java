package eu.mobile.hotelssbd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE            = "hotels";
    private static final int    DATABASE_VERSION    = 1;

    /*********** Hotels table ***************/
    protected static final String HOTELS_TABLE_NAME                           = "hotels_table";
    protected static final String COLUMN_HOTEL_ID                             = "hotel_id";
    protected static final String COLUMN_HOTEL_NAME                           = "hotel_name";
    protected static final String COLUMN_HOTEL_DESCRIPTION                    = "hotel_description";

    /*********** Images table ***************/
    protected static final String IMAGES_TABLE_NAME                           = "images_table";
    protected static final String COLUMN_IMAGE_ID                             ="image_id";
    protected static final String COLUMN_IMAGE_PATH                           = "image_path";
    protected static final String COLUMN_HOTEL_ID_FOREIGN_KEY                 = "hotel_id_FOREIGN_key";

    /*********** Room details table *********/
    protected static final String ROOM_DETAILS_TABLE_NAME                     = "room_details_table";
    protected static final String COLUMN_ROOM_DETAIL_ID                       = "room_detal_id";
    protected static final String COLUMN_ROOM_PRICE                           = "room_price";
    protected static final String COLUMN_ROOM_DETAILS_HOTEL_ID_FOREIGN_KEY     = "room_details_hotel_id_FOREIGN_key";
    protected static final String COLUMN_CATEGORY_ID_FOREIGN_KEY               = "category_id_FOREIGN_key";

    /*********** Categories table ***********/
    protected static final String CATEGORIES_TABLE_NAME                       = "categories_table";
    protected static final String COLUMN_CATEGORY_ID                          = "category_id";
    protected static final String COLUMN_DESCRIPTION                          = "description";

    /*********** Rooms table **********************/
    protected static final String ROOMS_TABLE_NAME                            = "rooms_table";
    protected static final String COLUMN_ROOM_ID                              = "room_id";
    protected static final String COLUMN_ROOM_NUMBER                          = "room_number";
    protected static final String COLUMN_ROOM_DESCRIPTION                     = "room_description";
    protected static final String COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY           = "room_detail_id_FOREIGN_key";

    /*********** Clients table ********************/
    protected static final String CLIENTS_TABLE_NAME                          = "clients_table";
    protected static final String COLUMN_CLIENT_ID                            = "client_id";
    protected static final String COLUMN_FIRST_NAME                           = "first_name";
    protected static final String COLUMN_LAST_NAME                            = "last_name";
    protected static final String COLUMN_EGN                                  = "egn";

    /*********** Reservations table ***************/
    protected static final String RESERVATIONS_TABLE_NAME                     = "reservations_table";
    protected static final String COLUMN_RESERVATION_ID                       = "reservation_id";
    protected static final String COLUMN_DATE_FROM                            = "date_from";
    protected static final String COLUMN_DATE_TO                              = "date_to";
    protected static final String COLUMN_ROOM_ID_FOREIGN_KEY                  = "room_id_FOREIGN_key";
    protected static final String COLUMN_CLIENT_ID_FOREIGN_KEY                = "client_id_FOREIGN_key";


    //Create tables
    private static final String CREATE_HOTELS_TABLE = "CREATE TABLE " + HOTELS_TABLE_NAME + " ( "
            + COLUMN_HOTEL_ID                           + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_HOTEL_NAME                         + " TEXT, "
            + COLUMN_HOTEL_DESCRIPTION                  + " TEXT)";

    private static final String CREATE_IMAGES_TABLE = "CREATE TABLE " + IMAGES_TABLE_NAME + " ( "
            + COLUMN_IMAGE_ID                           + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_IMAGE_PATH                         + " TEXT, "
            + COLUMN_HOTEL_ID_FOREIGN_KEY                + " INTEGER, "
            + " FOREIGN KEY(" + COLUMN_HOTEL_ID_FOREIGN_KEY + ") REFERENCES " + HOTELS_TABLE_NAME + "(" + COLUMN_HOTEL_ID + "))";

    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CATEGORIES_TABLE_NAME + " ( "
            + COLUMN_CATEGORY_ID                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DESCRIPTION                        + " TEXT)";

    private static final String CREATE_ROOM_DETAILS_TABLE = " CREATE TABLE " + ROOM_DETAILS_TABLE_NAME + " ( "
            + COLUMN_ROOM_DETAIL_ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROOM_PRICE                         + " REAL, "
            + COLUMN_ROOM_DETAILS_HOTEL_ID_FOREIGN_KEY   + " INTEGER, "
            + COLUMN_CATEGORY_ID_FOREIGN_KEY             + " INTEGER, "
            + " FOREIGN KEY (" + COLUMN_ROOM_DETAILS_HOTEL_ID_FOREIGN_KEY + ") REFERENCES " + HOTELS_TABLE_NAME + "(" + COLUMN_HOTEL_ID + "),"
            + " FOREIGN KEY (" + COLUMN_CATEGORY_ID_FOREIGN_KEY + ") REFERENCES " + CATEGORIES_TABLE_NAME + " (" + COLUMN_CATEGORY_ID + "))";

    private static final String CREATE_ROOMS_TABLE  = " CREATE TABLE " + ROOMS_TABLE_NAME + " ( "
            + COLUMN_ROOM_ID                            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROOM_NUMBER                        + " INTEGER, "
            + COLUMN_ROOM_DESCRIPTION                   + " TEXT, "
            + COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY   + " INTEGER, "
            + " FOREIGN KEY (" + COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY + ") REFERENCES " + ROOM_DETAILS_TABLE_NAME + "(" + COLUMN_ROOM_DETAIL_ID + "))";

    private static final String CREATE_CLIENTS_TABLE    = "CREATE TABLE " + CLIENTS_TABLE_NAME + " ( "
            + COLUMN_CLIENT_ID                          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FIRST_NAME                         + " TEXT, "
            + COLUMN_LAST_NAME                          + " TEXT, "
            + COLUMN_EGN                                + " TEXT)";

    private static final String CREATE_RESERVATIONS = " CREATE TABLE " + RESERVATIONS_TABLE_NAME + " ( "
            + COLUMN_RESERVATION_ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE_FROM                          + " TEXT, "
            + COLUMN_DATE_TO                            + " TEXT, "
            + COLUMN_ROOM_ID_FOREIGN_KEY                 + " INTEGER, "
            + COLUMN_CLIENT_ID_FOREIGN_KEY               + " INTEGER, "
            + " FOREIGN KEY (" + COLUMN_ROOM_ID_FOREIGN_KEY + ") REFERENCES " + ROOMS_TABLE_NAME + "(" + COLUMN_ROOM_ID + "),"
            + " FOREIGN KEY (" + COLUMN_CLIENT_ID_FOREIGN_KEY + ") REFERENCES " + CLIENTS_TABLE_NAME + "(" + COLUMN_CLIENT_ID + "))";

    private static  SqliteDatabase   database;

    public static SqliteDatabase getInstance(Context context){
        if(database == null)
            database =  new SqliteDatabase(context);

        return database;
    }

    protected SqliteDatabase(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_HOTELS_TABLE);
        sqLiteDatabase.execSQL(CREATE_IMAGES_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORIES_TABLE);
        sqLiteDatabase.execSQL(CREATE_ROOM_DETAILS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ROOMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_CLIENTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HOTELS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ROOM_DETAILS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ROOMS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLIENTS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}