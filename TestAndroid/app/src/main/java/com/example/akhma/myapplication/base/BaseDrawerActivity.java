package com.example.akhma.myapplication.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.akhma.myapplication.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akhma on 9/8/16.
 */

public abstract class BaseDrawerActivity extends AppCompatActivity {

  private Locale mCurrentLocale;

  @BindView(R.id.content_frame)
  protected FrameLayout frameLayout;

  @Override
  @Deprecated
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    super.setContentView(R.layout.drawerlayout);
    onBindView();
    onViewCreated();
  }

  protected void onBindView() {
    ButterKnife.bind(this);
  }

  @Override
  public void setContentView(@LayoutRes int layoutResID) {
    getLayoutInflater().inflate(layoutResID, frameLayout, true);
  }

  protected abstract void onViewCreated();

  @Override
  protected void onRestart() {
    super.onRestart();

    android.os.Handler handler = new android.os.Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Log.i("TX.refresh", "Switching to %s from %s");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
          finish();
          startActivity(getIntent());
        } else recreate();
      }
    }, 1);

  }


}
