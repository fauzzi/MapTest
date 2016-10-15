package com.example.akhma.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akhma.myapplication.base.ToolbarActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

/**
 * Created by akhma on 15/10/2016.
 */

public class FirecastsActivity extends ToolbarActivity {

  @BindView(R.id.textCondition)
  TextView mCondition;
  @BindView(R.id.buttonSunny)
  Button mSunny;
  @BindView(R.id.buttonFoggy)
  Button mFoggy;

  DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
  DatabaseReference mConditionRef = mRootRef.child("condition");


  @Override
  protected int getContentViewResource() {
    return R.layout.activity_firecasts;
  }

  @Override
  protected void onViewCreated() {

  }

  @Override
  protected void onStart() {
    super.onStart();
    mConditionRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        String text = dataSnapshot.getValue(String.class);
        mCondition.setText(text);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(FirecastsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });

    mSunny.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mConditionRef.setValue("Sunny");
      }
    });

    mFoggy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mConditionRef.setValue("Foggy");
      }
    });
  }
}
