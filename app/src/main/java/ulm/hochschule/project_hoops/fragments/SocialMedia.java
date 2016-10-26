package ulm.hochschule.project_hoops.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ulm.hochschule.project_hoops.R;


public class SocialMedia extends Fragment {


    // buttons
    private ImageButton ibtnFacebook ;
    private ImageButton ibtnTwitter;
    private ImageButton ibtnInstagram;
    private View layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          layout = inflater.inflate(R.layout.fragment_social_media, container, false);
          ibtnFacebook = (ImageButton) layout.findViewById(R.id.ibtn_facebook);
          ibtnInstagram = (ImageButton) layout.findViewById(R.id.ibtn_instagram);
          ibtnTwitter = (ImageButton) layout.findViewById(R.id.ibtn_twitter);
          ibtnInstagram = (ImageButton) layout.findViewById(R.id.ibtn_instagram);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ibtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFacebook();
            }
        });

        ibtnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                startTwitter();

            }
       });

        ibtnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInstagram();
            }
        });
    }



    public void startFacebook() {
        Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/bbu01/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.facebook.katana");
        //Intent intent2 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/bbu01/"));
        //intent2.setPackage("com.android.chrome");

        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/bbu01/")));
        }
    }





    public void startTwitter(){

        Uri uri = Uri.parse("twitter://user?user_id=1446432300");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.twitter.android");
        Uri uri2 = Uri.parse("https://twitter.com/ratiopharmulm?lang=de");
        //Intent intent2 = new Intent(Intent.ACTION_VIEW,uri2);
       // intent2.setPackage("com.android.chrome");

        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/ratiopharmulm?lang=de")));
        }
    }




    public void startInstagram(){
        Uri uri = Uri.parse("http://instagram.com/_u/ratiopharmulm");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        Uri uri2 = Uri.parse("https://www.instagram.com/ratiopharmulm/");
       // Intent intent2 = new Intent(Intent.ACTION_VIEW,uri2);
        //intent2.setPackage("com.android.chrome");

        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/ratiopharmulm/")));
        }
    }

}
