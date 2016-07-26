package ulm.hochschule.project_hoops.fragments;

/**
 * Created by Zoll on 26.07.2016.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.TouchImageView;

public class StadiumViewTab extends Fragment{

    private Context context;
    private View layout;
    private Button btn_switchMap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_stadiumview, container, false);
        btn_switchMap = (Button) layout.findViewById(R.id.btn_switchMap2);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_switchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new HouseViewTab();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentPanel, f).addToBackStack(f.getTag()).commit();

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = getContext();
        TouchImageView img = new TouchImageView(context);
        img.setImageResource(R.drawable.stadium);
        img.setMaxZoom(4f);

    }
}
