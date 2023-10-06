package vn.edu.usth.mylogin.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import vn.edu.usth.mylogin.Fragment.Profile;
import vn.edu.usth.mylogin.Fragment.Home;
import vn.edu.usth.mylogin.Fragment.MyLibrary;
import vn.edu.usth.mylogin.Fragment.Search;
import vn.edu.usth.mylogin.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    MyLibrary myLibrary = new MyLibrary();
    Home home = new Home();
    Search search = new Search();
    BottomNavigationView navigationView;
    FirebaseAuth auth;
    TextView textView;
    FirebaseUser user;
    DrawerLayout drawerLayout;
    NavigationView navigationView2;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    FirebaseFirestore fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        textView = findViewById(R.id.user_details);
        String userID;

        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            userID = user.getUid();
            DocumentReference documentReference = fireStore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            textView.setText(value.getString("uName"));
                        }
        });
        };

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_Open, R.string.menu_Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView2 = findViewById(R.id.naviView);
        navigationView2.setNavigationItemSelectedListener(this);
        navigationView2.bringToFront();

        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.home_view) {
                        FragmentTransaction replaceFragment= getSupportFragmentManager().beginTransaction();
                        replaceFragment.replace(R.id.container, home).commit();

                    } else if (item.getItemId() == R.id.myLibrary_view) {
                        FragmentTransaction replaceFragment = getSupportFragmentManager().beginTransaction();
                        replaceFragment.replace(R.id.container, myLibrary).commit();
                    }
                    else
                    {
                        FragmentTransaction replaceFragment = getSupportFragmentManager().beginTransaction();
                        replaceFragment.replace(R.id.container, search).commit();
                    }
                    return true;
                }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.profile)
        {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
            finish();
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