package eu.mobile.hotelssbd.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import eu.mobile.hotelssbd.Models.RoomDetails;
import eu.mobile.hotelssbd.R;

/**
 * Created by stoycho.petrov on 13/04/2017.
 */

public class RoomsAdapter extends ArrayAdapter<RoomDetails> {

    private List<RoomDetails>       mRooms;
    private LayoutInflater          mLayoutInflater;

    public RoomsAdapter(@NonNull Context context, @NonNull List<RoomDetails> objects) {
        super(context, R.layout.item_room, objects);

        mLayoutInflater     = ((Activity)context).getLayoutInflater();
        mRooms              = objects;
    }

    private class ViewHolder{
        private TextView     mRoomDescription;
        private TextView     mRoomPrice;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView                                 =  mLayoutInflater.inflate(R.layout.item_room, null);
            RoomsAdapter.ViewHolder viewHolder          =  new RoomsAdapter.ViewHolder();

            viewHolder.mRoomDescription                 = (TextView) convertView.findViewById(R.id.description);
            viewHolder.mRoomPrice                       = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(viewHolder);
        }

        DecimalFormat decimalFormat = new DecimalFormat("00.00");
        final RoomsAdapter.ViewHolder holder           = (RoomsAdapter.ViewHolder) convertView.getTag();
        RoomDetails hotel                              = mRooms.get(position);

        holder.mRoomPrice.setText(decimalFormat.format(hotel.getmRoomPrice()) + " lv.");
        holder.mRoomDescription.setText(hotel.getmRoomDescription());

        return convertView;
    }
}
