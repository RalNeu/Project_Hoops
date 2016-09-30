package ulm.hochschule.project_hoops.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.sonstige.BitmapResolver;

/**
 * TODO: document your custom view class.
 */
public class Achievement extends LinearLayout {

    private ImageView img_Emblem;
    private TextView tv_Description, tv_Title;

    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private View layout;

    int emblem = 0;
    String description = "???";
    String title = "???";

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

    public void setAchievement(int emblem, String description, String title) {

        this.emblem = emblem;
        this.description = description;
        this.title = title;
        mapUIObjects();
    }

    private void instantiateUIObjects() {
        img_Emblem = (ImageView) this.findViewById(R.id.img_Emblem);
        tv_Description = (TextView) this.findViewById(R.id.tv_Description);
        tv_Title = (TextView) this.findViewById(R.id.tv_Title);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
    }

    private void mapUIObjects() {
        int id = 0;
        switch (emblem) {
            case 1:
                id = R.drawable.achievement_bronze;
                break;
            case 2:
                id = R.drawable.achievement_silber;
                break;
            case 3:
                id = R.drawable.achievement_gold;
                break;
            default:
                id = R.drawable.achievement_grau_transparent;
                break;
        }
        img_Emblem.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), id, 150,150));

        tv_Description.setText(description);
        tv_Title.setText(title);
    }
}
