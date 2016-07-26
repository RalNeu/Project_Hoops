package ulm.hochschule.project_hoops.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;

import ulm.hochschule.project_hoops.R;

/**
 * TODO: document your custom view class.
 */
public class Achievement extends LinearLayout {

    private ImageView img_Emblem;
    private TextView tv_Description, tv_Date;

    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private View layout;

    int emblem = 0;
    String description = "???";
    String date = "01.01.2000";


    public Achievement(Context context) {
        super(context);
        init(context);
        instantiateUIObjects();
        mapUIObjects();
    }

    public Achievement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        instantiateUIObjects();
        mapUIObjects();
    }

    public Achievement(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        instantiateUIObjects();
        mapUIObjects();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.compound_view_achievement, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setAchievement(int emblem, String description, String date) {

        this.emblem = emblem;
        this.description = description;
        this.date = date;
        mapUIObjects();
    }

    private void instantiateUIObjects() {
        img_Emblem = (ImageView) this.findViewById(R.id.img_Emblem);
        tv_Description = (TextView) this.findViewById(R.id.tv_Description);
        tv_Date = (TextView) this.findViewById(R.id.tv_Date);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
    }

    private void mapUIObjects() {
        switch (emblem) {
            case 0:
                img_Emblem.setImageResource(R.drawable.achievement_grau_transparent);
                break;
            case 1:
                img_Emblem.setImageResource(R.drawable.achievement_bronze);
                break;
            case 2:
                img_Emblem.setImageResource(R.drawable.achievement_silber);
                break;
            case 3:
                img_Emblem.setImageResource(R.drawable.achievement_gold);
                break;
        }

        tv_Description.setText(description);
        tv_Date.setText(date);
    }

}
