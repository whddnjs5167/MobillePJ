package com.example.mobillepj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("db").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()) startActivity(new Intent(ModeActivity.this, HostRegistActivity.class));
                            //문서 훑는 반복문
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, "dbuid : " + document.getData().get("uid"));
                                if (document.getData().get("uid").toString().equals(uid)) {
                                    startActivity(new Intent(ModeActivity.this, HostMainActivity.class));
                                }
                            }
                        } else {
                            startActivity(new Intent(ModeActivity.this, HostRegistActivity.class));
                        }
                    }
                });
            }
        });
    }
}