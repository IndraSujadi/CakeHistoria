package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tunjuk Button
        ImageButton btn_Home = findViewById(R.id.btn_Home);
        ImageButton btn_Order = findViewById(R.id.btn_Order);
        ImageButton btn_Account = findViewById(R.id.btn_Account);

        final Frag_Home home =new Frag_Home();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Frame,home);
        fragmentTransaction.commit();


        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frame,home);
                fragmentTransaction.commit();
            }
        });

        btn_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frag_Account account =new Frag_Account();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frame,account);
                fragmentTransaction.commit();
            }
        });
        

    }
}
