package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 10.06.2016.
 */
public class MyBetTab extends Fragment {

    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.mybet, container, false);
        return layout;
    }
}
