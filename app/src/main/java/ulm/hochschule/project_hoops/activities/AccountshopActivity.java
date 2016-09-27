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

import Enums.AvatarItems;
import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.views.ItemView;

public class AccountshopActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AvatarItems currentSelected = AvatarItems.EYES;
    private LinearLayout content_Items;
    private TextView tv_Coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountshop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_Coins = (TextView) findViewById(R.id.tv_Coins);
        content_Items = (LinearLayout) findViewById(R.id.contentItems);
        addItem();
        addItem();
        addItem();
        addItem();
        addItem();
        addItem();

        addItem();
        addItem();
        addItem();

        addItem();
        addItem();
        addItem();


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

    }

    private void addItem() {
        //TODO
        ItemView i = new ItemView(getApplicationContext());
        i.setItem(R.drawable.avatar_hat_12, "Ein Helm der alten Vikinger", 3000, false);
        content_Items.addView(i);
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
