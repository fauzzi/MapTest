package com.example.akhma.myapplication.base;

/**
 * Created by akhma on 04/09/2016.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.akhma.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public abstract class BaseDemoActivity extends FragmentActivity implements OnMapReadyCallback {
  private GoogleMap mMap;

  protected int getLayoutId() {
    return R.layout.map;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    setUpMap();
  }

  @Override
  protected void onResume() {
    super.onResume();
    setUpMap();
  }

  @Override
  public void onMapReady(GoogleMap map) {
    if (mMap != null) {
      return;
    }
    mMap = map;
    startDemo();
  }

  private void setUpMap() {
    ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
  }

  /**
   * Run the demo-specific code.
   */
  protected abstract void startDemo();

  protected GoogleMap getMap() {
    return mMap;
  }
}
