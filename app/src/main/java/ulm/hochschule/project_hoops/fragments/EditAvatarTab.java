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

    private Button btnHat, btnEyes, btnHair, btnBeard, btnSkin, btnBody, btnSave, btnPrev, btnNext, selectedBtn;
    private View layout;
    private ImageView imgHat, imgHead;
    private int itemIndex;

    private ArrayList<Drawable> hats, hair, eyes;


    public EditAvatarTab() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnHair);
                setSelectedCategory();
            }
        });
        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnEyes);
            }
        });
        btnHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnHat);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemIndex == hats.size()){
                    disableNext();
                    enablePrev();
                } else if ( itemIndex < hats.size()){
                    enableNext();
                    enablePrev();
                }
                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        imgHat.setBackgroundDrawable(hats.get(++itemIndex));

                        break;
                    case R.id.btnHair:
                        hair.get(itemIndex);
                        break;
                    case R.id.btnEyes:
                        eyes.get(itemIndex);
                        break;
                }
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemIndex == 0 ){
                    disablePrev();
                    enableNext();
                } else {
                    enableNext();
                    enablePrev();
                }
                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        imgHat.setBackgroundDrawable(hats.get(--itemIndex));

                        break;
                    case R.id.btnHair:
                        hair.get(itemIndex);
                        break;
                    case R.id.btnEyes:
                        eyes.get(itemIndex);
                        break;
                }
            }
        });

    }

    public void disableButton(Button btn) {
        if(selectedBtn != null)
            selectedBtn.setEnabled(true);
        btn.setEnabled(false);
        selectedBtn = btn;
        enableNext();
    }

    public void enableNext(){
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
    }

    public void enablePrev(){
        btnPrev.setVisibility(View.VISIBLE);
        btnPrev.setEnabled(true);
    }

    public void disableNext(){
        btnNext.setVisibility(View.INVISIBLE);
        btnNext.setEnabled(false);
    }

    public void disablePrev(){
        btnPrev.setVisibility(View.INVISIBLE);
        btnPrev.setEnabled(false);
    }

    public void setSelectedCategory() {
        itemIndex = 0;

    }

    public void nextItem(){


    }

    public void previousItem(){


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
        instantiateUiObjects();
        fillItemArrays();
        btnPrev.setVisibility(View.INVISIBLE);
        btnPrev.setEnabled(false);
        btnNext.setVisibility(View.INVISIBLE);
        btnNext.setEnabled(false);

        return layout;
    }


    private void instantiateUiObjects() {

        btnHair = (Button) layout.findViewById(R.id.btnHair);
        btnHat = (Button) layout.findViewById(R.id.btnHat);
        btnBeard = (Button) layout.findViewById(R.id.btnBeard);
        btnEyes = (Button) layout.findViewById(R.id.btnEyes);
        btnPrev = (Button) layout.findViewById(R.id.btnPrev);
        btnNext = (Button) layout.findViewById(R.id.btnNext);
        imgHat = (ImageView) layout.findViewById(R.id.imageView6);
        imgHead = (ImageView) layout.findViewById(R.id.imageView5);
        hats = new ArrayList<>();
        hair = new ArrayList<>();
        eyes = new ArrayList<>();
    }

    private void fillItemArrays() {
        hats.add(getResources().getDrawable(R.drawable.avatarhat));
        hats.add(getResources().getDrawable(R.drawable.avatarhat2));
    }
}
