package Hackathon.Projects.MediCare.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import Hackathon.Projects.MediCare.R;

public class AdminPage extends AppCompatActivity {
    ImageButton doctor1, doctor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        doctor1 = findViewById(R.id.doctor1);
        doctor2 = findViewById(R.id.doctor2);
        doctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPage.this, Doctor1.class));
            }
        });
        doctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPage.this, Doctor2.class));
            }
        });
    }
}

