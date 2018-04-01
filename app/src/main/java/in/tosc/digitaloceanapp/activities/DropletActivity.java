package in.tosc.digitaloceanapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.tosc.digitaloceanapp.R;
import in.tosc.digitaloceanapp.adapters.DropletsAdapter;
import in.tosc.digitaloceanapp.fragments.DashboardFragment;
import in.tosc.digitaloceanapp.utils.FontsOverride;
import in.tosc.digitaloceanapp.utils.Utils;
import in.tosc.doandroidlib.DigitalOcean;
import in.tosc.doandroidlib.api.DigitalOceanClient;
import in.tosc.doandroidlib.objects.AccountInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by the-dagger on 11/26/2016.
 */

public class DropletActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static DigitalOceanClient doClient;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA);
        setContentView(R.layout.activity_droplet);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.dispatchSetSelected(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        doClient = DigitalOcean.getDOClient(Utils.getAuthToken(this));
        doClient.getAccount().enqueue(new Callback<AccountInfo>() {
            @Override
            public void onResponse(Call<AccountInfo> call, Response<AccountInfo> response) {
                String email = response.body().getAccount().getEmail();

                ImageView profilePic = ((ImageView) drawer.findViewById(R.id.accountPic));
                TextView emailView = (TextView) drawer.findViewById(R.id.accountEmail);

                if (emailView != null) {
                    emailView.setText(email);
                }
                if (profilePic != null && email != null && !email.isEmpty()) {
                    Picasso.with(DropletActivity.this)
                            .load("https://www.gravatar.com/avatar/")
                            .into(profilePic);
                }
            }

            @Override
            public void onFailure(Call<AccountInfo> call, Throwable t) {
                Log.e("Failed to get email", t.getLocalizedMessage());
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Select Dashboard menu by default!
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }


    public static void refreshData(final DropletsAdapter adapter) {

/*        doClient.getDroplets(1, 10).enqueue(new Callback<Droplets>() {
            @Override
            public void onResponse(Call<Droplets> call, Response<Droplets> response) {
                droplets.clear();
                List<Droplet> dropletsDownloaded = response.body().getDroplets();
                for (Droplet droplet : dropletsDownloaded) {
                    if (droplet.isLocked()) {
                        dropletsDownloaded.remove(droplet);  //A locked droplet prevents any user actions
                    }
                }
                droplets.addAll(dropletsDownloaded);
                adapter.notifyDataSetChanged();
                adapter.updateUIOnEmpty();
                Log.e("Droplets fetched", String.valueOf(dropletsDownloaded.size()));
            }

            @Override
            public void onFailure(Call<Droplets> call, Throwable t) {
                droplets = null;
                Log.e("Failed to get Droplets", t.getMessage());
            }
        });*/
    }
/*
    public static void refreshModifiedData(final OnDropletNameChange onDropletNameChange) {

        doClient.getDroplets(1, 10).enqueue(new Callback<Droplets>() {
            @Override
            public void onResponse(Call<Droplets> call, Response<Droplets> response) {
                droplets.clear();
                droplets.addAll(response.body().getDroplets());
                //dropletAdapter.notifyDataSetChanged();
                Log.e("Droplets fetched", String.valueOf(response.body().getDroplets().size()));
                onDropletNameChange.onSuccess(droplets);
            }

            @Override
            public void onFailure(Call<Droplets> call, Throwable t) {
                droplets = null;
                Log.e("Failed to get Droplets", t.getMessage());
                onDropletNameChange.onError(t.getMessage());
            }
        });
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        //refreshData(dropletsAdapter);
    }

    public void pushFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .replace(R.id.frameHolder, fragment);//R.id.content_frame is the layout you want to replace
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            toolbar.setTitle(R.string.dashboard);
            DashboardFragment dashboardFragment = new DashboardFragment();
            pushFragment(dashboardFragment);
        } else if (id == R.id.nav_domains) {
            toolbar.setTitle(R.string.domains);
        } else if (id == R.id.nav_certificates) {
            toolbar.setTitle(R.string.certificates);
        } else if (id == R.id.nav_manage) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent();
//            shareIntent.setDataAndType(Uri.parse("http://google.com/"),"");
            startActivity(Intent.createChooser(shareIntent, "Share app"));
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*    private void openCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
*/
}
