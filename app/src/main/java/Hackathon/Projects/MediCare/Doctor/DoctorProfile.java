package Hackathon.Projects.MediCare.Doctor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import Hackathon.Projects.MediCare.ModelClasses.DoctorProfileModelClass;
import Hackathon.Projects.MediCare.R;

public class DoctorProfile extends AppCompatActivity {
    //vars
    public static final int PICK_IMAGE = 1;
    private static final String TAG = "DoctorProfile";
    Uri filePath;
    //Firebase
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    //widgets
    Button Upload, saveProfileDoctor;
    TextView EmailDoctor, phoneNumberDoctor;
    EditText DegreeDoctor, hospitalName, clinicName, TypeOfDoctor, nameDoctor;
    ImageView imageView;
    //Strings
    String degree, clinic, hospital, typeDoctor, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        EmailDoctor = findViewById(R.id.EmailDoctor);
        phoneNumberDoctor = findViewById(R.id.phoneNumberDoctor);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("DoctorProfileClassModel");
        storageReference = FirebaseStorage.getInstance().getReference("Image");

        if (firebaseUser != null) {
            EmailDoctor.setText(firebaseUser.getEmail());
            phoneNumberDoctor.setText(firebaseUser.getPhoneNumber());
        }
        nameDoctor = findViewById(R.id.nameDoctor);
        saveProfileDoctor = findViewById(R.id.saveProfileDoctor);
        imageView = findViewById(R.id.cerificate);
        Upload = findViewById(R.id.uploadVerification);
        DegreeDoctor = findViewById(R.id.DegreeDoctor);
        hospitalName = findViewById(R.id.hospitalName);
        clinicName = findViewById(R.id.clinicName);
        TypeOfDoctor = findViewById(R.id.TypeOfDoctor);

        validateProfile();

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    private void validateProfile() {
        saveProfileDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree = DegreeDoctor.getText().toString().trim();
                clinic = clinicName.getText().toString().toLowerCase().trim();
                hospital = hospitalName.getText().toString().toLowerCase().trim();
                typeDoctor = TypeOfDoctor.getText().toString().trim();
                name = nameDoctor.getText().toString().trim();
                if (TextUtils.isEmpty(degree) || TextUtils.isEmpty(name) || TextUtils.isEmpty(clinic) || TextUtils.isEmpty(typeDoctor) || TextUtils.isEmpty(hospital)) {
                    Toast.makeText(DoctorProfile.this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
                } else {
                    AddDataToFireBase();
                }
            }
        });
    }

    private void AddDataToFireBase() {
        uploadImage();
        String id = databaseReference.push().getKey();
        DoctorProfileModelClass doctorProfileModelClass = new DoctorProfileModelClass(name, degree, hospital, clinic, typeDoctor, id);
        assert id != null;
        databaseReference.child(id).setValue(doctorProfileModelClass);
        Toast.makeText(this, "Saved Data ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                // Get the Uri of data
                filePath = data.getData();
                try {
                    // Setting image on image view using Bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    // Log the exception
                    e.printStackTrace();
                }
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(
                                UploadTask.TaskSnapshot taskSnapshot) {
                            // Image uploaded successfully
                            // Dismiss dialog
                            progressDialog.dismiss();
                            Toast.makeText(DoctorProfile.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(DoctorProfile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(
                                @NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
}