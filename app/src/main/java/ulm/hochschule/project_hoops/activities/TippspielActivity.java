package ulm.hochschule.project_hoops.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.TipTab;
import ulm.hochschule.project_hoops.objects.Card;
import ulm.hochschule.project_hoops.utilities.CardArrayAdapter;
import ulm.hochschule.project_hoops.utilities.ViewPagerAdapter;

/**
 * Created by Johann on 10.06.2016.
 */
public class TippspielActivity extends AppCompatActivity {

    /*
    private void changeFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.view_TipGame, f).commit();
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippspiel);
        //changeFragment(new TipTab());

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(0), "Tipps");
        adapter.addFrag(new TipTab(), "Spiel");
        viewPager.setAdapter(adapter);
    }

    public static class DummyFragment extends Fragment {
        int color;
        ListView listView;
        CardArrayAdapter cardArrayAdapter;

        public DummyFragment() {
        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color) {
            this.color = color;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.tippspiel_listview, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);

            listView = (ListView) view.findViewById(R.id.listView);

            listView.addHeaderView(new View(getActivity()));
            listView.addFooterView(new View(getActivity()));


            cardArrayAdapter = new CardArrayAdapter(getContext(), R.layout.listitem_mybet);

            //Liste von Einträgen
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < 4; i++) {
                Card card = new Card();
                cardArrayAdapter.add(card);
            }

            listView.setAdapter(cardArrayAdapter);
            return view;
        }
    }

}
