package ke.co.debechlabs.besure;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.fragments.ResourcesAudioFragment;
import ke.co.debechlabs.besure.fragments.ResourcesLinksFragment;
import ke.co.debechlabs.besure.fragments.ResourcesVideosFragment;

public class ResourcesActivity extends AppCompatActivity
    implements ResourcesAudioFragment.OnFragmentInteractionListener,
                ResourcesVideosFragment.OnFragmentInteractionListener,
                ResourcesLinksFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Resources");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Video Resources");

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_1497013041_video_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sound);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_globe);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        actionBar.setTitle("Video Resources");
                        break;
                    case 1:
                        actionBar.setTitle("Audio Resources");
                        break;
                    case 2:
                        actionBar.setTitle("Websites");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ResourcesVideosFragment(), "Videos");
        adapter.addFragment(new ResourcesAudioFragment(), "Audios");
        adapter.addFragment(new ResourcesLinksFragment(), "Web Pages");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
            return null;
        }

        public void addFragment(Fragment fm, String title){
            mFragmentList.add(fm);
            mFragmentTitleList.add(title);
        }

    }
}
