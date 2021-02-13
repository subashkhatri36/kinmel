package sabaijanakaari.blogspot.kinmel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import sabaijanakaari.blogspot.kinmel.Database.DBquery;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Activity mainActivity;

    private TextView badgecount;

    private static final int HOME_FRAGMENT = 0;
    private static final int CARTFRAGMENT = 3;
    private static final int MY_ORDER_FRAGMENT = 1;
    private int currentFragment = -1;
    private static int WHISHLIST_FRAGMENT = 4;
    private static int MY_REWARD_FRAGMWNT = 2;
    private static int MY_ACCOUNT_FRAGMENT = 5;
    public static boolean SHOW_CART = false;
    private FirebaseUser currentUser;

    private Window window;
    public static DrawerLayout drawer;
    ImageView actionbarlogo;

    Toolbar toolbar;
    private NavigationView navigationView;
    /////////////Variable Defination
    FirebaseAuth mAuth;
    private static FragmentManager fragmentManager;
    FrameLayout frameLayout;
    ///////////////Variable Defination


    private int scrollflage;
    AppBarLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        frameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);
        mAuth = FirebaseAuth.getInstance();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        actionbarlogo = (ImageView) findViewById(R.id.actionbar_logo);
        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        scrollflage = params.getScrollFlags();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (currentFragment == -1) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        if (SHOW_CART) {
            mainActivity=this;
            // drawer.setDrawerLockMode(1);
            drawer.setDrawerLockMode(drawer.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart", new MycartFragment(), -2);
        } else {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);

        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);
        }
        invalidateOptionsMenu();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (SHOW_CART) {
                    mainActivity=null;
                    SHOW_CART = false;
                    finish();

                } else {
                    invalidateOptionsMenu();
                    actionbarlogo.setVisibility(View.VISIBLE);
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);

            MenuItem cartItem = menu.findItem(R.id.main_cart);

            cartItem.setActionView(R.layout.bagde_layout);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.ic_shopping_cart_white_24dp);
            badgecount = cartItem.getActionView().findViewById(R.id.badge_count);
            badgecount.setText(String.valueOf(DBquery.cartList.size()));
            if (currentUser != null) {
                if (DBquery.cartList.size() == 0) {
                    DBquery.loadCartList(MainActivity.this, new Dialog(MainActivity.this), false, badgecount,new TextView(MainActivity.this));

                } else {
                    badgecount.setVisibility(View.VISIBLE);
                    if (DBquery.cartList.size() < 99) {
                        badgecount.setText(String.valueOf(DBquery.cartList.size()));
                    } else {
                        badgecount.setText("99");
                    }
                }
            }
            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(i);
                        RegisterActivity.COMING_FOROM = true;
                    } else {
                        goToFragment("My Cart", new MycartFragment(), CARTFRAGMENT);
                    }
                }
            });

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search) {
            //Search Icon
            return true;
        } else if (id == R.id.main_notification) {
            //Notification
            return true;
        } else if (id == R.id.main_cart) {
            //mychart
            if (currentUser == null) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                RegisterActivity.COMING_FOROM = true;
            } else {
                goToFragment("My Cart", new MycartFragment(), CARTFRAGMENT);
            }

            return true;
        } else if (id == android.R.id.home) {
            if (SHOW_CART) {
                mainActivity=null;
                //null
                SHOW_CART = false;
                finish();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title, Fragment fragment, int currentFragmentno) {
        actionbarlogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, currentFragmentno);
        if (currentFragmentno == CARTFRAGMENT || SHOW_CART) {
            params.setScrollFlags(0);
            navigationView.getMenu().getItem(3).setChecked(true);
        } else {
            params.setScrollFlags(scrollflage);
        }
    }

    private void setFragment(Fragment fragment, int fragmentNumber) {
        if (currentFragment != fragmentNumber) {
            if (fragmentNumber == MY_REWARD_FRAGMWNT) {
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            } else {
                window.setStatusBarColor(Color.parseColor("#CE0000"));
                toolbar.setBackgroundColor(Color.parseColor("#CE0000"));
            }
            currentFragment = fragmentNumber;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fading, R.anim.fade_out);
            //fragmentTransaction.setCustomAnimations(R.anim.right_enter,R.anim.left_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();

        }


    }

    MenuItem menuItem;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuItem=item;
        if (currentUser != null) {
            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    int id = menuItem.getItemId();
                    if (id == R.id.nav_myhome) {
                        //My home
                        invalidateOptionsMenu();
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        actionbarlogo.setVisibility(View.VISIBLE);
                        navigationView.getMenu().getItem(0).setChecked(true);
                        setFragment(new HomeFragment(), HOME_FRAGMENT);

                    } else if (id == R.id.nav_myorder) {
                        goToFragment("My Order", new MyorderFragment(), MY_ORDER_FRAGMENT);
                    } else if (id == R.id.nav_myreward) {
                        goToFragment("My Order", new MyrewardFragment(), MY_REWARD_FRAGMWNT);
                    } else if (id == R.id.nav_mycart) {
                        goToFragment("My Cart", new MycartFragment(), CARTFRAGMENT);

                    } else if (id == R.id.nav_mywishlist) {
                        goToFragment("My WhisList", new MywhishlistFragment(), WHISHLIST_FRAGMENT);

                    } else if (id == R.id.nav_myaccount) {
                        goToFragment("My Account", new MyaccountFragment(), MY_ACCOUNT_FRAGMENT);

                    } else if (id == R.id.nav_share) {

                    } else if (id == R.id.nav_logout) {
                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();
                        DBquery.ClearData();
                        Intent registeractivity = new Intent(MainActivity.this, RegisterActivity.class);
                        registeractivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(registeractivity);
                        finish();
                    }
                }
            });
            return true;

        } else {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
            RegisterActivity.COMING_FOROM = true;
            return false;
        }

    }
}
