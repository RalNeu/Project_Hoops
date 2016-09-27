package ulm.hochschule.project_hoops.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Ralph on 27.09.2016.
 */
public class SquareImage extends ImageView {

    private final double mScale = 1.0;

    public SquareImage (Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (width > (int)((mScale * height) + 0.5)) {
            width = (int)((mScale * height) + 0.5);
        } else {
            height = (int)((width / mScale) + 0.5);
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        );
    }

}
