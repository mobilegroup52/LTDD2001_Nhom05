package com.example.quanlychitieu.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.MainActivity;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.adapters.SpinnerLanguageAdapter;
import com.example.quanlychitieu.utils.CustomConstant;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private long backPressedTime;
    List<String> languages = new ArrayList<>();
    Spinner spinnerLanguage;
    Button btnSwitchToSignUp, btnSwitchToSignIn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUserLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
//            sharedPreferences = getSharedPreferences("loggingUser", Context.MODE_PRIVATE);
//
//            if (sharedPreferences.getString("role", "").equals(CustomConstant.ROLE_USER)) {
//                // If user logged in and the role is USER, go to MainActivity
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            } else if (sharedPreferences.getString("role", "").equals(CustomConstant.ROLE_ADMIN)) {
//                // If user logged in and the role is ADMIN, go to AdminActivity
//                Intent intent = new Intent(this, AdminActivity.class);
//                startActivity(intent);
//                finish(); // Đóng activi
//            }
        } else {
            setContentView(R.layout.activity_welcome);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }

            initializeElement();
            handleSwitchToRegisterActivity();
            handleSwitchToLoginActivity();
            handleChangeLanguage();
        }
    }

    private void handleChangeLanguage() {
        languages.add(getString(R.string.language_vn));
        languages.add(getString(R.string.language_en));

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(WelcomeActivity.this, item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerLanguageAdapter adapter = new SpinnerLanguageAdapter(WelcomeActivity.this, languages);
        spinnerLanguage.setAdapter(adapter);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("loggingUser", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    private void handleSwitchToLoginActivity() {
        btnSwitchToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleSwitchToRegisterActivity() {
        btnSwitchToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeElement() {
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        btnSwitchToSignUp = findViewById(R.id.btnSwitchToSignUp);
        btnSwitchToSignIn = findViewById(R.id.btnSwitchToSignIn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}