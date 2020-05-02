package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class individual extends AppCompatActivity {

    private FirebaseFirestore fbStore = FirebaseFirestore.getInstance();

    private TextView txtCakeCategory, txtLikeCount, txtOwnerName, txtTestimony, txtCakeDetails, txtCakePrice;
    private ImageView star1, star2, star3, star4, star5;
    private ImageView imgCake;
    private ImageView back;

    private String orderID, cakeID,kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        Intent i = getIntent();
        cakeID = i.getStringExtra("cakeID");
        orderID = i.getStringExtra("orderID");
        kategori = i.getStringExtra("kategori");

        back = findViewById(R.id.imgBackNEW);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                /*Intent i = new Intent(getApplicationContext(), specificCategory.class);
                i.putExtra("series",kategori);*/
                startActivity(i);
            }
        });

        txtCakeCategory = findViewById(R.id.txtCakeCategory);
        txtLikeCount = findViewById(R.id.txtLikeCount);
        txtOwnerName = findViewById(R.id.txtOwnerName);
        txtTestimony = findViewById(R.id.txtTestimony);
        txtCakeDetails = findViewById(R.id.txtCakeDetails);
        txtCakePrice = findViewById(R.id.txtCakePrice);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        imgCake = findViewById(R.id.imgCake);

        DocumentReference dbCakes = fbStore.collection("Cakes").document(cakeID);
        dbCakes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map subdoc = document.getData();
                        Map cakeDetails = (Map) subdoc.get("CakeDetails");
                        Map testimony = (Map) subdoc.get("testimony");

                        txtCakeCategory.setText((String) document.get("cakeCategory"));
                        txtLikeCount.setText(String.valueOf((Long) document.get("likes")));
                        txtOwnerName.setText((String) document.get("owner"));
                        txtTestimony.setText((String) testimony.get("testimonyText"));

                        String refCakeDetails = (String) cakeDetails.get("cakeType") + ", " + (String) cakeDetails.get("cakeColor")
                                + ", " + (String) cakeDetails.get("cakeDecor") + ", " + (String) cakeDetails.get("cakeTheme")
                                + ", " + (String) cakeDetails.get("cakeFlavor") + ", " + (String) cakeDetails.get("cakeTier")
                                + ", " + (String) cakeDetails.get("cakeShape") + ", " + (String) cakeDetails.get("cakeSize");
                        txtCakeDetails.setText(refCakeDetails);

                        int refHargaProduk = document.get("cakePrice", Integer.class);
                        txtCakePrice.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refHargaProduk))));

                        int rating = ((Long) testimony.get("rating")).intValue();
                        switch (rating) {
                            case 1:
                                star1.setImageResource(R.drawable.rate_clicked);
                                star2.setImageResource(R.drawable.rate_not_clicked_yet);
                                star3.setImageResource(R.drawable.rate_not_clicked_yet);
                                star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 2:
                                star1.setImageResource(R.drawable.rate_clicked);
                                star2.setImageResource(R.drawable.rate_clicked);
                                star3.setImageResource(R.drawable.rate_not_clicked_yet);
                                star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 3:
                                star1.setImageResource(R.drawable.rate_clicked);
                                star2.setImageResource(R.drawable.rate_clicked);
                                star3.setImageResource(R.drawable.rate_clicked);
                                star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 4:
                                star1.setImageResource(R.drawable.rate_clicked);
                                star2.setImageResource(R.drawable.rate_clicked);
                                star3.setImageResource(R.drawable.rate_clicked);
                                star4.setImageResource(R.drawable.rate_clicked);
                                star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 5:
                                star1.setImageResource(R.drawable.rate_clicked);
                                star2.setImageResource(R.drawable.rate_clicked);
                                star3.setImageResource(R.drawable.rate_clicked);
                                star4.setImageResource(R.drawable.rate_clicked);
                                star5.setImageResource(R.drawable.rate_clicked);
                                break;
                            default:
                                star1.setImageResource(R.drawable.rate_not_clicked_yet);
                                star2.setImageResource(R.drawable.rate_not_clicked_yet);
                                star3.setImageResource(R.drawable.rate_not_clicked_yet);
                                star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                star5.setImageResource(R.drawable.rate_not_clicked_yet);
                        }
                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
