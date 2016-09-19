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
import java.sql.SQLException;
import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.activities.EditProfilActivity;
import ulm.hochschule.project_hoops.objects.AvatarItems;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;


public class EditAvatarTab extends Fragment {

    private Button btnHat, btnEyes, btnHair, btnBeard, btnSkin, btnBody, btnSave, btnPrev, btnNext, selectedBtn;
    private View layout;
    private ImageView imgHat, imgHair, imgEyes;
    private int hatIndex, eyesIndex, hairIndex, beardIndex, skinIndex, bodyIndex;

    private ArrayList<Integer> hats, hair, eyes, beard, skin, body;

    SqlManager sqlManager = SqlManager.getInstance();
    UserProfile uProfile = UserProfile.getInstance();
    AvatarItems aItems = AvatarItems.getInstance();

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
                        imgHat.setBackgroundResource(hats.get(++hatIndex));
                        handleArrowEnable(hats, hatIndex);
                        break;
                    case R.id.btnHair:
                        imgHair.setBackgroundResource(hair.get(++hairIndex));
                        handleArrowEnable(hair, eyesIndex);
                        break;
                    case R.id.btnEyes:
                        imgEyes.setBackgroundResource(eyes.get(++eyesIndex));
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
                        imgHat.setBackgroundResource(hats.get(--hatIndex));
                        handleArrowEnable(hats, hatIndex);
                        break;
                    case R.id.btnHair:
                        imgHair.setBackgroundResource(hair.get(--hairIndex));
                        handleArrowEnable(hair, eyesIndex);
                        break;
                    case R.id.btnEyes:
                        imgEyes.setBackgroundResource(eyes.get(--eyesIndex));
                        handleArrowEnable(eyes, eyesIndex);
                        break;
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemsString = "" + String.format("%02d", hatIndex) + String.format("%02d", eyesIndex) + String.format("%02d", hairIndex)
                        + String.format("%02d", beardIndex) + String.format("%02d", skinIndex) + String.format("%02d", bodyIndex);
                sqlManager.updateAvatarItems(itemsString, uProfile.getUsername());
                try {
                    ProfilTab.getInstance().updateAvatar();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                getActivity().finish();
            }
        });

    }

    public void disableButton(Button btn, int itemIndex) {
        if(selectedBtn != null)
            selectedBtn.setEnabled(true);
        btn.setEnabled(false);
        selectedBtn = btn;
    }

    public void handleArrowEnable(ArrayList list, int itemIndex) {
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
        btnPrev.setVisibility(View.INVISIBLE);
        btnPrev.setEnabled(false);
        btnNext.setVisibility(View.INVISIBLE);
        btnNext.setEnabled(false);

        btnHair.setEnabled(false);
        btnBeard.setEnabled(false);
        btnBody.setEnabled(false);
        btnSkin.setEnabled(false);

        beardIndex = 0;
        skinIndex = 0;
        bodyIndex = 0;

        try {
            setItemView();
        } catch(SQLException e) {
            e.printStackTrace();
        }

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
        btnSave = (Button) layout.findViewById(R.id.btnSave);
        imgHat = (ImageView) layout.findViewById(R.id.imgHat);
        imgHair = (ImageView) layout.findViewById(R.id.imgHair);
        imgEyes = (ImageView) layout.findViewById(R.id.imgEyes);
        hats = aItems.getList("hat");
        hair = aItems.getList("hair");
        eyes = aItems.getList("eyes");
    }

    private void setItemView() throws SQLException {
        /*imgHat.setBackgroundDrawable(aItems.getAccountItem("hat"));
        imgEyes.setBackgroundDrawable(aItems.getAccountItem("eyes"));
        imgHair.setBackgroundDrawable(aItems.getAccountItem("hair"));*/
        imgHat.setBackgroundResource(aItems.getAccountItemByID("hat"));
        imgEyes.setBackgroundResource(aItems.getAccountItemByID("eyes"));
        imgHair.setBackgroundResource(aItems.getAccountItemByID("hair"));
        hatIndex = aItems.getIndex("hat");
        hairIndex = aItems.getIndex("hair");
        eyesIndex = aItems.getIndex("eyes");
        beardIndex = aItems.getIndex("beard");
        skinIndex = aItems.getIndex("skin");
        bodyIndex = aItems.getIndex("body");
    }
}
