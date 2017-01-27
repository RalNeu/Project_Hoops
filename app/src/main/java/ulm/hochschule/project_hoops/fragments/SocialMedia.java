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
import android.widget.Button;
import android.widget.ImageButton;

import ulm.hochschule.project_hoops.R;


public class SocialMedia extends Fragment {


    // buttons
    private Button ibtnFacebook ;
    private Button ibtnTwitter;
    private Button ibtnInstagram;
    private View layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          layout = inflater.inflate(R.layout.fragment_social_media, container, false);
          ibtnFacebook = (Button) layout.findViewById(R.id.ibtn_facebook);
          ibtnInstagram = (Button) layout.findViewById(R.id.ibtn_instagram);
          ibtnTwitter = (Button) layout.findViewById(R.id.ibtn_twitter);
          ibtnInstagram = (Button) layout.findViewById(R.id.ibtn_instagram);
        return layout;
    }

    //OnClicklistener erstellt
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


//Öffnet Facebook im Browser oder App
    public void startFacebook() {
        Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/bbu01/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.facebook.katana");

        try {
            startActivity(intent);
            // Wenn die Facebook App nicht gefunden wurde wird im Webbrowser die Seite geöffnet
        }catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/bbu01/")));
        }
    }




//Öffnet Twitter im Browser oder App
    public void startTwitter(){

        Uri uri = Uri.parse("twitter://user?user_id=1446432300");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.twitter.android");
        Uri uri2 = Uri.parse("https://twitter.com/ratiopharmulm?lang=de");

        try {
            startActivity(intent);
            // Wenn die Twitter App nicht gefunden wurde wird im Webbrowser die Seite geöffnet
        }catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/ratiopharmulm?lang=de")));
        }
    }



    //Öffnet Instagramm im Browser oder App
    public void startInstagram(){
        Uri uri = Uri.parse("http://instagram.com/_u/ratiopharmulm");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        Uri uri2 = Uri.parse("https://www.instagram.com/ratiopharmulm/");


        try {
            startActivity(intent);
            // Wenn die Instagramm App nicht gefunden wurde wird im Webbrowser die Seite geöffnet
        }catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/ratiopharmulm/")));
        }
    }

}
