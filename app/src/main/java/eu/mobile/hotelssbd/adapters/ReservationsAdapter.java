package eu.mobile.hotelssbd.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.mobile.hotelssbd.connectionClasses.DownloadImage;
import eu.mobile.hotelssbd.models.Client;
import eu.mobile.hotelssbd.models.Hotel;
import eu.mobile.hotelssbd.models.Reservation;
import eu.mobile.hotelssbd.models.Room;
import eu.mobile.hotelssbd.models.RoomDetails;
import eu.mobile.hotelssbd.R;

/**
 * Created by stoycho.petrov on 24/04/2017.
 */

public class ReservationsAdapter extends ArrayAdapter<Reservation> {

    private List<Reservation>   mReservations;
    private List<Client>        mClients;
    private List<Room>          mRooms;
    private List<RoomDetails>   mRoomDetails;
    private List<Hotel>         mHotels;
    private LayoutInflater      mLayoutInflater;

    private Context             mContext;
    private OnDeleteClicked     mDeleteListener;

    public interface OnDeleteClicked{
        void onDelete(int position);
    }

    public ReservationsAdapter(@NonNull Context context, @NonNull List<Reservation> reservations, List<Room> rooms, List<Client> clients, List<RoomDetails> roomDetails, List<Hotel> hotels, OnDeleteClicked listener) {
        super(context, R.layout.item_reservation, reservations);

        mReservations       = reservations;
        mRooms              = rooms;
        mClients            = clients;
        mRoomDetails        = roomDetails;
        mHotels             = hotels;
        mLayoutInflater     = ((Activity)context).getLayoutInflater();
        mDeleteListener     = listener;

        mContext            = context;
    }

    private class ViewHolder{
        private ImageView       mHotelImageView;
        private TextView        mHotelNameTxt;
        private TextView        mClientNamesTxt;
        private TextView        mNights;
        private TextView        mPrice;
        private ImageView       mDeleteImg;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView                                 =  mLayoutInflater.inflate(R.layout.item_reservation, null);
            ReservationsAdapter.ViewHolder viewHolder   =  new ReservationsAdapter.ViewHolder();

            viewHolder.mHotelImageView                  = (ImageView)convertView.findViewById(R.id.hotel_image);
            viewHolder.mHotelNameTxt                    = (TextView) convertView.findViewById(R.id.hotel_name);
            viewHolder.mClientNamesTxt                  = (TextView) convertView.findViewById(R.id.client_names);
            viewHolder.mNights                          = (TextView) convertView.findViewById(R.id.nights);
            viewHolder.mPrice                           = (TextView) convertView.findViewById(R.id.price);
            viewHolder.mDeleteImg                       = (ImageView)convertView.findViewById(R.id.delete_img);

            convertView.setTag(viewHolder);
        }
        final ReservationsAdapter.ViewHolder holder           = (ReservationsAdapter.ViewHolder) convertView.getTag();
        DecimalFormat decimalFormat = new DecimalFormat("00.00");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Reservation reservation = mReservations.get(position);
        Client      client      = mClients.get(position);
        Room        room        = mRooms.get(position);
        Hotel       hotel       = mHotels.get(position);
        RoomDetails roomDetails = mRoomDetails.get(position);

        Date        dateFrom    = null;
        Date        dateTo      = null;

        try {
            dateFrom = format.parse(reservation.getmDateFrom());
            dateTo   = format.parse(reservation.getmDateTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar dateFromCal    = Calendar.getInstance();
        dateFromCal.setTime(dateFrom);

        Calendar dateToCal  = Calendar.getInstance();
        dateToCal.setTime(dateTo);

        long diff = dateToCal.getTimeInMillis() - dateFromCal.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        double price = days * roomDetails.getmRoomPrice();

        holder.mNights.setText("Nights: " + days);
        holder.mClientNamesTxt.setText(client.getmFirstName() + " " + client.getmLastName());
        holder.mPrice.setText(decimalFormat.format(price) + "lv.");
        holder.mHotelNameTxt.setText(hotel.getmHotelName());
        String image    = hotel.getmImages().get(0);

        holder.mDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteListener.onDelete(position);
            }
        });

        if(image != null){
            DownloadImage downloadImage = new DownloadImage(mContext) {
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    holder.mHotelImageView.setImageBitmap(bitmap);
                }
            };

            try {
                downloadImage.execute(new URL(image));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
