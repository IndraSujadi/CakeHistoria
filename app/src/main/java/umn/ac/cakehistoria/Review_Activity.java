package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Ordering;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Review_Activity extends AppCompatActivity {


    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Views
    private ImageView star_rate1,star_rate2,star_rate3,star_rate4,star_rate5;
    private EditText edt_testimony;
    private TextView txt_Title, txt_cake_category, txtNameReceiver, txtEmailReceiver, txtNomorReceiver, txtAlamatReceiver
            , txtHargaProduk, txtHargaDeliv, txtTotalPrice;

    private ImageView img_cake_pict;

    private Button btn_submit_review;

    private String cakeID;
    private String orderID;

    private int rating = 0;
    private String testimonyText = "";

    ImageButton btnBack;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        cakeID = i.getStringExtra("cakeID");
        orderID = i.getStringExtra("orderID");

        btnBack = findViewById(R.id.btnBack);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //untuk add action, harus ke manifest "android:parentActivityName"
//
//        Toolbar toolbar = findViewById(R.id.review_toolbar);
//        setSupportActionBar(toolbar);

        txt_Title = findViewById(R.id.txt_Title);
        txt_cake_category = findViewById(R.id.txt_cake_category);
        txtNameReceiver = findViewById(R.id.txtNameReceiver);
        txtEmailReceiver = findViewById(R.id.txtEmailReceiver);
        txtNomorReceiver = findViewById(R.id.txtNomorReceiver);
        txtAlamatReceiver = findViewById(R.id.txtAlamatReceiver);
        txtHargaProduk = findViewById(R.id.txtHargaProduk);
        txtHargaDeliv = findViewById(R.id.txtHargaDeliv);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        edt_testimony = findViewById(R.id.edt_testimony);
        img_cake_pict = findViewById(R.id.img_cake_pict);

        btn_submit_review = findViewById(R.id.btn_submit_review);

        DocumentReference dbCakes = db.collection("Cakes").document(cakeID);
        dbCakes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map subdoc = document.getData();
                        Map cakeDetails = (Map) subdoc.get("CakeDetails");

                        String imageURL = (String) document.get("imageURL");

                        if (imageURL != null && !imageURL.trim().isEmpty()) {
                            Picasso.get().load((String) document.get("imageURL")).into(img_cake_pict);
                        } else {
                            Picasso.get()
                                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                                    .into(img_cake_pict);
                        }

                        txt_Title.setText((String) document.get("cakeCategory") + " Custom Cake");
                        txt_cake_category.setText((String) document.get("cakeCategory"));
                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });

        DocumentReference dbOrders = db.collection("Orders").document(orderID);
        dbOrders.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map subdoc = document.getData();
                        Map receiverInfo = (Map) subdoc.get("receiverInfo");

                        txtNameReceiver.setText((String) receiverInfo.get("receiverName"));
                        txtEmailReceiver.setText((String) receiverInfo.get("receiverEmail"));
                        txtNomorReceiver.setText((String) receiverInfo.get("receiverPhone"));

                        String refAlamat = (String) receiverInfo.get("receiverFullAddress") + ", " + (String) receiverInfo.get("receiverKecamatan")
                                + ", " + (String) receiverInfo.get("receiverKota") + ", " + (String) receiverInfo.get("receiverProvinsi")
                                + ", " + (String) receiverInfo.get("receiverZip");
                        txtAlamatReceiver.setText(refAlamat);

                        int refHargaProduk = document.get("cakePrice", Integer.class);
                        txtHargaProduk.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refHargaProduk))));

                        int refHargaDeliv = document.get("delivPrice", Integer.class);
                        txtHargaDeliv.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refHargaDeliv))));

                        int refTotal1 = document.get("totalPrice", Integer.class);
                        txtTotalPrice.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refTotal1))));
                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });

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
                rating = 1;
                Log.d("RatingData", "Rating: " + rating);
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
                rating = 2;
                Log.d("RatingData", "Rating: " + rating);
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
                rating = 3;
                Log.d("RatingData", "Rating: " + rating);
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
                rating = 4;
                Log.d("RatingData", "Rating: " + rating);
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
                rating = 5;
                Log.d("RatingData", "Rating: " + rating);
            }
        });

        // -- Programming Pemberian Rating Bintang Ends Here --

        btn_submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testimonyText = edt_testimony.getText().toString();

                // Insert subdocument: Testimony
                Map<String, Object> testimonyObj = new HashMap<>();
                testimonyObj.put("testimonyText", testimonyText);
                testimonyObj.put("rating", rating);

                Map<String, Object> testimonySubdoc = new HashMap<>();
                testimonySubdoc.put("testimony", testimonyObj);

                db.collection("Cakes").document(cakeID)
                        .set(testimonySubdoc, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "Subdocument Testimony berhasil dimasukkan: " + cakeID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CobaData", "Subdocument Testimony gagal dimasukkan");
                            }
                        });

                db.collection("Orders").document(orderID)
                        .update("orderStatus", "Done");

                db.collection("Cakes").document(cakeID)
                        .update("isPosted", "yes");


                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

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
