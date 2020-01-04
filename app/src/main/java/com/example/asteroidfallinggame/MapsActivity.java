package com.example.asteroidfallinggame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;
    private double chosenUserLocationLatitude;
    private double chosenUserLocationLongitude;
    private Location _currentUserLocation;
    int PERMISSION_ID = 44;
    private final static int FRAGMENT_ID = 0x101;
    private int mainActivityWitdh;
    private int mainActivityHeight;
    private LinearLayout linearLayout;
    private TableLayout tableLayout;
    private ApplicationUserModel applicationUserModel;
    private static int currentPoints = 0;
    private static int amountOfGamesPlayed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //getLastLocation();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        applicationUserModel = Global_Variable.applicationUserModel;
        mainActivityWitdh = getResources().getDisplayMetrics().widthPixels;
        mainActivityHeight = getResources().getDisplayMetrics().heightPixels;
        linearLayout = findViewById(R.id.linear_layout);
        addHeadLine();
        //addImage();
        currentPoints = getIntent().getExtras().getInt(Global_Variable.MY_POINTS);
        amountOfGamesPlayed++;
        if (currentPoints >= applicationUserModel.getMaxPointAchieved())
            applicationUserModel.setMaxPointAchieved(currentPoints);
        InitialTableLayout();
        addTableScores();
        linearLayout.addView(tableLayout);
        addButton();
        addMapFragment();
        addRowToTableFromFireBase();
    }
    public void addMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    chosenUserLocationLatitude  = location.getLatitude();
                                    chosenUserLocationLongitude = location.getLongitude();
                                    setLocation();
                                    Global_Variable.fireBaseUtill.saveScoreSheet(applicationUserModel.getUsername(),applicationUserModel.getMaxPointAchieved(),new LatLng(chosenUserLocationLatitude,chosenUserLocationLongitude));
                                    applicationUserModel.setMaxPointAchieved(0);
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            //Location mLastLocation = locationResult.getLastLocation();
            //latTextView.setText(mLastLocation.getLatitude()+"");
            //lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }
    private void setLocation(){
        if(mMap!= null) {
            LatLng location = new LatLng(chosenUserLocationLatitude,chosenUserLocationLongitude);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title(""));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
        addRowToTableFromFireBase();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        setLocation();
    }
    private void addHeadLine(){
        //Set Button Settings
        Button welcomeButton = new Button(this);
        welcomeButton.setText(Global_Variable.SCORE_SHEET);
        welcomeButton.setTextSize(mainActivityWitdh/28);
        welcomeButton.setWidth(mainActivityWitdh);
        welcomeButton.setHeight(mainActivityHeight/7);
        welcomeButton.setEnabled(false);;
        welcomeButton.setBackgroundResource(R.color.headerColor);
        welcomeButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(welcomeButton);

    }
    private void addButton(){
        Button loginButton = new Button(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MapsActivity.this,
                        GameActivity.class);
                startActivity(myIntent);
            }
        });
        loginButton.setText(Global_Variable.NEW_GAME);
        LinearLayout.LayoutParams loginButtonLayoutParams =
                new LinearLayout.LayoutParams((int)(mainActivityWitdh *0.5),mainActivityHeight/20);
        loginButtonLayoutParams.gravity = Gravity.CENTER;
        loginButtonLayoutParams.setMargins(0
                ,mainActivityHeight/13
                ,0
                ,mainActivityHeight/13);
        loginButton.setLayoutParams(loginButtonLayoutParams);
        loginButton.setBackgroundResource(R.color.loginViewColor);
        loginButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(loginButton);
    }
    private void InitialTableLayout(){
        tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
    }
    private void addTableScores(){
        // add columns
        TableRow row = new TableRow(this);
        row.addView(getNewTextViewWithStringContent(Global_Variable.USERNAME));
        row.addView(getNewTextViewWithStringContent(Global_Variable.SCORE));
        row.setBackgroundColor(Color.DKGRAY);
        tableLayout.addView(row);
    }
    private TextView getNewTextViewWithStringContent(String content){
        TextView UserNametextView = new TextView(this);
        UserNametextView.setText(content);
        UserNametextView.setTextSize(mainActivityWitdh/40);
        UserNametextView.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        UserNametextView.setGravity(Gravity.CENTER);
        return UserNametextView;
    }
    private void addRowToTable(String userName,int Score,LatLng location){
        TableRow row = new TableRow(this);
        row.addView(getNewTextViewWithStringContent(userName));
        row.addView(getNewTextViewWithStringContent(String.valueOf(Score)));
        tableLayout.addView(row);
        mMap.addMarker(new MarkerOptions().position(location).title(userName));
    }
    private void addRowToTableFromFireBase(){
        Global_Variable.fireBaseUtill.getRefrencesScoresSheet()
                .orderByChild(Global_Variable.SCORE_COLUMN)
                .limitToLast(Global_Variable.TOP_RESULTS)
                .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tableLayout.removeAllViews();
                        addTableScores();
                        for(DataSnapshot usersSnapShot: dataSnapshot.getChildren()){
                            try {
                                String userName = usersSnapShot.child(Global_Variable.USERNAME_COLUMN).getValue().toString();
                                String Score = usersSnapShot.child(Global_Variable.SCORE_COLUMN).getValue().toString();
                                String latitude  = usersSnapShot.child(Global_Variable.LATITUDE_COLUMN).getValue().toString();
                                String longitude = usersSnapShot.child(Global_Variable.LONGITUDE_COLUMN).getValue().toString();
                                LatLng location = new LatLng(
                                         Double.valueOf(latitude)
                                        ,Double.valueOf(longitude));
                                addRowToTable(userName,Integer.valueOf(Score),location);
                            }
                            catch(Exception e){
                                e.getMessage();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
