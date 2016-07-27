package ulm.hochschule.project_hoops.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import ulm.hochschule.project_hoops.R;


public class EditAvatarTab extends Fragment {

    private Button btnHat, btnEyes, btnHair, btnBeard, btnSkin, btnBody, btnSave;
    private View layout;

    //private OnFragmentInteractionListener mListener;

    public EditAvatarTab() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeItemsTab();
            }
        });
        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeItemsTab();
            }
        });

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_edit_avatar_tab, container, false);
        instatiateUiObjects();
        System.out.println("test");
        //btnHair.setOnClickListener(new View.OnClickListener() {
          //  @Override
        //public void onClick(View v) {
          //      openChangeItemsTab();
           // }
        //});
        //btnEyes.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View v) {
             //   openChangeItemsTab();
           // }
        //});

        return layout;
    }


    private void instatiateUiObjects() {

        btnHair = (Button) layout.findViewById(R.id.btnHair);
        btnEyes = (Button) layout.findViewById(R.id.btnEyes);

    }



    public void openChangeItemsTab() {
        changeFragment(new ChangeItemsTab());
    }

    private void changeFragment(Fragment f) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentPanel, f).addToBackStack( f.getTag() ).commit();
    }
}
