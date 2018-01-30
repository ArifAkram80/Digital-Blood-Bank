package com.example.arif.digitalbloodbank;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MAPFragment extends Fragment implements OnMapReadyCallback, GetLoctionUpdates{

    private static final String TAG = "MAP";
    GoogleMap mMap;
    SupportMapFragment mapFragment;

    FloatingActionButton fab ;
    Bitmap smallHos,smallMyPos;


    // GPSTracker class
    GPSTracker gps;

    // LogCat tag
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111;


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


        fab = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButton);


        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.myposition);
        Bitmap m=bitmapdraw.getBitmap();
        smallMyPos = Bitmap.createScaledBitmap(m, width, height, false);

        BitmapDrawable bitmapdraw2=(BitmapDrawable)getResources().getDrawable(R.drawable.hosicon);
        Bitmap h=bitmapdraw2.getBitmap();
        smallHos = Bitmap.createScaledBitmap(h, width, height, false);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i(TAG, "ON MAP");


        AddHospitalMarker();

        if(ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            gps = new GPSTracker(getActivity(), this);
            if (gps.location != null) {
                CenterMapLocation(gps.location, "My Location");
            }
        }
        else{
            GetLocationPermission();
        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CenterMapLocation(gps.location, "Your Location");
            }
        });



    }

    private void AddHospitalMarker() {

        ///Sylhet Marker

        LatLng MAG_H = new LatLng(24.9015224,91.8531193 );
        LatLng RED_H = new LatLng(24.897975,91.868328 );
        LatLng JRR_H = new LatLng(24.9131673,91.8528676 );
        LatLng NEM_H = new LatLng(24.8647796,91.8567914 );
        LatLng IBN_H = new LatLng(24.891809, 91.877650);
        LatLng SDH_H = new LatLng(24.893352, 91.870866 );
        LatLng ALH_H = new LatLng(24.888937, 91.879772 );
        LatLng UNP_H = new LatLng(24.895157,91.8686158 );

        mMap.addMarker(new MarkerOptions().position(MAG_H).title("M.A.G. Osmani Medical College").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(RED_H).title("Red Crescent Sylhet").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(JRR_H).title("Jalalabad Ragib-Rabeya Medical College & Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(NEM_H).title("North East Medical College & Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(IBN_H).title("Ibn Sina Hospital Sylhet Ltd.").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(SDH_H).title("Sylhet Diabetic Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(ALH_H).title("Al Haramain Hospital (Private) Limited").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(UNP_H).title("United Poly Clinic").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));


        /// MoulviBazar

        LatLng MDH_H = new LatLng(24.4813023,91.7659611 );
        LatLng MBP_H = new LatLng(24.4872413,91.7725191 );
        mMap.addMarker(new MarkerOptions().position(MDH_H).title("Moulvibazar District Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(MBP_H).title("Moulvibazar Polyclinic").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));


        /// Sunamganj
        LatLng SSH_H = new LatLng(25.0647059,91.4108044);
        LatLng SGH_H = new LatLng(25.069876,91.4086318);
        mMap.addMarker(new MarkerOptions().position(SSH_H).title("Sunamganj Sadar Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(SGH_H).title("Sunamganj General Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));





        //Habiganj
        LatLng AZS_H = new LatLng(24.3734165,91.4176521);
        LatLng CHH_H = new LatLng(24.3741036,91.4165966);

        mMap.addMarker(new MarkerOptions().position(AZS_H).title("Adhunik Zilla Sadar Hospital").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));
        mMap.addMarker(new MarkerOptions().position(CHH_H).title("Moulvibazar Polyclinic").icon(BitmapDescriptorFactory.fromBitmap(smallHos)));

    }

    protected void GetLocationPermission(){
        if(Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("ARIFFFF", "Permission");
            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void CenterMapLocation(Location location, String s) {
        
        if(location != null)
        {
            LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            AddHospitalMarker();
            mMap.addMarker(new MarkerOptions().position(userLoc).title("Your Location").icon(BitmapDescriptorFactory.fromBitmap(smallMyPos)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc,15));
        }else{
            Toast.makeText(getActivity(), "Determining Your Location... Turn On GPS", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void OnLocationChangedInterface(Location location) {
        CenterMapLocation(location, "Your Location");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111) {
            if (grantResults.length > 0) {
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    Log.i("ARIFFF", "Dukse -_- ");
                    gps = new GPSTracker(getActivity(), this);

                    gps.getLocation();

                    if (gps.location != null) {
                        CenterMapLocation(gps.location, "My Location");
                    }
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), " Permission Not Granted ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

