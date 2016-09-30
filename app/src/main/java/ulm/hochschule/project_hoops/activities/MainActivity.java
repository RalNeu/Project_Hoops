package ulm.hochschule.project_hoops.activities;

import android.content.Context;
import android.content.Intent;
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

import java.util.Timer;
import java.util.TimerTask;

import ulm.hochschule.project_hoops.fragments.ChatFragment;
import ulm.hochschule.project_hoops.interfaces.AchievementReceiver;
import ulm.hochschule.project_hoops.fragments.HouseViewTab;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.AchievementTab;
import ulm.hochschule.project_hoops.fragments.LoginTab;
import ulm.hochschule.project_hoops.fragments.ProfilTab;
import ulm.hochschule.project_hoops.fragments.RegisterTab;
import ulm.hochschule.project_hoops.fragments.RegisterTab2;
import ulm.hochschule.project_hoops.fragments.WebView2;
import ulm.hochschule.project_hoops.utilities.SqlManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AchievementReceiver {

    private Button btn_Register;
    private Button btn_login;
    private NavigationView navigationView;

    private EditText et_username;
    private EditText et_password;
    private ImageButton imageButton;
    private Button buttonAds;

    private SqlManager manager;

    private Fragment currTab;
    private Fragment registerTab = new RegisterTab2();
    private Fragment loginTab = new LoginTab();
    private Fragment houseView = new HouseViewTab();
    private Fragment chatFragment = new ChatFragment();

    private AchievementTab achievementTab = new AchievementTab();

    private MenuItem profile;
    private MenuItem tipgame;
    private MenuItem achievements;
    private MenuItem chatClientMenuItem;
    private MenuItem accountshop;

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


        buttonAds = (Button) findViewById(R.id.btn_Ads);
        buttonAds.setVisibility(View.INVISIBLE);
        imageButton = (ImageButton)  findViewById(R.id.ibtn_Ads);
        imageButton.setVisibility(View.INVISIBLE);
        //btn close the Ads
        buttonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageButton.setVisibility(View.INVISIBLE);
                        buttonAds.setVisibility(View.INVISIBLE);
                        System.out.print("ich bin in der onlick view");
                        timer();
                    }
                });
            }
        });
        openTab();
        timer();

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
                        imageButton.bringToFront();
                        imageButton.setVisibility(View.VISIBLE);
                        buttonAds.bringToFront();
                        buttonAds.setVisibility(View.VISIBLE);
                    }
                });

            }
        },20*1000);
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
        if(imageButton.VISIBLE == View.VISIBLE) {
            imageButton.post(new Runnable() {
                @Override
                public void run() {
                    imageButton.bringToFront();
                    buttonAds.bringToFront();
                }
            });
        }
    }


    private void logIn() {
        // TODO: 20.05.2016
    }

    private boolean check() {
        boolean ok = true;
        try {
            if (manager.userExist(et_username.getText().toString())) {
                if (!et_password.getText().toString().equals(manager.getUser(et_username.getText().toString())[3])) {
                    et_password.setError("Passwort falsch");
                    et_username.setError("Benutzer existiert nicht");
                    ok = false;
                }
            } else if (manager.emailExist(et_username.getText().toString())) {
                if (!et_password.getText().toString().equals(manager.getPassword(et_username.getText().toString()))) {
                    et_password.setError("Passwort falsch");
                    et_username.setError("Benutzer existiert nicht");
                    ok = false;
                }
            } else {
                et_password.setError("Passwort falsch");
                et_username.setError("Benutzer existiert nicht");
                ok = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println((manager.getPassword(et_username.getText().toString())));
        } catch (Exception e) {

        }
        return ok;
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

        //btn_Register = (Button) findViewById(R.id.btn_Register);
        //btn_login = (Button) findViewById(R.id.bt_Login);

       // dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


       // MenuView.ItemView i = (MenuView.ItemView) dLayout.findViewById(R.id.profile);
        //i.setEnabled(false);

       // et_username = (EditText) findViewById(R.id.et_UserName_nav);
        //et_password = (EditText) findViewById(R.id.et_Password_nav);

/*        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (check()) {
                                                 Toast.makeText(getApplicationContext(), "eingeloggt", Toast.LENGTH_LONG).show();
                                                 onBackPressed();
                                             } else
                                                 Toast.makeText(getApplicationContext(), "nicht eingeloggt", Toast.LENGTH_LONG).show();
                                         }
                                     }
        );*/

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

        if (id == R.id.nav_camera) {
            //currTab = newsTab;
           // changeFragment(newsTab);
        } else if (id == R.id.nav_gallery) {
            changeFragment(new WebView2());
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

    //Close keyboard Patrick
    public void closeKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dLayout.getWindowToken(), 0);
    }

}