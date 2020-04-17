package Hackathon.Projects.MediCare.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import Hackathon.Projects.MediCare.ModelClasses.PatientProfileModelClass;
import Hackathon.Projects.MediCare.R;

public class PatientProfile extends AppCompatActivity {
FirebaseAuth  mAuth;
FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
String Email,Gender,DOB,Name;
TextView EmailPatient,phoneNumberPatient;
EditText namePatient,DobPatient;
Button saveProfile;
MaterialButtonToggleGroup materialButtonToggleGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        EmailPatient=findViewById(R.id.EmailPatient);
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        assert firebaseUser != null;
        Email=firebaseUser.getEmail();
        phoneNumberPatient=findViewById(R.id.phoneNumberPatient);
        EmailPatient.setText(Email);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("PatientProfile");
        phoneNumberPatient.setText(firebaseUser.getPhoneNumber());
        namePatient=findViewById(R.id.namePatient);
        DobPatient=findViewById(R.id.DobPatient);
        saveProfile=findViewById(R.id.saveProfile);
        materialButtonToggleGroup=findViewById(R.id.genderPatientProfile);
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId==R.id.PatientGenderMaleProfile){
                        Gender="Male";
                    }else if(checkedId==R.id.PatientGenderFeMaleProfile){
                        Gender="Female";
                    }else {
                        Gender="Others";
                    }
                }
            }
        });
        validateProfilePatient();
    }

    private void validateProfilePatient() {
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DOB=DobPatient.getText().toString();
                Name=namePatient.getText().toString();
                if(TextUtils.isEmpty(Name)||TextUtils.isEmpty(Gender)||TextUtils.isEmpty(DOB)){
                    Toast.makeText(PatientProfile.this, "Please Fill All The Details", Toast.LENGTH_SHORT).show();
                }else{
                    saveDataIntoFireBase();
                }
            }
        });
    }
    private void saveDataIntoFireBase() {
        String id=databaseReference.push().getKey();
        PatientProfileModelClass patientProfileModelClass=new PatientProfileModelClass(id,Gender,Name,DOB);
        assert id != null;
        databaseReference.child(id).setValue(patientProfileModelClass);
        Toast.makeText(this, "Saved SuccessFuly", Toast.LENGTH_SHORT).show();
    }
}
