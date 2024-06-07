package com.mirea.kt.android2023.itcalculator;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivityButton(R.id.subnetButton, SubnetActivity.class);
        initActivityButton(R.id.transferButton, TransferActivity.class);
        initActivityButton(R.id.passwordButton, PasswordActivity.class);
        initActivityButton(R.id.poeButton, PoeActivity.class);

    }

    private void initActivityButton(@IdRes int button, Class<?> activity) {
        findViewById(button).setOnClickListener(v -> {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        });
    }
}