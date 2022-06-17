package com.example.mobillepj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModeActivity extends AppCompatActivity {
    private static final String TAG = "MY_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        Button clientBt;
        Button hostBt;

        clientBt = findViewById(R.id.clientBt);
        hostBt = findViewById(R.id.hostBt);


        //clientBt 버튼이 눌리면
        clientBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 Clientmain액티비티 함수를 호출한다.
                startActivity(new Intent(ModeActivity.this, ClientmainActivity.class));

            }
        });

        //hostBt 버튼이 눌리면
        hostBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent함수를 통해 hostmain액티비티 함수를 호출한다.
                //DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                String uid = firebaseAuth.getUid();
                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //이미 점포 등록이 되어있으면
                        for (DataSnapshot snapshot : dataSnapshot.child("id_list").getChildren()) {
//                            Log.d(TAG, "dbuid: "+snapshot.child("uid").getValue().toString());
//                            Log.d(TAG, "uid: "+
                            String dbuid = snapshot.child("uid").getValue().toString();
                            if(uid.equals(dbuid)){
                                dbuid = "";
                                startActivity(new Intent(ModeActivity.this, HostMainActivity.class));
                            }
                            //가게 점포 등록이 안되어 있으면
                            else if (!dbuid.isEmpty()) startActivity(new Intent(ModeActivity.this, HostRegistActivity.class));
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}