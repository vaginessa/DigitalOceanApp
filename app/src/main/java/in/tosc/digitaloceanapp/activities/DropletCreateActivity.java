package in.tosc.digitaloceanapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigDecimal;
import java.util.ArrayList;

import in.tosc.digitaloceanapp.R;
import in.tosc.digitaloceanapp.adapters.DataCenterAdapter;
import in.tosc.digitaloceanapp.adapters.SelectDistributionAdapter;
import in.tosc.digitaloceanapp.adapters.SelectImageSizeAdapter;
import in.tosc.digitaloceanapp.adapters.SelectRegionAdapter;
import in.tosc.digitaloceanapp.adapters.SelectSizeAdapter;
import in.tosc.digitaloceanapp.utils.Utils;
import in.tosc.doandroidlib.DigitalOcean;
import in.tosc.doandroidlib.api.DigitalOceanClient;
import in.tosc.doandroidlib.objects.Image;
import in.tosc.doandroidlib.objects.Images;
import in.tosc.doandroidlib.objects.Region;
import in.tosc.doandroidlib.objects.Regions;
import in.tosc.doandroidlib.objects.Size;
import in.tosc.doandroidlib.objects.Sizes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DropletCreateActivity extends AppCompatActivity {
    private ArrayList<Image> distributionList;
    private ArrayList<Region> regionsList;
    private ArrayList<Size> sizeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droplet_create);

        EditText edt_dropletName = (EditText) findViewById(R.id.edt_droplet_name);
        final Spinner spinner_distribution, spinner_region, spinner_size;
        spinner_distribution = (Spinner) findViewById(R.id.spinner_distribution);
        spinner_region = (Spinner) findViewById(R.id.spinner_region);
        spinner_size = (Spinner) findViewById(R.id.spinner_size);

        distributionList = new ArrayList<>();
        regionsList = new ArrayList<>();
        sizeList = new ArrayList<>();

        final SelectDistributionAdapter distributionAdapter = new SelectDistributionAdapter(
                this,
                distributionList
        );
        spinner_distribution.setAdapter(distributionAdapter);

        final SelectRegionAdapter regionAdapter = new SelectRegionAdapter(this, regionsList);
        spinner_region.setAdapter(regionAdapter);

        final SelectImageSizeAdapter sizeAdapter = new SelectImageSizeAdapter(this, sizeList);
        spinner_size.setAdapter(sizeAdapter);

        String authToken = Utils.getAuthToken(this);
        DigitalOceanClient doClient = DigitalOcean.getDOClient(authToken);

        doClient.getImages(1, 100, "distribution").enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                distributionList.addAll(response.body().getImages());
                distributionAdapter.notifyDataSetChanged();
                Log.i("Droplets fetched", String.valueOf(distributionList.size()));
            }

            @Override
            public void onFailure(Call<Images> call, Throwable t) {
                Image errImage = new Image();
                errImage.setDistribution(getResources().getString(R.string.data_unavailable));
                distributionList.add(errImage);
                distributionAdapter.notifyDataSetChanged();
                Log.e("Failed getting images", t.getLocalizedMessage());
            }
        });

        doClient.getRegions().enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                regionsList.addAll(response.body().getRegions());
                regionAdapter.notifyDataSetChanged();
                Log.i("Regions fetched", "onResponse: " + regionsList.size());
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {
                Region errRegion = new Region();
                errRegion.setName(getResources().getString(R.string.data_unavailable));
                regionsList.add(errRegion);
                regionAdapter.notifyDataSetChanged();
                Log.e("failed getting Regions", "onFailure: Unable to oad Regions ", t);
            }
        });

        doClient.getSizes().enqueue(new Callback<Sizes>() {
            @Override
            public void onResponse(Call<Sizes> call, Response<Sizes> response) {
                sizeList.addAll(response.body().getSizes());
                sizeAdapter.notifyDataSetChanged();
                Log.e("failed getting Sizes", String.valueOf(sizeList.size()));
            }

            @Override
            public void onFailure(Call<Sizes> call, Throwable t) {
                Size errSize = new Size();
                errSize.setDiskSize(0);
                errSize.setPriceHourly(BigDecimal.ZERO);
                sizeList.add(errSize);
                sizeAdapter.notifyDataSetChanged();
                Log.e("Failed getting sizes", t.getLocalizedMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_droplet,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
    private void removeFragment(int count) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (count) {
            case 4:
                fragmentManager.popBackStack("additionalDetailsFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 3:
                fragmentManager.popBackStack("selectSizeFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                btnNext.setVisibility(View.VISIBLE);
                break;
            case 2:
                fragmentManager.popBackStack("DataCenterFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 1:
                fragmentManager.popBackStack("DataCenterFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            default:
                count = 1;
                this.finish();
                break;
        }
    }

    private void addFragment(int count) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (count) {

            case 2:
                DataCenterFragment selectDataCenter = new DataCenterFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, selectDataCenter, "DATA_CENTER");
                fragmentTransaction.addToBackStack("DataCenterFragment");
                fragmentTransaction.commit();
                break;
            case 3:
                SelectSizeFragment selectSizeFragment = new SelectSizeFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, selectSizeFragment, "SELECT_SIZE");
                fragmentTransaction.addToBackStack("selectSizeFragment");
                fragmentTransaction.commit();
                break;
            case 4:
                AdditionalDetailsFragment additionalDetailsFragment = new AdditionalDetailsFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, additionalDetailsFragment, "ADDITIONAL_DETAILS");
                fragmentTransaction.addToBackStack("additionalDetailsFragment");
                fragmentTransaction.commit();
                btnNext.setVisibility(View.GONE);
                break;
//            case 5:
//                createDroplet(droplet);
//                break;
            default:
                this.finish();
                count = 1;
        }
    }

    public void createDroplet(Droplet droplet) {
        //TODO Make network call to create a droplet

        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectImageFragment selectImageFragment = new SelectImageFragment();
        fragmentTransaction.replace(R.id.fragmentHolder, selectImageFragment, "CREATE_DROPLET");
        fragmentTransaction.addToBackStack("additionalDetailsFragment");
        fragmentTransaction.commit();
    }

    public void previous(View view) {
        count--;
//        Log.d("count dec" , String.valueOf(count));
        removeFragment(count);
        Log.e("Increased count", String.valueOf(count));
    }

    public void next(View view) {

        int setCount =  (droplet.getImage()!=null?1:0) +
                        (droplet.getRegion()!=null?1:0) +
                        (droplet.getSize()!=null?1:0);

        if(setCount == count) {
            count++;
            addFragment(count);
            Log.e("Decreased count", String.valueOf(count));
        }
        else
        {
            Toast.makeText(this, R.string.please_choose_option, Toast.LENGTH_SHORT).show();
        }
    }*/
}