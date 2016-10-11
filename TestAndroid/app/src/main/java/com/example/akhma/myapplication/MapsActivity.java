package com.example.akhma.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.akhma.myapplication.base.ToolbarActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;

public class MapsActivity extends ToolbarActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
  GoogleApiClient.OnConnectionFailedListener,
  GoogleMap.OnMarkerDragListener,
  GoogleMap.OnMapLongClickListener {

  private GoogleMap mMap;
  //To store longitude and latitude from map
  private double longitude;
  private double latitude;

  private GoogleApiClient googleApiClient;


  @Override
  protected int getContentViewResource() {
    return R.layout.activity_maps;
  }

  @Override
  protected void onViewCreated() {

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
    try {
      if (mMap == null) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
          .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
      }
//      mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //Initializing googleapi client
    googleApiClient = new GoogleApiClient.Builder(this)
      .addConnectionCallbacks(this)
      .addOnConnectionFailedListener(this)
      .addApi(LocationServices.API)
      .build();
  }

  @Override
  protected void onStart() {
    googleApiClient.connect();
    super.onStart();
  }

  @Override
  protected void onStop() {
    googleApiClient.disconnect();
    super.onStop();
  }

  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    // Add a marker in Sydney and move the camera
    LatLng latLng = new LatLng(-34, 151);

//    mMap.addMarker(new MarkerOptions().position(latLng ).title("Marker in Sydney"));
//    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng ));

    //Adding marker to that coordinate
    mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    //Setting onMarkerDragListener to track the marker drag
    mMap.setOnMarkerDragListener(this);
    //Adding a long click listener to the map
    mMap.setOnMapLongClickListener(this);
  }


  //Getting current location
  private Location getCurrentLocation() {
    Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

    //Creating a location object
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return location;
    }

    if (location != null) {
      //Getting longitude and latitude
      longitude = location.getLongitude();
      latitude = location.getLatitude();

      Log.e("Fauzi", "" + latitude + ", " + longitude);

      //moving the map to location
      moveMap();
    }
    return location;
  }

  //Function to move the map
  private void moveMap() {
    //String to display current latitude and longitude
    String msg = latitude + ", " + longitude;
    Log.e("fauzi", msg);

    //Creating a LatLng Object to store Coordinates
    LatLng latLng = new LatLng(latitude, longitude);

    //Adding marker to map
    mMap.addMarker(new MarkerOptions()
      .position(latLng) //setting position
      .draggable(true) //Making the marker draggable
      .title("Current Location")); //Adding a title

    //Moving the camera
    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    //Animating the camera
    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    //Displaying current coordinates in toast
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_current:
        getCurrentLocation();
        moveMap();
        return true;
      case R.id.action_save:
        return true;
      case R.id.action_view:
        return true;
      case R.id.action_distance:
        startActivity(new Intent(MapsActivity.this, DistanceCalculationActivity.class));
        return true;
      case R.id.action_nearby_place:
        startActivity(new Intent(MapsActivity.this, NearbyPlaceActivity.class));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    getCurrentLocation();
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override
  public void onMapLongClick(LatLng latLng) {
    //Clearing all the markers
    mMap.clear();

    //Adding a new marker to the current pressed position we are also making the draggable true
    mMap.addMarker(new MarkerOptions()
      .position(latLng)
      .draggable(true));
  }

  @Override
  public void onMarkerDragStart(Marker marker) {

  }

  @Override
  public void onMarkerDrag(Marker marker) {

  }

  @Override
  public void onMarkerDragEnd(Marker marker) {
    //Getting the coordinates
    latitude = marker.getPosition().latitude;
    longitude = marker.getPosition().longitude;

    //Moving the map
    moveMap();
  }
}
