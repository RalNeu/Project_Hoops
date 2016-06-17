package ulm.hochschule.project_hoops.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class testFragment extends Fragment {


    public testFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tabviewtest, container, false);

        return layout;
    }

}
