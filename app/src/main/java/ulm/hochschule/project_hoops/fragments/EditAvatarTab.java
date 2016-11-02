package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.AvatarItems;
import ulm.hochschule.project_hoops.sonstige.BitmapResolver;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;


public class EditAvatarTab extends Fragment {

    private Button btnHat, btnEyes, btnBackground, btnMouth, btnSkin, btnBody, btnSave, btnPrev, btnNext, selectedBtn;
    private View layout;
    private ImageView imgHat, imgBackground, imgEyes, imgMouth, imgSkin, imgBody;
    private int hatIndex, eyesIndex, backgroundIndex, mouthIndex, skinIndex, bodyIndex;

    private ArrayList<Integer> hats, background, eyes, mouth, skin, body;

    SqlManager sqlManager = SqlManager.getInstance();
    UserProfile uProfile = UserProfile.getInstance();
    AvatarItems aItems = AvatarItems.getInstance();

    public EditAvatarTab() {

        // Required empty public constructor
    }


    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnBackground);
                handleArrowEnable(2, backgroundIndex);
            }
        });
        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton(btnEyes);
                handleArrowEnable(1, eyesIndex);
            }
        });
        btnHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(0, hatIndex);
                disableButton(btnHat);
            }
        });
        btnMouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(3, mouthIndex);
                disableButton(btnMouth);
            }
        });
        btnSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(4, skinIndex);
                disableButton(btnSkin);
            }
        });
        btnBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowEnable(5, bodyIndex);
                disableButton(btnBody);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        hatIndex = getNextIdx(0, hatIndex);
                        imgHat.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), hats.get(hatIndex), 300,300));
                        //imgHat.setBackgroundResource(hats.get(hatIndex));
                        handleArrowEnable(0, hatIndex);
                        break;
                    case R.id.btnBackground:
                        backgroundIndex = getNextIdx(2, backgroundIndex);
                        imgBackground.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), background.get(backgroundIndex), 300,300));
                        //imgBackground.setBackgroundResource(0);
                        handleArrowEnable(2, backgroundIndex);
                        break;
                    case R.id.btnEyes:
                        eyesIndex = getNextIdx(1, eyesIndex);
                        imgEyes.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), eyes.get(eyesIndex), 300,300));
                        //imgEyes.setBackgroundResource(eyes.get(eyesIndex));
                        handleArrowEnable(1, eyesIndex);
                        break;
                    case R.id.btnMouth:
                        mouthIndex = getNextIdx(3, mouthIndex);
                        imgMouth.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), mouth.get(mouthIndex), 300,300));
                        //imgMouth.setBackgroundResource(mouth.get(mouthIndex));
                        handleArrowEnable(3, mouthIndex);
                        break;
                    case R.id.btnSkin:
                        skinIndex = getNextIdx(4, skinIndex);
                        imgSkin.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), skin.get(skinIndex), 300,300));
                        // imgSkin.setBackgroundResource(skin.get(skinIndex));
                        handleArrowEnable(4, skinIndex);
                        break;
                    case R.id.btnBody:
                        bodyIndex = getNextIdx(5, bodyIndex);
                        imgBody.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), body.get(bodyIndex), 300,300));
                        //imgBody.setBackgroundResource(body.get(bodyIndex));
                        handleArrowEnable(5, bodyIndex);
                        break;
                }

            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectedBtn.getId()) {
                    case R.id.btnHat:
                        hatIndex = getPrevIdx(0, hatIndex);
                        imgHat.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), hats.get(hatIndex), 300,300));
                        //imgHat.setBackgroundResource(hats.get(hatIndex));
                        handleArrowEnable(0, hatIndex);
                        break;
                    case R.id.btnBackground:
                        backgroundIndex = getPrevIdx(2, backgroundIndex);
                        if(backgroundIndex == -1) {
                            imgBackground.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), 0, 300,300));
                            //imgBackground.setBackgroundResource(0);
                        } else {
                            imgBackground.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), background.get(backgroundIndex), 300,300));
                            //imgBackground.setBackgroundResource(background.get(backgroundIndex));
                        }
                        handleArrowEnable(2, backgroundIndex);
                        break;
                    case R.id.btnEyes:
                        eyesIndex = getPrevIdx(1, eyesIndex);
                        imgEyes.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), eyes.get(eyesIndex), 300,300));
                        //imgEyes.setBackgroundResource(eyes.get(eyesIndex));
                        handleArrowEnable(1, eyesIndex);
                        break;
                    case R.id.btnMouth:
                        mouthIndex = getPrevIdx(3, mouthIndex);
                        imgMouth.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), mouth.get(mouthIndex), 300,300));
                        //imgMouth.setBackgroundResource(mouth.get(mouthIndex));
                        handleArrowEnable(3, mouthIndex);
                        break;
                    case R.id.btnSkin:
                        skinIndex = getPrevIdx(4, skinIndex);
                        imgSkin.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), skin.get(skinIndex), 300,300));
                       // imgSkin.setBackgroundResource(skin.get(skinIndex));
                        handleArrowEnable(4, skinIndex);
                        break;
                    case R.id.btnBody:
                        bodyIndex = getPrevIdx(5, bodyIndex);
                        imgBody.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), body.get(bodyIndex), 300,300));
                        //imgBody.setBackgroundResource(body.get(bodyIndex));
                        handleArrowEnable(5, bodyIndex);
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

    private int getNextIdx(int item, int idx) {
        for(int i = idx + 1;i< uProfile.getMaxItems(item); i++) {
            if(uProfile.getItemAt(item, i) == 'y') {
                idx = i;
                i = uProfile.getMaxItems(item);
            }
        }
        return idx;
    }

    private int getPrevIdx(int item, int idx) {
        boolean found = false;
        for(int i = idx - 1;i >= 0;i--) {
            if(uProfile.getItemAt(item, i) == 'y') {
                idx = i;
                i = -1;
                found = true;
            }
        }

        if(!found && item == 2) {
            idx = -1;
        }

        return idx;
    }

    public void save() {
                String itemsString = "" + String.format("%02d", hatIndex) + String.format("%02d", eyesIndex) + String.format("%02d", backgroundIndex)
                        + String.format("%02d", mouthIndex) + String.format("%02d", skinIndex) + String.format("%02d", bodyIndex);
                sqlManager.updateAvatarItems(itemsString, uProfile.getUsername());
                try {
                    ProfilTab.getInstance().updateAvatar();
                    AchievementHandler.getInstance().performEvent(9, 1, getActivity());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ServerException e) {
                    e.printStackTrace();
                }
    }


    public void disableButton(Button btn) {
        if(selectedBtn != null)
            selectedBtn.setEnabled(true);
        btn.setEnabled(false);
        selectedBtn = btn;
    }

    public void handleArrowEnable(int item , int itemIndex) {

        if (itemIndex == uProfile.getHighestItemAvailable(item))
            disableNext();
        else
            enableNext();

        //Bei hats kann noch 1 Schritt weiter zurückgegangen werden um den Hut zu entfernen
        if(item == 2) {
            if(itemIndex == -1 )
                disablePrev();
            else
                enablePrev();
        } else {
            if(itemIndex == uProfile.getLowestItemAvailable(item) )
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

        btnBackground = (Button) layout.findViewById(R.id.btnBackground);
        btnHat = (Button) layout.findViewById(R.id.btnHat);
        btnMouth = (Button) layout.findViewById(R.id.btnMouth);
        btnEyes = (Button) layout.findViewById(R.id.btnEyes);
        btnSkin = (Button) layout.findViewById(R.id.btnSkin);
        btnBody = (Button) layout.findViewById(R.id.btnBody);

        btnPrev = (Button) layout.findViewById(R.id.btnPrev);
        btnNext = (Button) layout.findViewById(R.id.btnNext);
        btnSave = (Button) layout.findViewById(R.id.btnSave);

        imgHat = (ImageView) layout.findViewById(R.id.imgHat);
        imgBackground = (ImageView) layout.findViewById(R.id.imgBackground);
        imgEyes = (ImageView) layout.findViewById(R.id.imgEyes);
        imgMouth = (ImageView) layout.findViewById(R.id.imgMouth);
        imgSkin = (ImageView) layout.findViewById(R.id.imgSkin);
        imgBody = (ImageView) layout.findViewById(R.id.imgBody);

        hats = aItems.getList("hat");
        background = aItems.getList("background");
        eyes = aItems.getList("eyes");
        mouth = aItems.getList("mouth");
        skin = aItems.getList("skin");
        body = aItems.getList("body");
    }

    private void setItemView() throws SQLException {
        imgHat.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), aItems.getAccountItemByID("hat"),300,300));
        imgBackground.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), aItems.getAccountItemByID("background"), 300, 300));
        imgEyes.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), aItems.getAccountItemByID("eyes"), 300, 300));
        imgMouth.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), aItems.getAccountItemByID("mouth"), 300, 300));
        imgSkin.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), aItems.getAccountItemByID("skin"), 300, 300));
        imgBody.setImageBitmap(BitmapResolver.decodeSampledBitmapFromResource(getResources(), aItems.getAccountItemByID("body"), 300, 300));

        hatIndex = aItems.getIndex("hat");
        backgroundIndex = aItems.getIndex("background");
        eyesIndex = aItems.getIndex("eyes");
        mouthIndex = aItems.getIndex("mouth");
        skinIndex = aItems.getIndex("skin");
        bodyIndex = aItems.getIndex("body");
    }
}
