package ulm.hochschule.project_hoops.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Calendar;

import org.apache.commons.lang3.ObjectUtils;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.ChatFragment;
import ulm.hochschule.project_hoops.fragments.MyWebView;
import ulm.hochschule.project_hoops.fragments.SocialMedia;
import ulm.hochschule.project_hoops.interfaces.AchievementReceiver;
import ulm.hochschule.project_hoops.fragments.HouseViewTab;

import ulm.hochschule.project_hoops.fragments.AchievementTab;
import ulm.hochschule.project_hoops.fragments.LoginTab;
import ulm.hochschule.project_hoops.fragments.ProfilTab;
import ulm.hochschule.project_hoops.fragments.RegisterTab;
import ulm.hochschule.project_hoops.fragments.RegisterTab2;
import ulm.hochschule.project_hoops.sonstige.BitmapResolver;
import ulm.hochschule.project_hoops.utilities.SqlManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AchievementReceiver {

    private NavigationView navigationView;


    //For Server downloading the ads
    private ImageView imageView;
    private Button buttonAds;
    private Bitmap bitmap;

    private SqlManager manager;

    private Fragment currTab;
    private Fragment registerTab = new RegisterTab2();
    private Fragment loginTab = new LoginTab();
    private Fragment houseView = new HouseViewTab();
    private Fragment chatFragment = new ChatFragment();
    private Fragment sozialMediaFragment = new SocialMedia();

    private AchievementTab achievementTab = new AchievementTab();

    private MenuItem profile;
    private MenuItem tipgame;
    private MenuItem achievements;
    private MenuItem chatClientMenuItem;
    private MenuItem accountshop;
    private boolean requestingImage = false;

    //For closeKeyboard
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout dLayout;
    private static DrawerLayout mDrawerLayout;

    public void setProfileEnabled(boolean flag) {
        tipgame.setEnabled(flag);
        profile.setEnabled(flag);
        achievements.setEnabled(flag);
        chatClientMenuItem.setEnabled(flag);
        accountshop.setEnabled(flag);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu navDrawer = navigationView.getMenu();
        profile = navDrawer.findItem(R.id.profile);
        tipgame = navDrawer.findItem(R.id.tipGame);
        achievements = navDrawer.findItem(R.id.achievement);
        chatClientMenuItem = navDrawer.findItem(R.id.nav_chat);
        accountshop = navDrawer.findItem(R.id.accountshop);



        tipgame.setEnabled(false);
        profile.setEnabled(false);
        achievements.setEnabled(false);
        chatClientMenuItem.setEnabled(false);
        accountshop.setEnabled(false);

        setIcons(navDrawer);
        //mi.setEnabled(false);

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                closeKeyboard();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        manager = SqlManager.getInstance();


        buttonAds = (Button) findViewById(R.id.btn_Ads );
        buttonAds.setVisibility(View.INVISIBLE);
        imageView = (ImageView)  findViewById(R.id.imageView_Ads);
        imageView.setVisibility(View.INVISIBLE);
        //btn close the Ads
        buttonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setVisibility(View.INVISIBLE);
                        buttonAds.setVisibility(View.INVISIBLE);
                        timer();
                    }
                });
            }
        });
        openTab();
        downloadImage();
        timer();

    }

    private void downloadImage(){

        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                requestingImage = true;
                try{
                    Socket s = new Socket("141.59.26.107", 21403);

                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

                    try{
                        byte[] buffer = (byte[]) ois.readObject();
                        bitmap = BitmapFactory.decodeByteArray(buffer,0,buffer.length);

                    }catch(ClassNotFoundException e){
                        e.printStackTrace();
                    }
                    ois.close();
                    s.close();
                }catch(Exception e){
                }
                requestingImage = false;
            }
        });
        d.start();
    }

    //Timer for the ads
    private void timer(){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                            imageView.bringToFront();
                            imageView.setVisibility(View.VISIBLE);
                            buttonAds.bringToFront();
                            buttonAds.setVisibility(View.VISIBLE);
                        } else{
                            if(!requestingImage)
                                downloadImage();
                        }

                    }
                });

            }
        },5*60*1000);
    }


    private void setIcons(Menu navDrawer) {

        MenuItem ticket = navDrawer.findItem(R.id.nav_ticket);
        MenuItem chat = navDrawer.findItem(R.id.nav_chat);
        MenuItem map = navDrawer.findItem(R.id.hausansicht);
        MenuItem login = navDrawer.findItem(R.id.login);
        MenuItem register = navDrawer.findItem(R.id.register);
        MenuItem game = navDrawer.findItem(R.id.basketball);

        int res = 40;

        ticket.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_ticket_icon, res, res)));
        map.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.icmap, res, res)));
        login.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_login, res, res)));
        game.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_bettinggame, res, res)));
        tipgame.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_bettinggame, res, res)));
        profile.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_stat_name, res, res)));
        achievements.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.achievement_icon, res, res)));
        chat.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_chat, 100,100)));//res, res)));
        accountshop.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.drawable.coinsack1, res, res)));
        register.setIcon(new BitmapDrawable(getResources(), BitmapResolver.decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_register, res, res)));
    }

    private void openTab() {
        changeFragment(new LoginTab());
    }

    private void openRegister() {

        changeFragment(new RegisterTab());

    }

    private void changeFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentPanel, f).addToBackStack( f.getTag()).commit();
        if(imageView.VISIBLE == View.VISIBLE) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.bringToFront();
                    buttonAds.bringToFront();
                }
            });
        }
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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
        };


        getMenuInflater().inflate(R.menu.main, menu);
        closeKeyboard();


        ImageView imV = (ImageView) findViewById(R.id.img_Logo);
        if(imV != null) {
            int h = imV.getHeight();
            double ratio = 1296.0/1197.0;
            imV.getLayoutParams().width = (int) (h*ratio);
        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        closeKeyboard();



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

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment f = null;

         if (id == R.id.nav_ticket) {
            changeFragment(new MyWebView());
        }else if (id == R.id.profile) {
            ProfilTab tab = ProfilTab.getInstance();
            changeFragment(tab);
        } else if (id == R.id.login) {
            currTab = loginTab;
            changeFragment(loginTab);
        } else if (id == R.id.register) {
            currTab = registerTab;
            changeFragment(new RegisterTab2());
        } else if (id == R.id.tipGame) {
            startActivity(new Intent(getApplicationContext(), TippspielActivity.class));
        } else if (id == R.id.hausansicht){
            currTab = houseView;
            changeFragment(new HouseViewTab());
        } else if(id == R.id.achievement) {
            changeFragment(achievementTab);
        } else if(id == R.id.nav_socialMedia) {
            changeFragment(sozialMediaFragment);
        }else if(id == R.id.nav_chat) {
            changeFragment(chatFragment);
        }else if(id == R.id.basketball) {
            startActivity(new Intent(getApplicationContext(), GameMenuActivity.class));
        } else if(id == R.id.accountshop) {
            startActivity(new Intent(getApplicationContext(), AccountshopActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        closeKeyboard();
        return true;
    }


    @Override
    public void addAchievement(int idx, int emblem, String description, String date) {
        achievementTab.addAchievement(idx, emblem, description, date);
    }

    //Close keyboard
    public void closeKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dLayout.getWindowToken(), 0);
    }

}