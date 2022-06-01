package com.hanul.alcoholic.Registe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanul.alcoholic.R;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스
    private EditText mEtMail, mEtpwd,mNickname; // 회원가입 입력 필드
    private Button mBtmRegister; // 회원가입 버튼
    private Button btnBackRegi; // 뒤로가기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("alcoholic");

        mEtMail = findViewById(R.id.et_email);
        mEtpwd = findViewById(R.id.et_pwd);
        mBtmRegister = findViewById(R.id.btn_register);
        mNickname = findViewById(R.id.et_name);

        mBtmRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String strEmail = mEtMail.getText().toString();
                String strPwd = mEtpwd.getText().toString();
                String strName = mNickname.getText().toString();

                // 예외처리 -- 구글 로그인으로 변경하게 되면 수정해야 함!
                if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPwd) || TextUtils.isEmpty(strName)) {
                    AlertDialog.Builder caution = new AlertDialog.Builder(RegisterActivity.this);
                    caution.setMessage("입력하지 않은 정보가 있습니다.");
                    caution.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
                }
                else {
                    //firebase auth 실행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailID(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setNickName(strName);

                                mDatabaseRef.child("USerAccount").child(firebaseUser.getUid()).setValue(account);
                                Toast.makeText(RegisterActivity.this,"회원가입에 성공하셨습니다.",Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }else {
                                Toast.makeText(RegisterActivity.this,"회원가입에 실패하셨습니다.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
        btnBackRegi = findViewById(R.id.btn_back_reg);

        btnBackRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}