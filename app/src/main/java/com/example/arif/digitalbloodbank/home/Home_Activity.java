package com.example.arif.digitalbloodbank.home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.About;
import com.example.arif.digitalbloodbank.Login;
import com.example.arif.digitalbloodbank.R;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Activity extends AppCompatActivity {

    private static final String TAG = "MainActvity";

    private Toolbar mTopToolbar;
    private ViewPager mViewPager;
    private SectionPageManager sectionPageManager;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        auth = FirebaseAuth.getInstance();
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        sectionPageManager = new SectionPageManager(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageManager adapter = new SectionPageManager(getSupportFragmentManager());
        adapter.addFragment(new Blood_Feed_Frag(), "Blood Feed");
        adapter.addFragment(new Notice_Frag(), "Search");
        adapter.addFragment(new Request_Blood(), "Request Blood");

        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home_, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_about) {
            Intent I = new Intent(getApplicationContext(), About.class);
            startActivity(I);
            return true;
        }
        if (id == R.id.action_logout) {
            auth.signOut();
            finish();
           Intent I = new Intent(getApplicationContext(), Login.class);
            startActivity(I);
            return true;
        }

        if (id == R.id.action_exit) {

       System.exit(0);
      finish();

        }


        return super.onOptionsItemSelected(item);
    }
}
