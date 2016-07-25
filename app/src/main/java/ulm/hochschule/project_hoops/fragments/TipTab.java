package ulm.hochschule.project_hoops.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.BettingGame;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipTab extends Fragment {

    private View layout;

    public TipTab() {
        // Required empty public constructor
    }

    private void changeFragment(Fragment f) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.replace(R.id.view_TipGame, f).addToBackStack( f.getTag() ).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_tip_game, container, false);

        Button btn_Vote = (Button) layout.findViewById(R.id.btn_Vote);

        final ImageView img_View = (ImageView) layout.findViewById(R.id.img_Team);

        img_View.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                img_View.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                img_View.getLayoutParams().height = img_View.getLayoutParams().width;
               // img_View.requestLayout();
            }
        });

        btn_Vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            changeFragment(new fragment_Send_Tip());

            }
        });
        return layout;
    }

}
