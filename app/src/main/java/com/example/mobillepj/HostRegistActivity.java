package com.example.mobillepj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
class FirebasePost {
    public String uid;
    public String name;
    public String address;
    public String number;

    public FirebasePost() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String uid, String name, String address, String number) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.number = number;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("address", address);
        result.put("number", number);
        return result;
    }
}

public class HostRegistActivity extends AppCompatActivity {

    private static final String TAG = "HostARegist";
    EditText storeNameText, storeAddrText, storeNumText;
    Button nextButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostregist);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HostRegist");

        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        storeNameText = findViewById(R.id.storenameEt);
        storeAddrText = findViewById(R.id.storelocationEt);
        storeNumText = findViewById(R.id.storenumberEt);
        nextButton = findViewById(R.id.storenextBt);

        //파이어베이스 user 로 접근

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //점포 정보 등록
                final String uid = firebaseAuth.getCurrentUser().getUid();
                String name = storeNameText.getText().toString().trim();
                String address = storeAddrText.getText().toString().trim();
                String number = storeNumText.getText().toString().trim();


                if (!(uid.isEmpty() || name.isEmpty() || address.isEmpty() || number.isEmpty())) {

                    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                    Map<String, Object> childUpdates = new HashMap<>();
                    Map<String, Object> postValues = null;

                    FirebasePost post = new FirebasePost(uid, name, address, number);
                    postValues = post.toMap();

                    childUpdates.put("/id_list/" + uid, postValues);
                    dbReference.updateChildren(childUpdates);
                    //modeActivity로 전환
                    Toast.makeText(HostRegistActivity.this, "등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HostRegistActivity.this, ModeActivity.class));

                }else {
                    Toast.makeText(HostRegistActivity.this,"빈 칸이 있는 지 확인해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();// 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}