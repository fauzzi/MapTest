package com.example.akhma.myapplication.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by akhma on 9/8/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  @Deprecated
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContentViewResource());
    onBindView();
    onViewCreated();
//    initSetGPS();
  }

  protected void onBindView() {
    ButterKnife.bind(this);
  }

  @LayoutRes
  protected abstract int getContentViewResource();

  protected abstract void onViewCreated();

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    try {
      super.recreate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
