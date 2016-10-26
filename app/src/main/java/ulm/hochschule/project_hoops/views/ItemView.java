package ulm.hochschule.project_hoops.views;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.activities.AccountshopActivity;
import ulm.hochschule.project_hoops.objects.AvatarItemDescription;
import ulm.hochschule.project_hoops.sonstige.BitmapResolver;
import ulm.hochschule.project_hoops.tasks.ImageDownloaderTask;
import ulm.hochschule.project_hoops.tasks.ImageLoader;
import ulm.hochschule.project_hoops.utilities.Notificator;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Ralph on 27.09.2016.
 */
public class ItemView extends LinearLayout {

    private SquareImage img_Item;
    private TextView tv_ItemDesciption, tv_ItemPrice;
    private ChargingButton btn_Buy;
    private Notificator nf;

    public ItemView(Context context) {
        super(context);
        init(context);
        instantiateUIObjects();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        instantiateUIObjects();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        instantiateUIObjects();
    }

    private void instantiateUIObjects() {
        img_Item = (SquareImage) this.findViewById(R.id.img_Item);
        tv_ItemDesciption = (TextView) this.findViewById(R.id.tv_ItemDescription);
        tv_ItemPrice = (TextView) this.findViewById(R.id.tv_ItemPrice);
        btn_Buy = (ChargingButton) this.findViewById(R.id.btn_Buy);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_view, this);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                return false;
            }
        });
    }

    public void setObserver(Observer obs) {
        nf = new Notificator();
        nf.addObserver(obs);
    }

    public void checkIfBought(AvatarItemDescription ad) {

        btn_Buy.setEnabled(!ad.getBought());

        if (ad.getBought()) {
            btn_Buy.disableOrange();
            btn_Buy.setBackgroundResource(R.drawable.haeckchen_full);
            btn_Buy.setText("Gekauft!");
            btn_Buy.setTextColor(Color.RED);
        }
    }



   public void setItem(AvatarItemDescription ad) {
        final AvatarItemDescription avd = ad;
        //ImageDownloaderTask il = new ImageDownloaderTask(img_Item, ad.getId());
        //il.execute();

        img_Item.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), ad.getId(), 150,150));
        //
        tv_ItemDesciption.setText(ad.getDescription());
        tv_ItemPrice.setText("" + ad.getPrice());

        btn_Buy.setTask(new Runnable() {
            @Override
            public void run() {
                UserProfile.getInstance().buyItem(avd.getItem(), avd.getIdx(), avd.getPrice());
                checkIfBought(avd);
                nf.notifObs();
            }
        });

        checkIfBought(ad);
        btn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    public void onTouch(OnTouchListener otl) {
        btn_Buy.setOnTouchListener(otl);
    }


}
