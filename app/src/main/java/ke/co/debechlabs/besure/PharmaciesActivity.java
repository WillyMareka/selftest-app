package ke.co.debechlabs.besure;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import ke.co.debechlabs.besure.fragments.PharmacyHelpfulInformationFragment;
import ke.co.debechlabs.besure.fragments.PharmacyListingFragment;
import ke.co.debechlabs.besure.fragments.PharmacyMapFragment;
import ke.co.debechlabs.besure.util.RevealAnimationSetting;

public class PharmaciesActivity extends AppCompatActivity implements
        PharmacyListingFragment.OnFragmentInteractionListener,
        PharmacyMapFragment.OnFragmentInteractionListener,
        PharmacyHelpfulInformationFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private LinearLayout container;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    selectedFragment = PharmacyMapFragment.newInstance(constructSetting());
                    break;
                case R.id.navigation_list:
                    selectedFragment = PharmacyListingFragment.newInstance(constructSetting());
                    break;
                case R.id.navigation_info:
                    selectedFragment = PharmacyHelpfulInformationFragment.newInstance(constructSetting());
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        container = (LinearLayout) findViewById(R.id.container);

        toolbar.setTitle("Pharmacies");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_map);
    }


    private RevealAnimationSetting constructSetting(){
        return RevealAnimationSetting.with(
                (int)(navigation.getX() + navigation.getWidth() /2),
                (int)(navigation.getY() + navigation.getHeight() /2),
                container.getWidth(),
                container.getHeight()
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
