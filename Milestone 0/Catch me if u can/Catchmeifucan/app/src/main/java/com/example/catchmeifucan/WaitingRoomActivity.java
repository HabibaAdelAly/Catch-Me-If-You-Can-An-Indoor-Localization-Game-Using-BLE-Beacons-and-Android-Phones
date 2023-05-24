package com.example.catchmeifucan;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class WaitingRoomActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Button GoButton;
    private DatabaseReference mDatabase;


   //private Map<String,String> players_names;
    private String name;
    ArrayList<String>users_name;
    private Iterable<DataSnapshot> players_names;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        users_name=new ArrayList<String>();
        GoButton= findViewById(R.id.waiting_button);
        GoButton.setEnabled(false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Game");
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Game").child("Team 1");
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Game").child("Team 2");
        Map<String ,Object> userData=new HashMap<>();
        name= user.getDisplayName();
        userData.put("location",0);
        userData.put("Score",0);


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Game");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            //    name=dataSnapshot.child("Team 1").child("name").getValue(String.class);
             //   Log.d("TAG", "Name" + name);
              //  users_name.add(name);

                if(dataSnapshot.getChildrenCount()<2) {
                    if (dataSnapshot.getChildrenCount() == 0) {
                        mDatabase.child(name);
                        mDatabase.child(name).setValue(userData);
                        TextView username = findViewById(R.id.Name_player1);
                        users_name.add(name);
                        username.setText(mDatabase.child(name).getKey());
                        GoButton.setEnabled(false);
                    } else {
                        mDatabase.child(name);
                        mDatabase.child(name).setValue(userData);
                        TextView username = findViewById(R.id.Name_player2);
                        username.setText(mDatabase.child(name).getKey());
                        users_name.add(name);
                        GoButton.setEnabled(true);
                        GoButton.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(WaitingRoomActivity.this, MainActivity.class));
                            }
                        });
                    }}
                   else {
                      GoButton.setEnabled(false);
                   Toast.makeText(WaitingRoomActivity.this,"Sorry the game if full . Please wait for the next round",Toast.LENGTH_SHORT).show();
                   finish();
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


      //  DatabaseReference usersRef2 = FirebaseDatabase.getInstance().getReference("Player 1");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 players_names = snapshot.getChildren();

                 if (snapshot.getChildrenCount()==2){
                    TextView username = findViewById(R.id.Name_player1);
                    Iterator<DataSnapshot> iterator = players_names.iterator();
                    username.setText(iterator.next().getKey());

                    TextView username2 = findViewById(R.id.Name_player2);
                    username2.setText(iterator.next().getKey());
                    GoButton.setEnabled(true);

                    GoButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(WaitingRoomActivity.this, MainActivity.class));
                        }
                    });


                }
             //  else if (snapshot.getChildrenCount())

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




   }




}