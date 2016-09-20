package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import java.sql.SQLException;
import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.AvatarItems;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;


public class EditAvatarTab extends Fragment {

    private Button btnHat, btnEyes, btnHair, btnMouth, btnSkin, btnBody, btnSave, btnPrev, btnNext, selectedBtn;
    private View layout;
    private ImageView imgHat, imgHair, imgEyes, imgMouth, imgSkin, imgBody;
    private int hatIndex, eyesIndex, hairIndex, mouthIndex, skinIndex, bodyIndex;

    private ArrayList<Integer> hats, hair, eyes, mouth, skin, body;

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
                disableButton(btnHair);
                handleArrowEnable(hair, hairIndex);
            }
        });
        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnEyes);
                handleArrowEnable(eyes, eyesIndex);
            }
        });
        btnHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(hats, hatIndex);
                disableButton(btnHat);
            }
        });
        btnMouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(mouth, mouthIndex);
                disableButton(btnMouth);
            }
        });
        btnSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(skin, skinIndex);
                disableButton(btnSkin);
            }
        });
        btnBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(body, bodyIndex);
                disableButton(btnBody);
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
                    case R.id.btnMouth:
                        imgMouth.setBackgroundResource(mouth.get(++mouthIndex));
                        handleArrowEnable(mouth, mouthIndex);
                        break;
                    case R.id.btnSkin:
                        imgSkin.setBackgroundResource(skin.get(++skinIndex));
                        handleArrowEnable(skin, skinIndex);
                        break;
                    case R.id.btnBody:
                        imgBody.setBackgroundResource(body.get(++bodyIndex));
                        handleArrowEnable(body, bodyIndex);
                        break;
                }

            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        if(hatIndex == 0) {
                            imgHat.setBackgroundResource(0);
                            hatIndex--;
                        } else {
                            imgHat.setBackgroundResource(hats.get(--hatIndex));
                        }
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
                    case R.id.btnMouth:
                        imgMouth.setBackgroundResource(mouth.get(--mouthIndex));
                        handleArrowEnable(mouth, mouthIndex);
                        break;
                    case R.id.btnSkin:
                        imgSkin.setBackgroundResource(skin.get(--skinIndex));
                        handleArrowEnable(skin, skinIndex);
                        break;
                    case R.id.btnBody:
                        imgBody.setBackgroundResource(body.get(--bodyIndex));
                        handleArrowEnable(body, bodyIndex);
                        break;
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                getActivity().finish();
            }
        });

    }

    public void save() {
                String itemsString = "" + String.format("%02d", hatIndex) + String.format("%02d", eyesIndex) + String.format("%02d", hairIndex)
                        + String.format("%02d", mouthIndex) + String.format("%02d", skinIndex) + String.format("%02d", bodyIndex);
                sqlManager.updateAvatarItems(itemsString, uProfile.getUsername());
                try {
                    ProfilTab.getInstance().updateAvatar();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        String itemsString = "" + String.format("%02d", hatIndex) + String.format("%02d", eyesIndex) + String.format("%02d", hairIndex)
                + String.format("%02d", beardIndex) + String.format("%02d", skinIndex) + String.format("%02d", bodyIndex);
        sqlManager.updateAvatarItems(itemsString, uProfile.getUsername());
        try {
            ProfilTab.getInstance().updateAvatar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disableButton(Button btn) {
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

        //Bei hats kann noch 1 Schritt weiter zurückgegangen werden um den Hut zu entfernen
        if(list == hats) {
            if(itemIndex == -1 )
                disablePrev();
            else
                enablePrev();
        } else {
            if(itemIndex == 0 )
                disablePrev();
            else
                enablePrev();
        }
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
        btnMouth = (Button) layout.findViewById(R.id.btnMouth);
        btnEyes = (Button) layout.findViewById(R.id.btnEyes);
        btnSkin = (Button) layout.findViewById(R.id.btnSkin);
        btnBody = (Button) layout.findViewById(R.id.btnBody);

        btnPrev = (Button) layout.findViewById(R.id.btnPrev);
        btnNext = (Button) layout.findViewById(R.id.btnNext);
        btnSave = (Button) layout.findViewById(R.id.btnSave);

        imgHat = (ImageView) layout.findViewById(R.id.imgHat);
        imgHair = (ImageView) layout.findViewById(R.id.imgHair);
        imgEyes = (ImageView) layout.findViewById(R.id.imgEyes);
        imgMouth = (ImageView) layout.findViewById(R.id.imgMouth);
        imgSkin = (ImageView) layout.findViewById(R.id.imgSkin);
        imgBody = (ImageView) layout.findViewById(R.id.imgBody);

        hats = aItems.getList("hat");
        hair = aItems.getList("hair");
        eyes = aItems.getList("eyes");
        mouth = aItems.getList("mouth");
        skin = aItems.getList("skin");
        body = aItems.getList("body");
    }

    private void setItemView() throws SQLException {
        imgHat.setBackgroundResource(aItems.getAccountItemByID("hat"));
        imgHair.setBackgroundResource(aItems.getAccountItemByID("hair"));
        imgEyes.setBackgroundResource(aItems.getAccountItemByID("eyes"));
        imgMouth.setBackgroundResource(aItems.getAccountItemByID("mouth"));
        imgSkin.setBackgroundResource(aItems.getAccountItemByID("skin"));
        imgBody.setBackgroundResource(aItems.getAccountItemByID("body"));

        hatIndex = aItems.getIndex("hat");
        hairIndex = aItems.getIndex("hair");
        eyesIndex = aItems.getIndex("eyes");
        mouthIndex = aItems.getIndex("mouth");
        skinIndex = aItems.getIndex("skin");
        bodyIndex = aItems.getIndex("body");
    }
}
