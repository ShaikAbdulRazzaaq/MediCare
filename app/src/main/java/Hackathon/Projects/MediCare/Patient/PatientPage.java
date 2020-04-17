package Hackathon.Projects.MediCare.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Hackathon.Projects.MediCare.R;

public class PatientPage extends AppCompatActivity {
   Button profilePatient,diseaseDetectionSymptoms,DiagnosePatient,AskQueryToDoctor,mindGames,chatBot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);
        profilePatient=findViewById(R.id.profilePatient);
        diseaseDetectionSymptoms=findViewById(R.id.diseaseDetectionSymptoms);
        DiagnosePatient=findViewById(R.id.DiagnosePatient);
        AskQueryToDoctor=findViewById(R.id.AskQueryToDoctor);
        mindGames=findViewById(R.id.mindGames);
        chatBot=findViewById(R.id.chatBot);
        profilePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientPage.this, PatientProfile.class);
                startActivity(intent);
            }
        });
        diseaseDetectionSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientPage.this, Symptoms.class);
                startActivity(intent);
            }
        });
        DiagnosePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientPage.this, DiagnoseFromPreviousReports.class);
                startActivity(intent);
            }
        });
        AskQueryToDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientPage.this, AskQueryToDoctorPatient.class));
            }
        });
        mindGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientPage.this, MindGamesPatient.class));
            }
        });
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientPage.this, ChatBot.class));
            }
        });
    }
}
