package com.longdhps08836.asmcarclient;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Main_Buttom_Navigation_Layout extends AppCompatActivity {

    FrameLayout flContainer;
    BottomNavigationView bnvNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__bottom_navigation__layout);

        connect_Layout();

        init();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, new Fragment_Home()).commit();
        }

    }

    private void init() {

        bnvNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.itemHome:
                        fragment = new Fragment_Home();
                        break;

                    case R.id.itemProfile:
                        fragment = new Fragment_Profile_Client();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
    }

    private void connect_Layout() {

        flContainer = findViewById(R.id.flContainer);
        bnvNav = findViewById(R.id.bnvNav);

    }
}
