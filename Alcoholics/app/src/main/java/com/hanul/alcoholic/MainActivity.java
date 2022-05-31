package com.hanul.alcoholic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanul.alcoholic.Registe.LoginActivity;
import com.hanul.alcoholic.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView userHeaderName;
    private TextView userHeaderEmail;
    private String writer;
    private String email;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private DrawerLayout drawer;

    private long backKeyPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cocktail, R.id.nav_cocktail2, R.id.nav_myCocktail, R.id.nav_community, R.id.nav_logout, R.id.nav_info)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
           @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

               if(destination.getId() == R.id.nav_logout) {
                    //LOGOUT인 경우
                    AlertDialog.Builder oDialog = new AlertDialog.Builder(MainActivity.this);
                    oDialog.setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    //이전 destination으로 돌아가기
                                    onBackPressed();
                                }
                            })
                            .setNeutralButton("예", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(getApplicationContext(), "로그아웃됨", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    //로그아웃 서버에서 가능?
                                }
                            })
                            .setCancelable(false).show();

                }else {}
            }
        });

        //유저이름 불러오기
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference("alcoholic");
        databaseReference.
                child("USerAccount").
                child(user.getUid()).
                child("nickName").
                get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "유저 데이터 읽기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            writer = String.valueOf(task.getResult().getValue());
                            email = String.valueOf(user.getEmail());

                            userHeaderName = findViewById(R.id.menuheader_user_name);
                            userHeaderName.setText(writer);
                            userHeaderEmail = findViewById(R.id.menuheader_user_email);
                            userHeaderEmail.setText(email);
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(Gravity.LEFT)){
            drawer.closeDrawers();
        }else{
            super.onBackPressed();
            /*AlertDialog.Builder oDialog = new AlertDialog.Builder(MainActivity.this);
            oDialog.setMessage("종료하시겠습니까?")
                    .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    })
                    .setNeutralButton("예", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finish();

                        }
                    })
                    .setCancelable(false).show();*/
        }

            //super.onBackPressed();

        //이전화면이 로그인화면이라면 종료 물어보기


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}