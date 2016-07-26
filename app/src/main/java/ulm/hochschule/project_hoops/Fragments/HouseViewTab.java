package ulm.hochschule.project_hoops.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.TouchImageView;



public class HouseViewTab extends android.support.v4.app.Fragment {

    private Context context;
    private View layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_houseview, container, false);

        return layout;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        context = getContext();
        TouchImageView img = new TouchImageView(context);
        img.setImageResource(R.drawable.haus);
        img.setMaxZoom(4f);
        //setContentView(img);
    }
}