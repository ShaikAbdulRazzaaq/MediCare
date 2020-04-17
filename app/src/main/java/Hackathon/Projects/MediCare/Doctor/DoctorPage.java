package Hackathon.Projects.MediCare.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import Hackathon.Projects.MediCare.R;

public class DoctorPage extends AppCompatActivity {
    Button resolveQueries, ViewEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);
        resolveQueries = findViewById(R.id.resolveQueries);
        ViewEditProfile = findViewById(R.id.ViewEditProfile);
        resolveQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorPage.this, resolveQueriesDoctor.class));
            }
        });
        ViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorPage.this, DoctorProfile.class));
            }
        });
    }

}
