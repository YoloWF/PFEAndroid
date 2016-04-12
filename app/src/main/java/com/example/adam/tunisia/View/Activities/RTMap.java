package com.example.adam.tunisia.View.Activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Helpers.GeoHelper;
import com.example.adam.tunisia.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RTMap extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private MarkerOptions position;


    Marker X;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtmap);


        DBAdapterSociete A = new DBAdapterSociete(this);
        A.open();

        ArrayList<CharSequence> L = new ArrayList<CharSequence>();

        for( Societe S : A.getAllSociete() ){
            L.add(S.getNOMCOMPLET());
        }


        CharSequence [] items = new CharSequence[L.size()];
        items = L.toArray(items);

        A.close();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                drawNetwork();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        LatLng sydney = new LatLng(36.85, 10.27);
        position = new MarkerOptions().position(sydney).title("Marker in Sydney");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(36.85, 10.27);
        mMap.addMarker(position);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

         X =   this.mMap.addMarker(new MarkerOptions().position(new LatLng(36.856433, 10.281315)));

    }

    @Override
    protected void onStop(){
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);



        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

       animateMarkerTo(X,X.getPosition().latitude+0.005,X.getPosition().longitude+0.005);

        Toast.makeText(this,location.toString(),Toast.LENGTH_LONG).show();
       // position.position(new LatLng(location.getLatitude(),location.getLongitude()));

        //mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void drawLine(ArrayList<Station_Ligne> ListeDesLignes){





        //CREATING THE POLYLINE
        PolylineOptions P = new PolylineOptions();
        P.geodesic(true);

        for(Station_Ligne SL : ListeDesLignes ) {


            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())))
                    .radius(20)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(6);


            if(SL.getSTATION().isMAJEURE()) {
                mMap.addMarker(new MarkerOptions().title(SL.getSTATION().getNOM())
                        .snippet(SL.getSTATION().getNOM())
                        .position(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                circleOptions.radius(20).fillColor(Color.BLACK).strokeColor(Color.BLACK);
            }



            P.add(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())));
            mMap.addCircle(circleOptions);
        }
        P.width(4).color(Color.GRAY);



        // ADDING THE POLYLINE
        mMap.addPolyline(P);

    }

    public void drawNetwork(){

        // CAMERA SETTING VARIABLES
        // -181 & +181 are the bounds of the latlng
        double MaxLNG = -181.0;
        double MinLNG = 181;
        double MaxLAT = -181.0;
        double MinLAT = 181.0;
        int Zoom;


        DBAdapterLigne myDBLigne;
        DBAdapterStation_Ligne myDBStation_Ligne;
        DBAdapterStation myDBStation;

        myDBLigne = new DBAdapterLigne(this);
        myDBStation_Ligne = new DBAdapterStation_Ligne(this);
        myDBStation = new DBAdapterStation(this);

        myDBStation.open();
        myDBStation_Ligne.open();
        myDBLigne.open();



        try {

            List<ArrayList<Station_Ligne>> Network = new ArrayList<ArrayList<Station_Ligne>>();

            // GET LINES
            List<Ligne> LL = myDBLigne.getAllLigneBySocieteAller("id");
            // FILL LINES
            for( Ligne L : LL) {

                // GET STATIONS
                ArrayList<Station_Ligne> SLL = myDBStation_Ligne.getAllStation_LigneByLigne(L.getROW_ID());
                // FILL STATIONS
                for( Station_Ligne SL : SLL){

                    SL.setSTATION(myDBStation.getStation(SL.getSTATION().getROW_ID()));

                    if(Double.parseDouble(SL.getSTATION().getLATITUDE())>MaxLAT){
                        MaxLAT=Double.parseDouble(SL.getSTATION().getLATITUDE());
                    }else if(Double.parseDouble(SL.getSTATION().getLATITUDE())<MinLAT){
                        MinLAT=Double.parseDouble(SL.getSTATION().getLATITUDE());
                    }

                    if(Double.parseDouble(SL.getSTATION().getLONGITUDE())>MaxLNG){
                        MaxLNG=Double.parseDouble(SL.getSTATION().getLONGITUDE());
                    }
                    if(Double.parseDouble(SL.getSTATION().getLONGITUDE())<MinLNG){
                        MinLNG=Double.parseDouble(SL.getSTATION().getLONGITUDE());
                    }

                }

                Collections.sort(SLL);

                drawLine(SLL);
             //   Network.add(SLL);

            }

           // SCMap.drawNetwork(Network);

            myDBStation_Ligne.close();
            myDBStation.close();
            myDBLigne.close();

        }catch(Exception e){
            //Log.v(TAG,"An exception has been thrown");
            e.printStackTrace();
        }
///////////////
      //  Zoom = GeoHelper.getZoomLevel(Math.max(GeoHelper.distFrom(MaxLAT,0,MinLAT,0),GeoHelper.distFrom(MaxLNG,0,MinLNG,0)));

        // SETTING CAMERA POSITION
       //////////////////////////////
        // SCMap.setCameraPosition((MaxLAT+MinLAT)/2,(MaxLNG+MinLNG)/2, Zoom);
    }

    private void animateMarkerTo(final Marker marker, final double lat, final double lng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long DURATION_MS = 3000;
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final LatLng startPosition = marker.getPosition();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float elapsed = SystemClock.uptimeMillis() - start;
                float t = elapsed/DURATION_MS;
                float v = interpolator.getInterpolation(t);

                double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                marker.setPosition(new LatLng(currentLat, currentLng));

                // if animation is not finished yet, repeat
                if (t < 1) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }




}
