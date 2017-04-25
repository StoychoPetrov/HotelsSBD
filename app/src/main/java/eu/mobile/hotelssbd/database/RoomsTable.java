package eu.mobile.hotelssbd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.models.Room;

/**
 * Created by stoycho.petrov on 24/04/2017.
 */

public class RoomsTable extends SqliteDatabase {

    private static RoomsTable roomsTable;

    public static RoomsTable getInstance(Context context){

        if(roomsTable == null)
            roomsTable = new RoomsTable(context);

        return roomsTable;
    }

    protected RoomsTable(Context context) {
        super(context);
    }

    public void insertRooms(List<Room> rooms){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        for(Room room : rooms) {
            insertValues.put(COLUMN_ROOM_NUMBER, room.getmRoomNumber());
            insertValues.put(COLUMN_ROOM_DESCRIPTION, room.getmRoomDescription());
            insertValues.put(COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY, room.getmRoomDetailId());

            db.insert(ROOMS_TABLE_NAME, null, insertValues);
        }
        db.close();
    }

    public List<Room> selectRooms(){
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT * FROM " + ROOMS_TABLE_NAME;


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setmRoomId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID)));
                room.setmRoomNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_NUMBER)));
                room.setmRoomDescription(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_DESCRIPTION)));
                room.setmRoomDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY)));
                rooms.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return rooms;
    }
    public int countRooms(){
        String query = "SELECT COUNT(*) FROM " + ROOMS_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        return count;
    }

    public int selectRoomId(int roomDetailId){

        String query = "SELECT " + COLUMN_ROOM_ID  + " FROM " + ROOMS_TABLE_NAME +
                " WHERE " + COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY + " = " + roomDetailId;


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int roomId = -1;
        if (cursor.moveToFirst()) {
               roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID));
        }
        cursor.close();
        db.close();

        return roomId;
    }
}
