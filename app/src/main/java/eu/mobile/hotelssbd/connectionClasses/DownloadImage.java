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

            bitmap = loadImageFromStorage(fileName);

            if(bitmap != null)
                return bitmap;

            URLConnection conn = urls[0].openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());

            saveToInternalStorage(bitmap, fileName);

        } catch (Exception ex) {
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    private void saveToInternalStorage(Bitmap bitmapImage, String fileName){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/hotels");
        myDir.mkdirs();
        String fname = fileName + ".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromStorage(String fileName)
    {

        try {
            File f = new File("hotels", fileName);

            Bitmap b = null;
            if(f.exists())
                b = BitmapFactory.decodeStream(new FileInputStream(f));

            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
