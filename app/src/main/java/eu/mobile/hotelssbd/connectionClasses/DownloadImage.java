package eu.mobile.hotelssbd.connectionClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by stoycho.petrov on 10/04/2017.
 */

public class DownloadImage extends AsyncTask<URL,Void,Bitmap> {

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bitmap;
        try {
            URLConnection conn = urls[0].openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception ex) {
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
