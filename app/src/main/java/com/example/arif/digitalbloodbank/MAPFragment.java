package com.example.arif.digitalbloodbank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MAPFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MAP";
    GoogleMap mMap;
    SupportMapFragment mapFragment;


    public MAPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mapfragment, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MyMapID);
        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.MyMapID, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i(TAG, "ON MAP");

        // 24.7308749,91.5402563
        LatLng Middle = new LatLng(24.7308749, 91.5402563);


        ///Sylhet Marker

        LatLng MAG_H = new LatLng(24.9015224,91.8531193 );
        LatLng RED_H = new LatLng(24.897975,91.868328 );
        LatLng JRR_H = new LatLng(24.9131673,91.8528676 );
        LatLng NEM_H = new LatLng(24.8647796,91.8567914 );

        mMap.addMarker(new MarkerOptions().position(MAG_H).title("M.A.G. Osmani Medical College")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(RED_H).title("Red Crescent Sylhet"));
        mMap.addMarker(new MarkerOptions().position(JRR_H).title("Jalalabad Ragib-Rabeya Medical College & Hospital"));
        mMap.addMarker(new MarkerOptions().position(NEM_H).title("North East Medical College & Hospital"));


        /// MoulviBazar

        LatLng MDH_H = new LatLng(24.4813023,91.7659611 );
        LatLng MBP_H = new LatLng(24.4872413,91.7725191 );
        mMap.addMarker(new MarkerOptions().position(MDH_H).title("Moulvibazar District Hospital"));
        mMap.addMarker(new MarkerOptions().position(MBP_H).title("Moulvibazar Polyclinic"));


        /// Sunamganj
        LatLng SSH_H = new LatLng(25.0647059,91.4108044);
        LatLng SGH_H = new LatLng(25.069876,91.4086318);
        mMap.addMarker(new MarkerOptions().position(SSH_H).title("Sunamganj Sadar Hospital"));
        mMap.addMarker(new MarkerOptions().position(SGH_H).title("Sunamganj General Hospital"));





        //Habiganj
        LatLng AZS_H = new LatLng(24.3734165,91.4176521);
        LatLng CHH_H = new LatLng(24.3741036,91.4165966);

        mMap.addMarker(new MarkerOptions().position(AZS_H).title("Adhunik Zilla Sadar Hospital"));
        mMap.addMarker(new MarkerOptions().position(CHH_H).title("Moulvibazar Polyclinic"));







        // mMap.addMarker(new MarkerOptions().position(Metro_UniV).title("Metropolitan University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Middle, (float) 8.5));
    }


}
