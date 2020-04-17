package Hackathon.Projects.MediCare.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Hackathon.Projects.MediCare.R;

public class DiagnoseFromPreviousReports extends AppCompatActivity {
Button diabetesButton,cardioVascularDiseaseButton,LiverDiseaseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_from_previous_reports);
        diabetesButton=findViewById(R.id.diabetesButton);
        cardioVascularDiseaseButton=findViewById(R.id.cardioVascularDiseaseButton);
        LiverDiseaseButton=findViewById(R.id.LiverDiseaseButton);

        diabetesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnoseFromPreviousReports.this, Diabetes.class));
            }
        });
        cardioVascularDiseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnoseFromPreviousReports.this, CardioVascularDisease.class));
            }
        });
        LiverDiseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnoseFromPreviousReports.this, LiverDisease.class));
            }
        });
    }
}
