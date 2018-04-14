package acitivities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import fragments.AboutFragment;
import fragments.AdmissionsFragment;
import fragments.HomeFragment;
import fragments.ProfileFragment;
import universite.com.parasite.MyFirebaseInstanceIdService;
import universite.com.parasite.R;
import universite.com.parasite.SharedPrefManager;
import universite.com.parasite.VolleySingleton;

import static universite.com.parasite.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FrameLayout mainContent;
    private Toolbar toolbar;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateToken(SharedPrefManager.getInstance(this).getKeyRefreshedToken());

        homeFragment = new HomeFragment();
        final AdmissionsFragment admissionsFragment = new AdmissionsFragment();
        openFragment(homeFragment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        toolbar.setSubtitle("In News");
        toolbar.setSubtitleTextColor(Color.WHITE);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        mainContent = findViewById(R.id.main_content);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                mainContent.setTranslationX(slideX);
            }
        };

        mDrawerLayout.addDrawerListener(drawerToggle);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_home:{
//                        getSupportActionBar().show();
                        toolbar.findViewById(R.id.toolbar_post_search).setVisibility(View.VISIBLE);
                        openFragment(homeFragment);
                        toolbar.setTitle("UniverSite");
                        toolbar.setSubtitle("");
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                    case R.id.nav_admission : {
//                        getSupportActionBar().show();
                        toolbar.findViewById(R.id.toolbar_post_search).setVisibility(View.GONE);
                        openFragment(admissionsFragment);
                        toolbar.setTitle("Admissions");
                        toolbar.setSubtitle("");
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                    case R.id.nav_about : {
//                        getSupportActionBar().show();
                        toolbar.findViewById(R.id.toolbar_post_search).setVisibility(View.GONE);
                        openFragment(new AboutFragment());
                        toolbar.setTitle("About Us");
                        toolbar.setSubtitle("");
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                    case R.id.nav_profile : {
//                        getSupportActionBar().hide();
                        toolbar.findViewById(R.id.toolbar_post_search).setVisibility(View.GONE);
                        toolbar.setTitle("Profile");
                        toolbar.setSubtitle("");
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        openFragment(new ProfileFragment());
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                }

                return true;
            }
        });

        navigationView.getMenu().getItem(0).setChecked(true);

    }

    private void openFragment(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.toolbar_post_search);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_post_search).getActionView();
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                homeFragment.search("");
                return true;
            }
        });
        if(searchView!=null)
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().equals(""))
                    homeFragment.search(newText);
                return true;
            }
        });
        if(searchView!=null)
        setSearchTextColor(searchView);
        return true;
    }

    private void setSearchTextColor(SearchView searchView) {
        EditText searchBox=((EditText) searchView.findViewById (android.support.v7.appcompat.R.id.search_src_text));
        searchBox.setTextColor(getResources().getColor(R.color.AppColor));
        searchBox.setHighlightColor(getResources().getColor(R.color.AppColor));
        searchBox.setHintTextColor(getResources().getColor(R.color.AppColor));

        ImageView searchClose = searchView.findViewById (android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setColorFilter(getResources().getColor(R.color.AppColor));




        ImageView searchSubmit = (ImageView) searchView.findViewById (android.support.v7.appcompat.R.id.search_go_btn);
        searchSubmit.setColorFilter(getResources().getColor(R.color.AppColor));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    private void updateToken(final String token){
        Log.v(MyFirebaseInstanceIdService.class.getSimpleName(), "updateToken() called");
        StringRequest tokenUpdate = new StringRequest(Request.Method.POST, BASE_URL + "update_token.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("token", token);
                map.put("id", SharedPrefManager.getInstance(MainActivity.this).getUser().getmID());

                return map;
            }
        };

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(tokenUpdate);
    }


}
