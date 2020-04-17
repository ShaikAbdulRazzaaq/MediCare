package Hackathon.Projects.MediCare.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import Hackathon.Projects.MediCare.Admin.AdminPage;
import Hackathon.Projects.MediCare.Doctor.DoctorPage;
import Hackathon.Projects.MediCare.Patient.PatientPage;
import Hackathon.Projects.MediCare.R;

public class MainActivity extends AppCompatActivity {

    String EmailID, Password, VerificationID, PhoneNumber,UserID;
    FirebaseUser CurrentUser;
    FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    //Widgets
    EditText emailAddress, password,phoneNumber;
    TextView SignUp;
    Button LoginwithEmail,LoginAsAdmin;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    PhoneAuthProvider phoneAuthProvider;
     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailAddress = findViewById(R.id.username);
        password = findViewById(R.id.password);
        SignUp = findViewById(R.id.signUp);
        phoneNumber=findViewById(R.id.phoneNumberSignin);
        LoginwithEmail = findViewById(R.id.loginwithEmail);
        LoginAsAdmin=findViewById(R.id.login);
        progressDialog=new ProgressDialog(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        phoneAuthProvider = PhoneAuthProvider.getInstance();
        signUpClicked();
        ValidatingTheLoginPage();
    }

    private void signUpClicked() {
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ValidatingTheLoginPage() {
        LoginwithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailID = emailAddress.getText().toString().toLowerCase().trim();
                Password = password.getText().toString().trim();
                if ((TextUtils.isEmpty(EmailID) || TextUtils.isEmpty(Password))) {
                    Toast.makeText(MainActivity.this, "Please Fill the Required Fields", Toast.LENGTH_SHORT).show();
                } else {
                        if (!isValidMail(EmailID)) {
                            Toast.makeText(MainActivity.this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                        }else{
                            authenticatingTheLoginPage();
                        }
                }
                }
        });
        LoginAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber=phoneNumber.getText().toString().trim();
                if(TextUtils.isEmpty(PhoneNumber)){
                    Toast.makeText(MainActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                }else{
                    if(!isValidMobile(PhoneNumber)){
                        Toast.makeText(MainActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    }else {
                        signingwithPhoneNumber(PhoneNumber);
                    }
                }
            }
        });
    }

    private void signingwithPhoneNumber(String phone) {

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                VerificationID = s;
            }
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                final String s = phoneAuthCredential.getSmsCode();
                mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Log.e(TAG, "" + task.getResult() + "\n" + VerificationID);
                            Toast.makeText(MainActivity.this, "Otp Code:"+s, Toast.LENGTH_LONG).show();
                            CurrentUser=mAuth.getCurrentUser();
                            assert CurrentUser != null;
                            UserID=CurrentUser.getUid();
                            if(UserID.equals("45l8ruQ5JvNApboiG6J88E1WvrK2")){
                                Intent intent=new Intent(MainActivity.this, AdminPage.class);
                                intent.putExtra("User",UserID);
                                finish();
                                startActivity(intent);
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "You Are not an Admin", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.e(TAG, s);
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
                Log.e("TAG", "" + e.getMessage());
            }
        };
        if (phone != null) {
            Log.e(TAG, "Verifying");
            phoneAuthProvider.verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, mCallBacks);
            progressDialog.setMessage("Processing");
            progressDialog.show();
        }
    }

    private void authenticatingTheLoginPage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(EmailID, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    CurrentUser=mAuth.getCurrentUser();
                    assert CurrentUser != null;
                    UserID=CurrentUser.getUid();
                    if(UserID.equals("Ee07s5ZnxeNuftRU57axlag7WaT2")){
                        Intent intent=new Intent(MainActivity.this, DoctorPage.class);
                        intent.putExtra("User",UserID);
                        finish();
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(MainActivity.this, PatientPage.class);
                        intent.putExtra("User",UserID);
                        finish();
                        startActivity(intent);
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}