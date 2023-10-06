package vn.edu.usth.mylogin.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.mylogin.Fragment.Home;
import vn.edu.usth.mylogin.Fragment.MyLibrary;
import vn.edu.usth.mylogin.Fragment.SearchFragment;
import vn.edu.usth.mylogin.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    MyLibrary myLibrary = new MyLibrary();
    Home home = new Home();
    SearchFragment searchFragment = new SearchFragment();
    BottomNavigationView navigationView;
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;
    DrawerLayout drawerLayout;
    NavigationView navigationView2;

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.home_view) {
                        FragmentTransaction replaceFragment= getSupportFragmentManager().beginTransaction();
                        replaceFragment.replace(R.id.container, home).commit();

                    } else if (item.getItemId() == R.id.myLibrary_view) {
                        FragmentTransaction replaceFragment= getSupportFragmentManager().beginTransaction();
                        replaceFragment.replace(R.id.container, myLibrary).commit();
                    } else
                    {
                        FragmentTransaction replaceFragment= getSupportFragmentManager().beginTransaction();
                        replaceFragment.replace(R.id.container, searchFragment).commit();
                    }
                    return true;
                }
        });

        drawerLayout = findViewById(R.id.drawer);
        navigationView2 = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_Open, R.string.menu_Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView2.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 23456789) {
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}