package ulm.hochschule.project_hoops.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import Enums.AvatarItems;
import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.AvatarItemDescription;
import ulm.hochschule.project_hoops.utilities.UserProfile;
import ulm.hochschule.project_hoops.views.ItemView;

public class AccountshopActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AvatarItems currentSelected = AvatarItems.EYES;
    private LinearLayout content_Items;
    private TextView tv_Coins;

    ArrayList<AvatarItemDescription> hat_ids, eyes_ids, skins_ids, bodies_ids, background_ids, mouths_ids;

    private void initIds() {
        hat_ids = new ArrayList<AvatarItemDescription>();
        eyes_ids = new ArrayList<AvatarItemDescription>();
        skins_ids = new ArrayList<AvatarItemDescription>();
        bodies_ids = new ArrayList<AvatarItemDescription>();
        background_ids = new ArrayList<AvatarItemDescription>();
        mouths_ids = new ArrayList<AvatarItemDescription>();

        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_1,400,"Blonde Zöpfe",0,0));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_2,600,"Schwarze Locken",0,1));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_4,1250,"Hasenohren",0,3));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_5,1250,"Teufelshörner",0,4));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_6,1750,"Schlafmütze",0,5));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_7,1250,"Alienantennen",0,6));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_8,1750,"Zauberer",0,7));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_9,1000,"Partyhut",0,8));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_10,1000,"Wintermütze",0,9));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_12,2250,"Vikingerhelm",0,11));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_13,1250,"Blonde Frisur",0,12));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_14,1250,"Rote Frisur",0,13));
        hat_ids.add(new AvatarItemDescription(R.drawable.avatar_hat_16,1250,"Schwarze Frisur",0,15));

        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes4,500,"Verschmitzte Augen",1,3));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes5,750,"Cyborgaugen",1,4));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes6,750,"Haselnussbraune Augen",1,5));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes7,1000,"Roboteraugen",1,6));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes8,600,"Normale Augen",1,7));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes9,1250,"Insektenaugen",1,8));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes10,1250,"Zyklopenauge",1,9));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes11,800,"Animeaugen",1,10));
        eyes_ids.add(new AvatarItemDescription(R.drawable.avatareyes12,1000,"Sonnenbrille",1,11));

        skins_ids.add(new AvatarItemDescription(R.drawable.avatar_skin_2,1500,"Alienfarbe",4,1));

        bodies_ids.add(new AvatarItemDescription(R.drawable.avatar_body_1,750,"Rote Farbe, schwarze Schrift",5,0));
        bodies_ids.add(new AvatarItemDescription(R.drawable.avatar_body_3,750,"Blaue Farbe, schwarze Schrift",5,2));
        bodies_ids.add(new AvatarItemDescription(R.drawable.avatar_body_4,750,"Rote Farbe, orangene Schrift",5,3));
        bodies_ids.add(new AvatarItemDescription(R.drawable.avatar_body_5,750,"Grüne Farbe, orangene Schrift",5,4));
        bodies_ids.add(new AvatarItemDescription(R.drawable.avatar_body_6,750,"Blaue Farbe, orangene Schrift",5,5));
        bodies_ids.add(new AvatarItemDescription(R.drawable.avatar_body_8,750,"Weiße Farbe",5,7));

        background_ids.add(new AvatarItemDescription(R.drawable.avatar_background_1,750,"Orange auf Grün",0,0));
        background_ids.add(new AvatarItemDescription(R.drawable.avatar_background_2,750,"Orange auf Blau",0,1));
        background_ids.add(new AvatarItemDescription(R.drawable.avatar_background_4,1500,"Stadtleben",0,3));

        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_1,750,"Irres Lachen",3,0));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_2,1000,"Verschlossen",3,1));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_3,1250,"Holzzähne",3,2));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_4,750,"Knutschmund",3,3));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_5,750,"Zunge raus",3,4));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_6,1250,"Vampir mit Schnauzer",3,5));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_7,1000,"Verschmitztes Lächeln",3,6));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_8,1250,"Monsterfänge",3,7));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_9,750,"Unglücklich",3,8));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_10,850,"Rufend",3,9));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_11,850,"Zähneknirschend",3,10));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_13,1250,"Vollbart",3,12));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_15,1250,"Playboy",3,14));
        mouths_ids.add(new AvatarItemDescription(R.drawable.avatar_mouth_16,1250,"Vergrauter Bart",3,15));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountshop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_Coins = (TextView) findViewById(R.id.tv_Coins);
        content_Items = (LinearLayout) findViewById(R.id.contentItems);

        initIds();
        changeItems();
        updateCoinCounter();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void changeItems() {
        content_Items.removeAllViews();
        ArrayList<AvatarItemDescription> cur = new ArrayList<>();
        switch (currentSelected) {
            case HATS:
                cur = hat_ids;
                break;
            case EYES:
                cur = eyes_ids;
                break;
            case SKINS:
                cur = skins_ids;
                break;
            case BODIES:
                cur = bodies_ids;
                break;
            case BACKGROUNDS:
                cur = background_ids;
                break;
            case MOUTHS:
                cur = mouths_ids;
                break;
        }

        for(int i = 0;i< cur.size();i++) {
            addItem(cur.get(i));
        }

    }

    private void addItem(AvatarItemDescription ad) {

        ItemView i = new ItemView(getApplicationContext());
        i.setItem(ad, tv_Coins);
        content_Items.addView(i);
    }

    private void updateCoinCounter() {
        tv_Coins.setText("" + UserProfile.getInstance().getCoins());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accountshop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.backBtn) {

        } else if (id == R.id.nav_eyes) {
            currentSelected = AvatarItems.EYES;
        } else if (id == R.id.nav_mouths) {
            currentSelected = AvatarItems.MOUTHS;
        } else if (id == R.id.nav_hats) {
            currentSelected = AvatarItems.HATS;
        } else if (id == R.id.nav_skins) {
            currentSelected = AvatarItems.SKINS;
        } else if (id == R.id.nav_bodies) {
            currentSelected = AvatarItems.BODIES;
        } else if (id == R.id.nav_backgrounds) {
            currentSelected = AvatarItems.BACKGROUNDS;
        }
        changeItems();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
