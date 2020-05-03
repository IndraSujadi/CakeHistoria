package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderDone_Activity extends AppCompatActivity {
    private Button btnSeeTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_done_);

        btnSeeTransaction = findViewById(R.id.btnSeeTransaction);
        btnSeeTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Transactions.class);
                startActivity(i);
                finish();
            }
        });
    }
}
