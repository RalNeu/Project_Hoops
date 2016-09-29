package ulm.hochschule.project_hoops.tasks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Ralph on 29.09.2016.
 */
public class ImageLoader extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private int id;

    public ImageLoader(ImageView imageView, int id) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.id = id;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeResource(Resources.getSystem(), id);
       /* imageViewReference.get().post(new Runnable() {
            @Override
            public void run() {

            }
        });*/
        return bitmap;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}