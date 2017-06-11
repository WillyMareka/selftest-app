package ke.co.debechlabs.besure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import ke.co.debechlabs.besure.fragments.ReferralContacts;
import ke.co.debechlabs.besure.fragments.ReferralListFragment;
import ke.co.debechlabs.besure.fragments.ReferralSitesFragment;
import ke.co.debechlabs.besure.util.RevealAnimationSetting;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ReferralContacts.OnFragmentInteractionListener,
        ReferralListFragment.OnFragmentInteractionListener{

    public static int navItemIndex = 0;

    private static String TAG_REFERRAL = "referral";
    private static String TAG_PHARMACIES = "pharmacies";
    private static String TAG_RESOURCES = "resources";
    private static String TAG_FAQ = "faq";
    private static String TAG_SHARE = "share";
    private static String TAG_ABOUT = "about";
    public static String CURRENT_TAG = TAG_REFERRAL;

    private static int TAG_FILTER = 0;

    private String[] activityTitles;

    private boolean shouldLoadReferralFragOnBackPress = true;
    private Handler mHandler;

    NavigationView navigationView;
    DrawerLayout drawer;
    LinearLayout contentWrapper;
    CoordinatorLayout mainCoordinatorLayout;

    MenuItem filterItem;

    BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("LocationPref", 0);
        contentWrapper = (LinearLayout) findViewById(R.id.mainContent_wrapper);
        mainCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainCoorinatorLayout);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        Menu mainMenu = toolbar.getMenu();
        System.out.println("Filter Item::::" + mainMenu.size());

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int randomColor =
                        Color.argb(255, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
                switch (item.getItemId()){
                    case R.id.bottom_nav_map:
                        selectedFragment = ReferralSitesFragment.newInstance(constructSetting());
                        break;
                    case R.id.bottom_nav_list:
                        selectedFragment = ReferralListFragment.newInstance(constructSetting());
                        break;
                    case R.id.bottom_nav_info:
                        selectedFragment = ReferralContacts.newInstance(constructSetting());
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainContent, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        navigation.setSelectedItemId(R.id.bottom_nav_map);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_REFERRAL;
            loadHomeFragment();
        }
    }

    private void loadHomeFragment(){
        selectNavMenu();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private void selectNavMenu(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
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
//        System.out.println("On Create Option Menu" + menu.size())
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_referral_sites) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_REFERRAL;
        } else if (id == R.id.nav_pharmacies) {
           startActivity(new Intent(this, PharmaciesActivity.class));
        } else if (id == R.id.nav_resources) {
            startActivity(new Intent(this, ResourcesActivity.class));
        } else if (id == R.id.nav_manage) {

        }else{
            navItemIndex = 0;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private RevealAnimationSetting constructSetting(){
        return RevealAnimationSetting.with(
                (int)(navigation.getX() + navigation.getWidth() /2),
                (int)(navigation.getY() + navigation.getHeight() /2),
                mainCoordinatorLayout.getWidth(),
                mainCoordinatorLayout.getHeight()
        );
    }
}
