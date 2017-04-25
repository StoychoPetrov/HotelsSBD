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
import java.util.List;

import eu.mobile.hotelssbd.connectionClasses.DownloadImage;
import eu.mobile.hotelssbd.models.Hotel;
import eu.mobile.hotelssbd.R;

/**
 * Created by stoycho.petrov on 10/04/2017.
 */

public class HotelsAdapter extends ArrayAdapter<Hotel> {

    private Context                  mContext;
    private List<Hotel>              mHotels;
    private LayoutInflater           mLayoutInflater;

    public HotelsAdapter(@NonNull Context context, @NonNull List<Hotel> hotels) {
        super(context, R.layout.item_hotel, hotels);

        mLayoutInflater     = ((Activity)context).getLayoutInflater();
        mContext            = context;
        mHotels             = hotels;
    }

    private class ViewHolder{
        private ImageView   mHotelImageView;
        private TextView    mHotelNameTxt;
        private TextView    mDescriptionTxt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView                                 =  mLayoutInflater.inflate(R.layout.item_hotel, null);
            ViewHolder viewHolder                       =  new ViewHolder();

            viewHolder.mHotelImageView                  = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.mHotelNameTxt                    = (TextView)  convertView.findViewById(R.id.name);
            viewHolder.mDescriptionTxt                  = (TextView)  convertView.findViewById(R.id.description);

            convertView.setTag(viewHolder);
        }

        final ViewHolder  holder           = (ViewHolder) convertView.getTag();
        Hotel       hotel            = mHotels.get(position);

        holder.mHotelNameTxt.setText(hotel.getmHotelName());
        holder.mDescriptionTxt.setText(hotel.getmHotelDescription());
        String image    = hotel.getmImages().get(0);
        if(image != null) {
            DownloadImage downloadImage = new DownloadImage() {
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
