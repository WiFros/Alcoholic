package com.hanul.alcoholic.Registe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanul.alcoholic.MainActivity;
import com.hanul.alcoholic.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private FirebaseAuth googleFirevaseAuth;
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스
    private EditText mEtMail, mEtpwd; // 로그인 입력 필드
    private BeginSignInRequest signInRequest;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private SignInButton buttonGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        googleFirevaseAuth = FirebaseAuth.getInstance();
        buttonGoogle = findViewById(R.id.login_button_google);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("alcoholic");
        mEtMail = findViewById(R.id.et_email);
        mEtpwd = findViewById(R.id.et_pwd);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String strEmail = mEtMail.getText().toString();
                String strPwd = mEtpwd.getText().toString();

                // 예외처리 1. EditText 값이 비어있는 경우, 2. 아이디가 이메일 형식이 아닌 경우
                if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPwd)) {
                    //Toast.makeText(getApplicationContext(), "empty!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder caution = new AlertDialog.Builder(LoginActivity.this);
                    caution.setMessage("아이디 또는 비밀번호를 확인하세요.");
                    caution.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
                }

                else if (!strEmail.contains("@") || !strEmail.contains(".")) {
                    //Toast.makeText(getApplicationContext(), "err!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder caution = new AlertDialog.Builder(LoginActivity.this);
                    caution.setMessage("올바르지 않은 아이디 형식입니다.");
                    caution.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
                }

                else {
                    mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //로그인 성공
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // 현재 액티비티 파괴
                            }else {
                                // 로그인 실패
                                //Toast.makeText(LoginActivity.this,"로그인에 실패하셨습니다.",Toast.LENGTH_LONG).show();
                                AlertDialog.Builder caution = new AlertDialog.Builder(LoginActivity.this);
                                caution.setMessage("아이디 또는 비밀번호를 확인하세요.");
                                caution.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {}
                                }).show();
                            }
                        }
                    });
                }
            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원 가입 화면 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

                //메인 액티비티에서 뒤로가기 눌렀을때 로그인으로 돌아오는 이슈!!!
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        googleFirevaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}