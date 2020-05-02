package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class Specific_Review_Activity extends AppCompatActivity {

    // Firebase
    private FirebaseFirestore fbStore;

    private TextView txtTitle, txt_cake_category, txtLikeCount, txtOwnerName, txtTestimony;
    private ImageView img_cake, imgOwner, star1, star2, star3, star4, star5;

    private int rating;
    private String cakeID;

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific__review);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();
        cakeID = i.getStringExtra("cakeID");

        fbStore = FirebaseFirestore.getInstance();

        //impelementasi toolbar atas stars here.

//        Toolbar toolbar = findViewById(R.id.review_toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.ReviewToolbar));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //implementasi toolbar atas ends here.

        // Views:
        txtTitle = findViewById(R.id.txt_Title);
        txt_cake_category = findViewById(R.id.txt_cake_category);
        txtLikeCount = findViewById(R.id.txtLikeCount);
        txtOwnerName = findViewById(R.id.txtOwnerName);
        txtTestimony = findViewById(R.id.txtTestimony);
        img_cake = findViewById(R.id.img_cake);
        imgOwner = findViewById(R.id.imgOwner);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

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

                        if ((String) document.get("imageURL") != "") {
                            Picasso.get().load((String) document.get("imageURL") ).into(img_cake);
                        } else {
                            Picasso.get()
                                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                                    .into(img_cake);
                        }
                        txtTitle.setText((String) document.get("cakeCategory") + " Custom Cake");
                        txt_cake_category.setText((String) document.get("cakeCategory"));
                        txtLikeCount.setText(String.valueOf((Long) document.get("likes")));
                        txtOwnerName.setText((String) document.get("owner"));
                        txtTestimony.setText((String) testimony.get("testimonyText"));

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_favourite) {
            Toast.makeText(Specific_Review_Activity.this, "Favourite clicked by user", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_shoppingcart) {
            Toast.makeText(Specific_Review_Activity.this, "Shopping cart clicked by user", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
