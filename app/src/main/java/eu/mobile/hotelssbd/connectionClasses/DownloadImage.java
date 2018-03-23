package eu.mobile.hotelssbd.connectionClasses;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by stoycho.petrov on 10/04/2017.
 */

public class DownloadImage extends AsyncTask<URL,Void,Bitmap> {

    private Context mContext;

    public DownloadImage(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bitmap;
        try {

            String url = urls[0].toString();
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));

            bitmap = getImageFromCache(fileName);

            if(bitmap != null)
                return bitmap;

            URLConnection conn = urls[0].openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());

            saveImageToCache(bitmap, fileName);

        } catch (Exception ex) {
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    private void saveImageToCache(Bitmap bitmap, String fileName){
        try {
            File file = File.createTempFile(fileName, ".png", mContext.getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFromCache(String fileName){
        Bitmap bitmap   = null;
        try {
            File cacheDir = mContext.getCacheDir();
            File[] files = cacheDir.listFiles();
            File image = null;

            for (File file : files) {
                if (file.getName().startsWith(fileName)) {
                    image = file;
                    break;
                }
            }

            if (image != null) {
                bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            }
        } catch (Exception e){}

        return bitmap;
    }
}
