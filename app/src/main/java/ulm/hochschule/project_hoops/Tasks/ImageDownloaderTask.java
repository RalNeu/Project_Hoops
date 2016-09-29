package ulm.hochschule.project_hoops.tasks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 15.05.2016.
 */
public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private int id;

    public ImageDownloaderTask(ImageView imageView, int id) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.id = id;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        System.out.println("doInBack");
        return BitmapFactory.decodeResource(Resources.getSystem(), id);//downloadBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    System.out.println("id = " + id);
                } else {
                    System.out.println("id1 = " + id);
                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.ic_menu_camera);
                   // imageView.setImageDrawable(placeholder);
                        imageView.setImageResource(id);
                    //imageView.setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), id));
                }
            }

        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}