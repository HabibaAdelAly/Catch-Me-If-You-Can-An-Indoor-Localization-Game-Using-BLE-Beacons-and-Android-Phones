package com.example.catchmeifucan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StartPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String name_current;
    private String emailInput;
    private Button StartButton;

    private DatabaseReference mDatabase;
// ...



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        StartButton = findViewById(R.id.startPageButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        emailInput=user.getEmail();

        if (user!= null){
            name_current = user.getDisplayName();
            TextView username = findViewById(R.id.Name_text);
            username.setText(name_current);
           // writeNewUser();
        }
        else{
            //no user is signed in
            Toast.makeText(StartPageActivity.this, "Problem. ",
                    Toast.LENGTH_SHORT).show();
        }

        // Set click listener on start up button
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPageActivity.this, WaitingRoomActivity.class));
            }
        });





    }


//    public void writeNewUser( ) {
//        User user = new User(name_current);
//
//       mDatabase.child("users").child(emailInput).setValue(user);
//    }
}