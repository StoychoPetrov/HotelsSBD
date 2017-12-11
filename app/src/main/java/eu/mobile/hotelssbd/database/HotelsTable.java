package eu.mobile.hotelssbd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.models.Hotel;

/**
 * Created by stoycho.petrov on 10/04/2017.
 */

public class HotelsTable extends SqliteDatabase {

    private static HotelsTable hotelsTable;

    private HotelsTable(Context context) {
        super(context);
    }

    public static HotelsTable getInstance(Context context){

        if(hotelsTable == null)
            hotelsTable = new HotelsTable(context);

        return hotelsTable;
    }

    public void insertHotels(List<Hotel> hotels){

        for(Hotel hotel : hotels) {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues insertValues = new ContentValues();

            insertValues.put(COLUMN_HOTEL_NAME,         hotel.getmHotelName());
            insertValues.put(COLUMN_HOTEL_DESCRIPTION,  hotel.getmHotelDescription());
            insertValues.put(COLUMN_STARS_COUNT,        hotel.getmStarsCount());
            List<String> images = hotel.getmImages();

            long hotelId = db.insert(HOTELS_TABLE_NAME,null,insertValues);
            db.close();
            for(String image : images) {
                insertImages(image,hotelId);
            }
        }
    }

    public int getHotelCount(){
        String query = "SELECT COUNT(*) FROM " + HOTELS_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        return count;
    }

    public List<Hotel> selectAllHotels(){
        List<Hotel> hotels = new ArrayList<>();

        String query = "SELECT * FROM " + HOTELS_TABLE_NAME;


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Hotel hotel = new Hotel();
                hotel.setmHotelId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOTEL_ID)));
                hotel.setmHotelName(cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_NAME)));
                hotel.setmHotelDescription(cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_DESCRIPTION)));
                hotel.setmStarsCount(cursor.getInt(cursor.getColumnIndex(COLUMN_STARS_COUNT)));
                hotel.setmImages(selectImagesByHotelId(hotel.getmHotelId()));
                hotels.add(hotel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return hotels;
    }

    public List<Hotel> searchHotels(String searchWord){
        List<Hotel> hotels = new ArrayList<>();

        String query = "SELECT * FROM " + HOTELS_TABLE_NAME
                + " WHERE " + COLUMN_HOTEL_NAME + " LIKE '" + searchWord + "%'";


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Hotel hotel = new Hotel();
                hotel.setmHotelId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOTEL_ID)));
                hotel.setmHotelName(cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_NAME)));
                hotel.setmHotelDescription(cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_DESCRIPTION)));
                hotel.setmStarsCount(cursor.getInt(cursor.getColumnIndex(COLUMN_STARS_COUNT)));
                hotel.setmImages(selectImagesByHotelId(hotel.getmHotelId()));
                hotels.add(hotel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return hotels;
    }

    public List<String> selectImagesByHotelId(int imageId){
        List<String> images = new ArrayList<>();

        String query = "SELECT * FROM " + IMAGES_TABLE_NAME +
                " WHERE " + COLUMN_HOTEL_ID_FOREIGN_KEY + " = " + imageId;

        SQLiteDatabase  db      = getReadableDatabase();
        Cursor          cursor  = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH));
                images.add(imagePath);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return images;
    }

    private void insertImages(String imagePath, long hotelId){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(COLUMN_IMAGE_PATH,             imagePath);
        insertValues.put(COLUMN_HOTEL_ID_FOREIGN_KEY,   hotelId);

        db.insert(IMAGES_TABLE_NAME,null,insertValues);
    }
}
