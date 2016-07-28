package ulm.hochschule.project_hoops.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.activities.EditProfilActivity;


public class EditAvatarTab extends Fragment {

    private Button btnHat, btnEyes, btnHair, btnBeard, btnSkin, btnBody, btnSave, btnPrev, btnNext;
    private View layout;
    private ImageView imgHat, imgHead;

    private ArrayList<ImageView> hats = new ArrayList<>();


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
                System.out.println("test0");
            }
        });
        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test05");
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    imgHat.setBackgroundDrawable(getResources().getDrawable(R.drawable.avatarhat));
                }
        });

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //imgHat.setImageResource(R.drawable.avatarhat);
                imgHat.setBackgroundDrawable(getResources().getDrawable(R.drawable.avatarhat2));
            }
        });

    }

    public void changeItem(ImageView item){


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_edit_avatar_tab, container, false);
        btnHair = (Button) layout.findViewById(R.id.btnHair);
        btnEyes = (Button) layout.findViewById(R.id.btnEyes);
        btnPrev = (Button) layout.findViewById(R.id.btnPrev);
        btnNext = (Button) layout.findViewById(R.id.btnNext);
        imgHat = (ImageView) layout.findViewById(R.id.imageView6);
        imgHead = (ImageView) layout.findViewById(R.id.imageView5);

        //instatiateUiObjects();
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
}
