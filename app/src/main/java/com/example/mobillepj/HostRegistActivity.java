package com.example.mobillepj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
class FirebasePost {
    public String uid;
    public String name;
    public String category;
    public String address;
    public String phoneNum;
    public String openClose;
    public String maxSeat;


    public FirebasePost() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String uid, String name, String catoegory, String address, String phoneNum, String openClose,  String maxSeat) {
        this.uid = uid;
        this.name = name;
        this.category = catoegory;
        this.address = address;
        this.phoneNum = phoneNum;
        this.openClose = openClose;
        this.maxSeat = maxSeat;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("category", category);
        result.put("address", address);
        result.put("phoneNum", phoneNum);
        result.put("openClose", openClose);
        result.put("maxSeat", maxSeat);
        return result;
    }
}

public class HostRegistActivity extends AppCompatActivity {

    private static final String TAG = "HostARegist";
    EditText storeNameText, storeAddrText, storeNumText, storeMaxSeatText, storeOpenCloseText, storeCategoryText;
    Button nextButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostregist);

        //?????? ??? ????????????
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HostRegist");

        actionBar.setDisplayHomeAsUpEnabled(true); //??????????????????
        actionBar.setDisplayShowHomeEnabled(true); //??? ?????????

        //?????????????????? ?????? ??????
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        storeNameText = findViewById(R.id.storeNameEt);
        storeCategoryText = findViewById(R.id.storeCategoEt);
        storeAddrText = findViewById(R.id.storeLocationEt);
        storeNumText = findViewById(R.id.storeNumberEt);
        storeMaxSeatText = findViewById(R.id.storeMaxSeatEt);
        storeOpenCloseText = findViewById(R.id.storeOpenCloseEt);
        nextButton = findViewById(R.id.storenextBt);

        //?????????????????? user ??? ??????

        //???????????? ???????????????   -->  firebase??? ???????????? ????????????.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //?????? ?????? ??????
                final String uid = firebaseAuth.getCurrentUser().getUid();
                String name = storeNameText.getText().toString().trim();
                String category = storeCategoryText.getText().toString().trim();
                String address = storeAddrText.getText().toString().trim();
                String phoneNum = storeNumText.getText().toString().trim();
                String openClose = storeOpenCloseText.getText().toString().trim();
                String maxSeat = storeMaxSeatText.getText().toString().trim();



                if (!(uid.isEmpty() || name.isEmpty() || category.isEmpty() || address.isEmpty() || phoneNum.isEmpty()) || openClose.isEmpty() || maxSeat.isEmpty()) {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> storeData= null;

                    FirebasePost post = new FirebasePost(uid, name, category, address, phoneNum, openClose, maxSeat);
                    storeData = post.toMap();

                    db.collection("db").document(uid).set(storeData);

                    //modeActivity??? ??????
                    Toast.makeText(HostRegistActivity.this, "????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HostRegistActivity.this, ModeActivity.class));

                }else {
                    Toast.makeText(HostRegistActivity.this,"??? ?????? ?????? ??? ??????????????????.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();// ???????????? ????????? ????????????
        return super.onSupportNavigateUp(); // ???????????? ??????
    }
}