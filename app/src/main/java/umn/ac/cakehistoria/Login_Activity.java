package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_Activity extends AppCompatActivity {
    ImageButton btn_GoogleLogin,btn_FBLogin;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    int GOOGLE_SIGN_IN = 0;

    FirebaseAuth.AuthStateListener fbAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //        btn_GoogleLogin = findViewById(R.id.imgBt_googleaccount);
        btn_FBLogin = findViewById(R.id.imgBt_facebookaccount);
        progressBar = findViewById(R.id.pBar_Login);
        mAuth = FirebaseAuth.getInstance();

        fbAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = mAuth.getCurrentUser();
                if(fbUser != null){
                    updateUI(fbUser);
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        // ----------------- GOOGLE SIGN IN BUTTON -------------------------------
        SignInButton googleButton = (SignInButton) findViewById(R.id.google_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_button:
                        Login();
                        break;
                }
            }
        });

        for (int i = 0; i < googleButton.getChildCount(); i++) {
            View v = googleButton.getChildAt(i);

            if (v instanceof TextView)
            {
                TextView tv = (TextView) v;
                tv.setTextSize(14);
                tv.setTypeface(null, Typeface.NORMAL);
                tv.setText("Sign in with Google");
                tv.setTextColor(Color.parseColor("#5e5e5e"));
//                tv.setBackgroundDrawable(getResources().getDrawable(
//                        R.drawable.ic_launcher));
                tv.setSingleLine(true);
//                tv.setPadding(15, 15, 15, 15);
                return;
            }
        }
        // ----------------- GOOGLE SIGN IN BUTTON -------------------------------
    }
    private void Login() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //Intent i = new Intent(Login_Activity.this,MainActivity.class);
            //startActivity(i);
            progressBar.setVisibility(View.INVISIBLE);
            FirebaseGoogleAuth(account);

        }catch (ApiException e ) {
            Toast.makeText(Login_Activity.this,"Sign In Failed",Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login_Activity.this, "Hello, "+ account.getDisplayName(),Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    Toast.makeText(Login_Activity.this, "Failed",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());

            Toast.makeText(Login_Activity.this, "Hello, "+ name,Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this, Login_Activity.class);
            Toast.makeText(this,"Login Gagal",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(fbAuthStateListener);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }


}
