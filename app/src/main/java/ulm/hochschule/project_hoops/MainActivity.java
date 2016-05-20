package ulm.hochschule.project_hoops;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {

    private Button btn_Register;
    private Button btn_login;
    private NavigationView navigationView;

    private EditText et_username;
    private EditText et_password;

    private SqlManager manager;

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

       //navigationView.getMenu().findItem(R.id.profile).setEnabled(false);

        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentPanel, new NewsTab()).commit();
    }

    private void openRegister() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.contentPanel, new RegisterTab()).commit();
        onBackPressed();
    }

    private void logIn(){
            /*View header = navigationView.getHeaderView(0);
            View header2 = LayoutInflater.from(this).inflate(R.layout.nav_header_usermenu, null);
            navigationView.removeHeaderView(header);
            navigationView.addHeaderView(header2);*/
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.nav_header_main, new login()).commit();
    }

    private boolean check(){
        if(!manager.userExist(et_username.getText().toString())){
            et_username.setError("username doesen't exist");
        }
        if(true){ //TODO
            et_password.setError("wrong password");
        }
        //username exist and password that belongs to the username
        return false;
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

        getMenuInflater().inflate(R.menu.main, menu);

        btn_Register = (Button) findViewById(R.id.btn_Register);
        btn_login = (Button) findViewById(R.id.bt_Login);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        }
        );

        et_username = (EditText) findViewById(R.id.et_UserName_nav);
        et_password = (EditText) findViewById(R.id.et_Password_nav);

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

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();



        if (id == R.id.nav_camera) {
            ft.replace(R.id.contentPanel, new NewsTab()).commit();
        } else if (id == R.id.nav_gallery) {
            ft.replace(R.id.contentPanel, new WebView2()).commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.profile) {
            ProfilTab tab = new ProfilTab();
            tab.addObserver(this);
            ft.replace(R.id.contentPanel, tab).commit();
        }
        else if (id == R.id.nav_test){
            ft.replace(R.id.contentPanel, new TestTab()).commit();
        }
        else if (id == R.id.nav_send) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void update(Observable observable, Object data) {
        Intent i = new Intent(getApplicationContext(), EditProfilActivity.class);
        startActivity(i);
    }


}
