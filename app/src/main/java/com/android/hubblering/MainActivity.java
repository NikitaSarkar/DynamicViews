package com.android.hubblering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.muserapp.R;
import com.android.muserapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextScreen();
            }
        });

    }

    private void inflateUserdata() {
        mainBinding.mtotalReports.setText("Total Reports: " + Constants.musersdata.length());
        UserAdapter mUserAdapter = new UserAdapter(Constants.musersdata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainBinding.muserreports.setLayoutManager(layoutManager);
        mainBinding.muserreports.setAdapter(mUserAdapter);
    }

    private void moveToNextScreen() {
        startActivity(new Intent(this, AddDynamicViewsActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        inflateUserdata();
    }
}
