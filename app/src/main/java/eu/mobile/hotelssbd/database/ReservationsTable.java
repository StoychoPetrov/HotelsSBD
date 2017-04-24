package eu.mobile.hotelssbd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.Models.Client;
import eu.mobile.hotelssbd.Models.Hotel;
import eu.mobile.hotelssbd.Models.Reservation;
import eu.mobile.hotelssbd.Models.Room;
import eu.mobile.hotelssbd.Models.RoomDetails;

/**
 * Created by stoycho.petrov on 24/04/2017.
 */

public class ReservationsTable extends SqliteDatabase {

    private static ReservationsTable reservation;

    public static ReservationsTable getInstance(Context context){

        if(reservation == null)
            reservation = new ReservationsTable(context);

        return reservation;
    }

    protected ReservationsTable(Context context) {
        super(context);
    }

    public long insertClient(Client client){
        SQLiteDatabase db           = getWritableDatabase();
        ContentValues  insertValues = new ContentValues();

        insertValues.put(COLUMN_FIRST_NAME, client.getmFirstName());
        insertValues.put(COLUMN_LAST_NAME,  client.getmLastName());
        insertValues.put(COLUMN_EGN,        client.getmEgn());

        long clientId   = db.insert(CLIENTS_TABLE_NAME, null, insertValues);
        db.close();
        return clientId;
    }

    public void insertReservation(Reservation reservation, int clientId, int roomId){
        SQLiteDatabase db           = getWritableDatabase();
        ContentValues  insertValues = new ContentValues();

        insertValues.put(COLUMN_DATE_FROM,                  reservation.getmDateFrom());
        insertValues.put(COLUMN_DATE_TO,                    reservation.getmDateTo());
        insertValues.put(COLUMN_CLIENT_ID_FOREIGN_KEY,      clientId);
        insertValues.put(COLUMN_ROOM_ID_FOREIGN_KEY,        roomId);

        db.insert(RESERVATIONS_TABLE_NAME, null, insertValues);
        db.close();
    }

    public Client selectClient(String egn){
        String query = "SELECT * FROM " + CLIENTS_TABLE_NAME +
                " WHERE " + COLUMN_EGN + " = " + egn;


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Client client = null;
        if (cursor.moveToFirst()) {
            client = new Client();
            client.setmClientId(cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENT_ID)));
            client.setmFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
            client.setmLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
            client.setmEgn(cursor.getString(cursor.getColumnIndex(COLUMN_EGN)));
        }
        cursor.close();
        db.close();

        return client;
    }

    public List<Reservation> selectReservations(Context context,List<Client> clients,List<Room> rooms, List<RoomDetails> roomDetailses, List<Hotel> hotels){
        String query = "SELECT * " +
                "FROM " + CLIENTS_TABLE_NAME        + " clients " +
                "JOIN " + RESERVATIONS_TABLE_NAME   + " reserve " +
                "ON "   + "clients." + COLUMN_CLIENT_ID + " = " + "reserve." + COLUMN_CLIENT_ID_FOREIGN_KEY +
                " JOIN " + ROOMS_TABLE_NAME  + " rooms " +
                "ON "   + "reserve." + COLUMN_ROOM_ID_FOREIGN_KEY + " = rooms." + COLUMN_ROOM_ID +
                " JOIN " + ROOM_DETAILS_TABLE_NAME + " details " +
                "ON " + "rooms." + COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY + " = details." + COLUMN_ROOM_DETAIL_ID +
                " JOIN " + HOTELS_TABLE_NAME + " hotels " +
                "ON "    + "details." + COLUMN_ROOM_DETAILS_HOTEL_ID_FOREIGN_KEY + " = " + "hotels." + COLUMN_HOTEL_ID;

        List<Reservation>   reservations = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Client      client      = new Client();
                Reservation reservation = new Reservation();
                Room        room        = new Room();
                RoomDetails roomDetails = new RoomDetails();
                Hotel       hotel       = new Hotel();

                client.setmClientId(cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENT_ID)));
                client.setmFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                client.setmLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                client.setmEgn(cursor.getString(cursor.getColumnIndex(COLUMN_EGN)));
                clients.add(client);

                reservation.setmReservationId(cursor.getInt(cursor.getColumnIndex(COLUMN_RESERVATION_ID)));
                reservation.setmDateFrom(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_FROM)));
                reservation.setmDateTo(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TO)));
                reservations.add(reservation);

                room.setmRoomId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID)));
                room.setmRoomNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_NUMBER)));
                room.setmRoomDescription(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_DESCRIPTION)));
                room.setmRoomDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_DETAIL_ID_FOREIGN_KEY)));
                rooms.add(room);

                roomDetails.setmRoomDetailId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_DETAIL_ID)));
                roomDetails.setmRoomPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_ROOM_PRICE)));
                roomDetailses.add(roomDetails);

                hotel.setmHotelName(cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_NAME)));
                hotel.setmImages(HotelsTable.getInstance(context).selectImagesByHotelId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOTEL_ID))));
                hotels.add(hotel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return reservations;
    }
}
