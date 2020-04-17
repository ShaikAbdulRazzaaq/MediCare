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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import Hackathon.Projects.MediCare.R;

public class SignUpActivity extends AppCompatActivity {
    //Widgets
    EditText phoneNumber, Email, password;
    Button SignUp;
    TextView SignIn;
    //Vars
    FirebaseAuth mAuth;
    PhoneAuthProvider phoneAuthProvider;
    String PhoneNumber;
    String EmailID;
    String passwordSignUp;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phoneAuthProvider = PhoneAuthProvider.getInstance();
        phoneNumber = findViewById(R.id.phoneNumberSignup);
        Email = findViewById(R.id.usernameSignUp);
        SignUp = findViewById(R.id.SignUp);
        SignIn = findViewById(R.id.signIn);
        password = findViewById(R.id.passwordSignUp);
        mAuth = FirebaseAuth.getInstance();
        signInClicked();
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneNumber = phoneNumber.getText().toString().trim();
                EmailID = Email.getText().toString().toLowerCase().trim();
                passwordSignUp = password.getText().toString().trim();
                if (TextUtils.isEmpty(EmailID) || TextUtils.isEmpty(passwordSignUp) || TextUtils.isEmpty(PhoneNumber)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                    phoneNumber.requestFocus();
                } else {
                    signUpAuthentication();
                }
            }
        });
    }
    private void signUpAuthentication() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(EmailID, passwordSignUp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Log.d(TAG,"Sign Up Done");
                    Toast.makeText(SignUpActivity.this, "Registered SuccessFully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);

                } else {
                    Log.d(TAG,"Something is wrong in while Signing In");
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Something is Wrong; Registration Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signInClicked() {
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Sign in Button Clicked Sending To Sign In Activity");
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}