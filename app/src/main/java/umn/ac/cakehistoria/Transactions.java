package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btn_onprogress = findViewById(R.id.btn_onprogess);
        Button btn_done = findViewById(R.id.btn_done);

        onprogress onprogress_status_frag = new onprogress();
        onprogress_status_frag.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_frame, onprogress_status_frag);
        fragmentTransaction.commit();

        btn_onprogress.setBackgroundColor(Color.parseColor("#d14900"));
        btn_onprogress.setTextColor(Color.parseColor("#ffffff"));

        btn_done.setBackgroundColor(Color.parseColor("#f2f2f2"));
        btn_done.setTextColor(Color.parseColor("#000000"));

        btn_onprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onprogress onprogress_status_frag = new onprogress();
                onprogress_status_frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_frame, onprogress_status_frag);
                fragmentTransaction.commit();

                btn_onprogress.setBackgroundColor(Color.parseColor("#d14900"));
                btn_onprogress.setTextColor(Color.parseColor("#ffffff"));

                btn_done.setBackgroundColor(Color.parseColor("#f2f2f2"));
                btn_done.setTextColor(Color.parseColor("#000000"));

            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                done done_status_frag = new done();
                done_status_frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_frame, done_status_frag);
                fragmentTransaction.commit();

                btn_done.setBackgroundColor(Color.parseColor("#d14900"));
                btn_done.setTextColor(Color.parseColor("#ffffff"));

                btn_onprogress.setBackgroundColor(Color.parseColor("#f2f2f2"));
                btn_onprogress.setTextColor(Color.parseColor("#000000"));

            }
        });

        // impelementasi toolbar atas starts here.

//        Toolbar toolbar = findViewById(R.id.review_toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.ReviewToolbar));
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // implementasi toolbar atas ends here.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_favourite) {
            Toast.makeText(Transactions.this, "Favourite clicked by user", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_shoppingcart) {
            Toast.makeText(Transactions.this, "Shopping cart clicked by user", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
