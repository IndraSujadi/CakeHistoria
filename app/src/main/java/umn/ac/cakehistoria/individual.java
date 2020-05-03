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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class individual extends AppCompatActivity {

    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();
    private String userID = fbUser.getUid();
    private FirebaseFirestore fbStore = FirebaseFirestore.getInstance();

    private TextView txtCakeCategory, txtLikeCount, txtOwnerName, txtTestimony, txtCakeDetails, txtCakePrice;
    private ImageView star1, star2, star3, star4, star5;
    private ImageView imgCake_individual;
    private ImageView back;

    private String orderID, cakeID,kategori;

    private LikeButton btnLike;

    private int likeCount;
    private boolean isLiked = false;

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
                startActivity(i);
                finish();
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

        imgCake_individual = findViewById(R.id.imgCake_individual);

        /*DocumentReference dbCakes = fbStore.collection("Cakes").document(cakeID);
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
                            Picasso.get().load((String) document.get("imageURL") ).into(imgCake_individual);
                        } else {
                            Picasso.get()
                                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                                    .into(imgCake_individual);
                        }

                        txtCakeCategory.setText((String) document.get("cakeCategory"));
                        likeCount = document.get("likes", Integer.class);
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
        });*/

        tampilData();

        btnLike = findViewById(R.id.btnLike);

        // Kalo udah dilike pas masuk ke activity button like nya udah liked.
        DocumentReference docLikes = fbStore.collection("User").document(userID)
                .collection("Likes").document(cakeID);

        docLikes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                isLiked = document.get("isLiked", Boolean.class);  //kalo yang ini gw nyalain error
                Log.d("CobaData", "is liked: " + isLiked);

                if(isLiked){
                    btnLike.setLiked(true);
                }
            }
        });

        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeCount += 1;
//                Toast.makeText(getApplicationContext(), "Like:" + String.valueOf(likeCount), Toast.LENGTH_LONG).show();
                fbStore.collection("Cakes").document(cakeID)
                        .update("likes", likeCount);

                // Update collection "LIKES" di User field: "isLiked"
                fbStore.collection("User").document(userID).collection("Likes").document(cakeID)
                        .update("isLiked", true);

                // Update collection "LIKES" di User field: "likes"
                fbStore.collection("User").document(userID).collection("Likes").document(cakeID)
                        .update("likes", likeCount);

                tampilData();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeCount -= 1;
//                Toast.makeText(getApplicationContext(), "Like:" + String.valueOf(likeCount), Toast.LENGTH_LONG).show();
                fbStore.collection("Cakes").document(cakeID)
                        .update("likes", likeCount);

                // Update collection "LIKES" di User
                fbStore.collection("User").document(userID).collection("Likes").document(cakeID)
                        .update("isLiked", false);

                // Update collection "LIKES" di User field: "likes"
                fbStore.collection("User").document(userID).collection("Likes").document(cakeID)
                        .update("likes", likeCount);

                tampilData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    public void tampilData () {
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
                            Picasso.get().load((String) document.get("imageURL") ).into(imgCake_individual);
                        } else {
                            Picasso.get()
                                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                                    .into(imgCake_individual);
                        }

                        txtCakeCategory.setText((String) document.get("cakeCategory"));
                        likeCount = document.get("likes", Integer.class);
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
}
