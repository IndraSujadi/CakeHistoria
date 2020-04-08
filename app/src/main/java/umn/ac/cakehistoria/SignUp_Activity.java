package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUp_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView txt_signin = findViewById(R.id.txt_SignIn);
        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_to_signin_activity = new Intent(SignUp_Activity.this, Login_Activity.class);
                startActivity(intent_to_signin_activity);

            }
        });
    }
}
