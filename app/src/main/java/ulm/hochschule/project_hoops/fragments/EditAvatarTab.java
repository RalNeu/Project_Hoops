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


import java.lang.reflect.Array;
import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.activities.EditProfilActivity;


public class EditAvatarTab extends Fragment {

    private Button btnHat, btnEyes, btnHair, btnBeard, btnSkin, btnBody, btnSave, btnPrev, btnNext, selectedBtn;
    private View layout;
    private ImageView imgHat, imgHead, imgHair, imgEyes;
    private int hatIndex, eyesIndex, hairIndex, beardIndex, skinIndex, bodyIndex;

    private ArrayList<Drawable> hats, hair, eyes, beard, skin, body;


    public EditAvatarTab() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnHair, hairIndex);
                handleArrowEnable(hair, hairIndex);
            }
        });
        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnEyes, eyesIndex);
                handleArrowEnable(eyes, eyesIndex);
            }
        });
        btnHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(hats, hatIndex);
                disableButton(btnHat, hatIndex);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        imgHat.setBackgroundDrawable(hats.get(++hatIndex));
                        handleArrowEnable(hats, hatIndex);
                        break;
                    case R.id.btnHair:
                        imgHair.setBackgroundDrawable(hair.get(++hairIndex));
                        handleArrowEnable(hair, eyesIndex);
                        break;
                    case R.id.btnEyes:
                        imgEyes.setBackgroundDrawable(eyes.get(++eyesIndex));
                        handleArrowEnable(eyes, eyesIndex);
                        break;
                }

            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        imgHat.setBackgroundDrawable(hats.get(--hatIndex));
                        handleArrowEnable(hats, hatIndex);
                        break;
                    case R.id.btnHair:
                        imgHair.setBackgroundDrawable(hair.get(--hairIndex));
                        handleArrowEnable(hair, eyesIndex);
                        break;
                    case R.id.btnEyes:
                        imgEyes.setBackgroundDrawable(eyes.get(--eyesIndex));
                        handleArrowEnable(eyes, eyesIndex);
                        break;
                    /*case R.id.btnBeard:
                        imgBeard.setBackgroundDrawable(beard.get(--beardIndex));
                        handleArrowEnable(eyes, eyesIndex);
                        break;
                    */
                }


            }
        });

    }

    public void disableButton(Button btn, int itemIndex) {
        if(selectedBtn != null)
            selectedBtn.setEnabled(true);
        btn.setEnabled(false);
        selectedBtn = btn;
    }

    public void handleArrowEnable(ArrayList<Drawable> list, int itemIndex) {
        if (itemIndex == list.size()-1)
            disableNext();
        else
            enableNext();

        if(itemIndex == 0 )
            disablePrev();
        else
            enablePrev();
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

        btnHair.setEnabled(false);
        btnBeard.setEnabled(false);
        btnBody.setEnabled(false);
        btnSkin.setEnabled(false);
        hatIndex = 0;
        eyesIndex = 0;
        return layout;
    }


    private void instantiateUiObjects() {

        btnHair = (Button) layout.findViewById(R.id.btnHair);
        btnHat = (Button) layout.findViewById(R.id.btnHat);
        btnBeard = (Button) layout.findViewById(R.id.btnBeard);
        btnEyes = (Button) layout.findViewById(R.id.btnEyes);
        btnSkin = (Button) layout.findViewById(R.id.btnSkin);
        btnBody = (Button) layout.findViewById(R.id.btnBody);
        btnPrev = (Button) layout.findViewById(R.id.btnPrev);
        btnNext = (Button) layout.findViewById(R.id.btnNext);
        imgHat = (ImageView) layout.findViewById(R.id.imgHat);
        imgHead = (ImageView) layout.findViewById(R.id.imgHead);
        imgHair = (ImageView) layout.findViewById(R.id.imgHair);
        imgEyes = (ImageView) layout.findViewById(R.id.imgEyes);
        hats = new ArrayList<>();
        hair = new ArrayList<>();
        eyes = new ArrayList<>();
    }

    private void fillItemArrays() {
        hats.add(getResources().getDrawable(R.drawable.avatarhat));
        hats.add(getResources().getDrawable(R.drawable.avatarhat2));
        eyes.add(getResources().getDrawable(R.drawable.avatareyes));
        eyes.add(getResources().getDrawable(R.drawable.avatareyes2));
        hair.add(getResources().getDrawable(R.drawable.avatarhairunderhat));
    }
}
