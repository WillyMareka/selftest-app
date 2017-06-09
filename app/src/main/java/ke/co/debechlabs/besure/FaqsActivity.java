package ke.co.debechlabs.besure;

import android.net.Uri;
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

import ke.co.debechlabs.besure.fragments.FaqListFragment;
import ke.co.debechlabs.besure.fragments.PharmacyHelpfulInformationFragment;
import ke.co.debechlabs.besure.util.RevealAnimationSetting;

/**
 * Created by Marewill on 6/9/2017.
 */

public class FaqsActivity extends AppCompatActivity implements
        FaqListFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private LinearLayout container;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FaqListFragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.faq_list:
                    selectedFragment = FaqListFragment.newInstance(constructSetting());
                    break;
//                case R.id.faq_info:
//                    selectedFragment = PharmacyHelpfulInformationFragment.newInstance(constructSetting());
//                    break;
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
        setContentView(R.layout.activity_faqs);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        container = (LinearLayout) findViewById(R.id.container);

        toolbar.setTitle("FAQs");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.faq_list);
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
