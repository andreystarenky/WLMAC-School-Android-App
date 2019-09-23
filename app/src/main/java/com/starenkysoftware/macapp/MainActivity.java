package com.starenkysoftware.macapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private WebView webView;
    private Boolean firstTime;
    private static final String TAG = "MainActivity";
    Fragment cf;
    Fragment af;
    Fragment lsf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "************* onCreate");
        System.out.println("OC");
        if(isFirstTime()) {
            Log.i(TAG, "************* firstTime");
            tutorial();
        }
        else{
            Log.i(TAG, "************* startApp");
            startApp();
        }
        Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());

        //setContentView(R.layout.tutorial);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "************* onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "************* onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "************* onResume");
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        if(bottomNav!=null) {
            //bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(bottomNav.getSelectedItemId());
        }
    }

    public void tutorial(){
        setContentView(R.layout.tutorial);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //startApp();
            }
        }, 2000);
    }

    public void hideOne(View v){
        ImageView imgView = (ImageView)findViewById(R.id.imageView1);
        imgView.setVisibility(View.INVISIBLE);
    }

    public void hideTwo(View v){
        ImageView imgView = (ImageView)findViewById(R.id.imageView2);
        imgView.setVisibility(View.INVISIBLE);
    }

    public void hideThree(View v){
        ImageView imgView = (ImageView)findViewById(R.id.imageView3);
        imgView.setVisibility(View.INVISIBLE);
    }

    /*public void hideFour(View v){
        ImageView imgView = (ImageView)findViewById(R.id.imageView4);
        imgView.setVisibility(View.INVISIBLE);
    }*/

    public void hideFive(View v){
        ImageView imgView = (ImageView)findViewById(R.id.imageView5);
        imgView.setVisibility(View.INVISIBLE);
    }

    public void hideSix(View v){
        ImageView imgView = (ImageView)findViewById(R.id.imageView6);
        imgView.setVisibility(View.INVISIBLE);
        startApp();
    }

    public void startApp(){
        Log.i(TAG, "************* in startApp");
        System.out.println("SA");
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("WLMAC");
        actionBar.setIcon(R.drawable.wlmaclogo);
        cf = new CalendarFragment();
        af = new AnnouncementsFragment();
        lsf = new LateStartsFragment();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LateStartsFragment()).commit();
        //bottomNav.setSelectedItemId(R.id.nav_calendar);
        Log.i(TAG, "************* startApp: seeing bottom nav to calendar");
        bottomNav.setSelectedItemId(R.id.nav_calendar);
    }

    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
            else{
                firstTime = false;
            }
        }
        return firstTime;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Log.i(TAG, "********** navigating to " + menuItem.toString());
                    int inAnim;
                    int outAnim;
                    Fragment selectedFragment=null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_calendar:
                            //selectedFragment = new CalendarFragment();
                            selectedFragment=cf;
                            System.out.println("1");
                            break;
                        case R.id.nav_announcements:
                            //selectedFragment = new AnnouncementsFragment();
                            selectedFragment=af;
                            System.out.println("2");
                            break;
                        case R.id.nav_late_starts:
                            //selectedFragment = new LateStartsFragment();
                            selectedFragment=lsf;
                            System.out.println("3");
                            break;
                    }
                    FragmentManager ft = getSupportFragmentManager();
                    //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            //selectedFragment).commit();
                                     ft.beginTransaction().setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
                    //ft.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right)
                            .replace(R.id.fragment_container, selectedFragment).commit();
                    //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    System.out.println(selectedFragment);
                    return true;
                }
            };

    public void notificationsOff(View v){

        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        if(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getApplicationContext().getPackageName());
        }else if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getApplicationContext().getPackageName());
            intent.putExtra("app_uid", getApplicationContext().getApplicationInfo().uid);
        }else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
        }
        startActivityForResult(intent, 0);
    }

    public void viewPolicy(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://andreystarenky.github.io/starenkysoftware/privacy_policy.html")));
    }
}
