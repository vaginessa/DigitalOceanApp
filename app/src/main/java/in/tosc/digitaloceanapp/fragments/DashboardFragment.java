package in.tosc.digitaloceanapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.tosc.digitaloceanapp.R;
import in.tosc.digitaloceanapp.activities.DropletActivity;
import in.tosc.digitaloceanapp.activities.DropletCreateActivity;
import in.tosc.digitaloceanapp.adapters.DropletsAdapter;
import in.tosc.digitaloceanapp.adapters.SelectDistributionAdapter;
import in.tosc.digitaloceanapp.utils.Utils;
import in.tosc.doandroidlib.DigitalOcean;
import in.tosc.doandroidlib.api.DigitalOceanClient;
import in.tosc.doandroidlib.objects.Droplet;
import in.tosc.doandroidlib.objects.Image;
import in.tosc.doandroidlib.objects.Images;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.tosc.digitaloceanapp.activities.DropletActivity.refreshData;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends android.support.v4.app.Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView dropletRecyclerView;
    DropletsAdapter dropletsAdapter;
    static ArrayList<Droplet> droplets = new ArrayList<>();
    DigitalOceanClient doClient;
    View emptyViewHolder;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //setSupportActionBar(toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        emptyViewHolder = rootView.findViewById(R.id.tview_empty_view_descr);
        dropletRecyclerView = (RecyclerView) rootView.findViewById(R.id.dropletsRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        dropletRecyclerView.setLayoutManager(layoutManager);
        dropletsAdapter = new DropletsAdapter(droplets, getContext()) {
            @Override
            public void onEmptyDataset(List<Droplet> droplets) {
                emptyViewHolder.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFilledDataset(List<Droplet> droplets) {
                emptyViewHolder.setVisibility(View.GONE);
            }
        };
        dropletRecyclerView.setAdapter(dropletsAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DropletCreateActivity.class);
                startActivity(intent);
            }
        });

        doClient = DigitalOcean.getDOClient(Utils.getAuthToken(getContext()));
        refreshData(dropletsAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(dropletsAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

}
