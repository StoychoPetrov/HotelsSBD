package eu.mobile.hotelssbd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.models.RoomDetails;

/**
 * Created by stoycho.petrov on 13/04/2017.
 */

public class CategoriesTable extends SqliteDatabase{

    private static CategoriesTable categoriesTable;

    public static CategoriesTable getInstance(Context context){

        if(categoriesTable == null)
            categoriesTable = new CategoriesTable(context);

        return categoriesTable;
    }


    private CategoriesTable(Context context) {
        super(context);
    }

    public void insertCategories(RoomDetails roomDetails){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(COLUMN_DESCRIPTION,         roomDetails.getmRoomDescription());

        long id = db.insert(CATEGORIES_TABLE_NAME,null,insertValues);
        db.close();
        insertIntoRoomDetails(roomDetails.getmRoomPrice(),id,roomDetails.getmHotelId());

    }

    private void insertIntoRoomDetails(double price, long categoryId, int hotelId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(COLUMN_ROOM_PRICE,         price);
        insertValues.put(COLUMN_ROOM_DETAILS_HOTEL_ID_FOREIGN_KEY, hotelId);
        insertValues.put(COLUMN_CATEGORY_ID_FOREIGN_KEY, categoryId);

        db.insert(ROOM_DETAILS_TABLE_NAME,null,insertValues);
        db.close();
    }

    public int getRoomDetailsCount(){
        String query = "SELECT COUNT(*) FROM " + ROOM_DETAILS_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        return count;
    }

    public List<RoomDetails> getRooms(int hotelId){
        List<RoomDetails> rooms = new ArrayList<>();

        String query = "SELECT * FROM " + ROOM_DETAILS_TABLE_NAME + " rooms " +
                " JOIN " + CATEGORIES_TABLE_NAME + " categories "
                + " ON " + COLUMN_CATEGORY_ID_FOREIGN_KEY + "=" + COLUMN_CATEGORY_ID
                + " WHERE " + COLUMN_ROOM_DETAILS_HOTEL_ID_FOREIGN_KEY + "=" + hotelId;


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                RoomDetails roomDetails = new RoomDetails();
                roomDetails.setmRoomDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_DETAIL_ID)));
                roomDetails.setmRoomPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_ROOM_PRICE)));
                roomDetails.setmHotelId(hotelId);
                roomDetails.setmRoomDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));


                rooms.add(roomDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return rooms;
    }

//    public String getDescription(int id){
//        String query = "SELECT * FROM " + CATEGORIES_TABLE_NAME
//                + "WHERE " + COLUMN_CATEGORY_ID + "=" + id;
//
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//           return cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
//        }
//        cursor.close();
//        db.close();
//
//        return null;
//    }
}
