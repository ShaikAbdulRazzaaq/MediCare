package Hackathon.Projects.MediCare.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import Hackathon.Projects.MediCare.Login.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        },900);
    }
}
