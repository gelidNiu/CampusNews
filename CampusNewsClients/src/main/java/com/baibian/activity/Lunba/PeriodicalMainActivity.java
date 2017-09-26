package com.baibian.activity.Lunba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.baibian.R;


public class PeriodicalMainActivity extends AppCompatActivity implements MainRecycleFragment.OnMyClickListener {
    private MainRecycleFragment mainRecycleFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_main);
        mainRecycleFragment = new MainRecycleFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, mainRecycleFragment);
        transaction.commit();
    }

    @Override
    public void Todetail(String type) {
        Intent intent = new Intent(PeriodicalMainActivity.this,PeriodicalMoreActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
