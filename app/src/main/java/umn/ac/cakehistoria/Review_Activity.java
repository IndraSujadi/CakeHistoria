package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Review_Activity extends AppCompatActivity {

    ImageView star_rate1,star_rate2,star_rate3,star_rate4,star_rate5;
    EditText edt_testimony;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);

        // -- Programming Pemberian Rating Bintang Start Here --

        star_rate1 = findViewById(R.id.star_rate1);
        star_rate2 = findViewById(R.id.star_rate2);
        star_rate3 = findViewById(R.id.star_rate3);
        star_rate4 = findViewById(R.id.star_rate4);
        star_rate5 = findViewById(R.id.star_rate5);

        star_rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_rate1.setImageResource(R.drawable.rate_clicked);
                star_rate2.setImageResource(R.drawable.rate_not_clicked_yet);
                star_rate3.setImageResource(R.drawable.rate_not_clicked_yet);
                star_rate4.setImageResource(R.drawable.rate_not_clicked_yet);
                star_rate5.setImageResource(R.drawable.rate_not_clicked_yet);
            }
        });

        star_rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_rate1.setImageResource(R.drawable.rate_clicked);
                star_rate2.setImageResource(R.drawable.rate_clicked);
                star_rate3.setImageResource(R.drawable.rate_not_clicked_yet);
                star_rate4.setImageResource(R.drawable.rate_not_clicked_yet);
                star_rate5.setImageResource(R.drawable.rate_not_clicked_yet);
            }
        });

        star_rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_rate1.setImageResource(R.drawable.rate_clicked);
                star_rate2.setImageResource(R.drawable.rate_clicked);
                star_rate3.setImageResource(R.drawable.rate_clicked);
                star_rate4.setImageResource(R.drawable.rate_not_clicked_yet);
                star_rate5.setImageResource(R.drawable.rate_not_clicked_yet);
            }
        });

        star_rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_rate1.setImageResource(R.drawable.rate_clicked);
                star_rate2.setImageResource(R.drawable.rate_clicked);
                star_rate3.setImageResource(R.drawable.rate_clicked);
                star_rate4.setImageResource(R.drawable.rate_clicked);
                star_rate5.setImageResource(R.drawable.rate_not_clicked_yet);
            }
        });

        star_rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star_rate1.setImageResource(R.drawable.rate_clicked);
                star_rate2.setImageResource(R.drawable.rate_clicked);
                star_rate3.setImageResource(R.drawable.rate_clicked);
                star_rate4.setImageResource(R.drawable.rate_clicked);
                star_rate5.setImageResource(R.drawable.rate_clicked);
            }
        });

        // -- Programming Pemberian Rating Bintang Ends Here --

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
            Toast.makeText(Review_Activity.this, "Favourite clicked by user", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_shoppingcart) {
            Toast.makeText(Review_Activity.this, "Shopping cart clicked by user", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
