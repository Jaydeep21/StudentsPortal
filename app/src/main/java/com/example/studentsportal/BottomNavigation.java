package com.example.studentsportal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BottomNavigation extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_addrecipe:
                    selectFragment =new DonateFragment();
//                    mTextMessage.setText(R.string.title_home);
                    break;
                case R.id.navigation_home:
                    selectFragment =new BorrowFragment();
//                    mTextMessage.setText(R.string.title_dashboard);
                    break;
                case R.id.navigation_search:
                    selectFragment =new LocationFragment();
//                    mTextMessage.setText(R.string.title_notifications);
                    break;
                case R.id.navigation_logout:
                    selectFragment =new ProfileFragment();
//                    mTextMessage.setText(R.string.title_notifications);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner,selectFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
