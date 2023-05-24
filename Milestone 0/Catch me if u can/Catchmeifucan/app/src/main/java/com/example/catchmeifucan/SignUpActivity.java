package com.example.catchmeifucan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
Button signUpButton2;
    private EditText fullNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button signUpButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        fullNameInput = findViewById(R.id.full_name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        signUpButton2 = findViewById(R.id.sign_up_button2);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Set click listener on sign up button
        signUpButton2.setOnClickListener(view -> {
            String fullName = fullNameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            // Validate input
            if (TextUtils.isEmpty(fullName)) {
                fullNameInput.setError("Required.");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordInput.setError("Required.");
                return;
            }

            if (!TextUtils.equals(password, confirmPassword)) {
                confirmPasswordInput.setError("Passwords do not match.");
                return;
            }

            // Create new user with Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up successful, navigate to main activity
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(fullName).build();
                                user.updateProfile(profileUpdates);
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            } else {
                                // Sign up failed, display error message
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}