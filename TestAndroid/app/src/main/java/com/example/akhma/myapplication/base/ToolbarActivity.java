package com.example.akhma.myapplication.base;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.akhma.myapplication.R;

import butterknife.BindView;

/**
 * Created by akhma on 9/8/16.
 */

public abstract class ToolbarActivity extends BaseActivity {
  @BindView(R.id.toolbar)
  protected Toolbar toolbar;

  @Override
  protected void onBindView() {
    super.onBindView();
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
