package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class specificCategory extends AppCompatActivity implements adapterSpecific.OnItemClickListener {

    private String KEY = "series";
    private String kategori;

    RecyclerView recyclerSpecific;
    ImageView imgback;

    private adapterSpecific adapter;

    FirebaseFirestore DB;

    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerSpecific = findViewById(R.id.recyclerSpecific);
        imgback = findViewById(R.id.imgBack_specific);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//==================================================BAGIAN RECYCLER VIEW=================================================================
        Bundle extras = getIntent().getExtras();
        kategori = extras.getString(KEY);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(kategori);

        DB = FirebaseFirestore.getInstance();

        // query the data
        Query query = DB.collection("Cakes")
                .whereEqualTo("cakeCategory", kategori)
                .whereEqualTo("isPosted","yes");

        FirestoreRecyclerOptions<class_cake> options = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(query, class_cake.class)
                .build();

        adapter = new adapterSpecific(options);
        adapter.setOnItemClickListener(this::OnItemClick);

        recyclerSpecific.setHasFixedSize(true);
        recyclerSpecific.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerSpecific.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    @Override
    public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
        String cakeID = documentSnapshot.getId();
        String orderID = (String) documentSnapshot.get("orderID");

        Intent i = new Intent(getApplicationContext(), individual.class);
        i.putExtra("cakeID", cakeID);
        i.putExtra("orderID", orderID);
        startActivity(i);
        finish();
    }
}

class adapterSpecific extends FirestoreRecyclerAdapter<class_cake, adapterSpecific.specificViewHolder> {

    private OnItemClickListener listener;
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();
    private String userID = fbUser.getUid();
    private FirebaseFirestore fbStore = FirebaseFirestore.getInstance();


    private boolean isLiked = false;

    public adapterSpecific(@NonNull FirestoreRecyclerOptions<class_cake> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull specificViewHolder holder, int position, @NonNull class_cake model) {
        if (model.getImageURL() != "") {
            Picasso.get().load(model.getImageURL()).into(holder.imgSpecific);
        } else {
            Picasso.get()
                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                    .into(holder.imgSpecific);
        }

        holder.categoryS.setText(model.getCakeCategory());
        holder.HargaS.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getCakePrice()))));

//        // Kalo udah dilike pas masuk ke activity button like nya udah liked.
//        DocumentReference docLikes = fbStore.collection("User").document(userID)
//                .collection("Likes").document(model.getCakeID());
//
//        docLikes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot document = task.getResult();
//                isLiked = document.get("isLiked", Boolean.class);  //kalo yang ini gw nyalain error
//                Log.d("CobaData", "is liked: " + isLiked);
////                if(isLiked){
////                    holder.btnLike.setLiked(true);
////                }
//            }
//        });
//
        // Dapetin likeCount
//        DocumentReference dbCakes = fbStore.collection("Cakes").document(model.getCakeID());
//        dbCakes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        likeCount = document.get("likes", Integer.class);
//                        Log.d("CobaData", "Likes: " + likeCount);
//                    } else {
//                        Log.d("CobaData", "No such document");
//                    }
//                } else {
//                    Log.d("CobaData", "get failed with ", task.getException());
//                }
//            }
//        });
//
//        holder.btnLike.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//                Toast.makeText(holder.mContext, "Added to favorites: " + model.getCakeID(), Toast.LENGTH_LONG).show();
//                likeCount += 1;
//                fbStore.collection("Cakes").document(model.getCakeID())
//                        .update("likes", likeCount);
//
//                // Update collection "LIKES" di User field: "isLiked"
//                fbStore.collection("User").document(userID).collection("Likes").document(model.getCakeID())
//                        .update("isLiked", true);
//
//                // Update collection "LIKES" di User field: "likes"
//                fbStore.collection("User").document(userID).collection("Likes").document(model.getCakeID())
//                        .update("likes", likeCount);
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//                Toast.makeText(holder.mContext, "Removed to favorites: " + model.getCakeID(), Toast.LENGTH_LONG).show();
//                likeCount -= 1;
//                fbStore.collection("Cakes").document(model.getCakeID())
//                        .update("likes", likeCount);
//
//                // Update collection "LIKES" di User
//                fbStore.collection("User").document(userID).collection("Likes").document(model.getCakeID())
//                        .update("isLiked", false);
//
//                // Update collection "LIKES" di User field: "likes"
//                fbStore.collection("User").document(userID).collection("Likes").document(model.getCakeID())
//                        .update("likes", likeCount);
//            }
//        });

        //holder.setCakeID(model.getCakeID());
    }

    @NonNull
    @Override
    public specificViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_likes, parent, false);
        return new specificViewHolder(view);
    }

    public class specificViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSpecific;
        Button categoryS, HargaS;
        //LikeButton btnLike;

        Context mContext;
        View mView;

        //private String refCakeID;
        //private int likeCount = 0;

        public specificViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mContext = itemView.getContext();
            imgSpecific = itemView.findViewById(R.id.imgLikes);
            categoryS = itemView.findViewById(R.id.kategoriS);
            HargaS = itemView.findViewById(R.id.hargaS);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.OnItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });


//            btnLike = itemView.findViewById(R.id.btnLike);
//
//            btnLike.setOnLikeListener(new OnLikeListener() {
//                @Override
//                public void liked(LikeButton likeButton) {
//                    Toast.makeText(mContext, "Added to favorites: " + refCakeID, Toast.LENGTH_LONG).show();
//                    likeCount += 1;
//                    fbStore.collection("Cakes").document(refCakeID)
//                            .update("likes", likeCount);
//
//                    // Update collection "LIKES" di User field: "isLiked"
//                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
//                            .update("isLiked", true);
//
//                    // Update collection "LIKES" di User field: "likes"
//                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
//                            .update("likes", likeCount);
//                }
//
//                @Override
//                public void unLiked(LikeButton likeButton) {
//                    Toast.makeText(mContext, "Removed to favorites: " + refCakeID, Toast.LENGTH_LONG).show();
//                    likeCount -= 1;
//                    fbStore.collection("Cakes").document(refCakeID)
//                            .update("likes", likeCount);
//
//                    // Update collection "LIKES" di User
//                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
//                            .update("isLiked", false);
//
//                    // Update collection "LIKES" di User field: "likes"
//                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
//                            .update("likes", likeCount);
//                }
//            });

        }

        /*public void setCakeID(String cakeID){
            this.refCakeID = cakeID;

            LikeButton likeButton = mView.findViewById(R.id.btnLike);

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    Toast.makeText(mContext, "Added to favorites: " + cakeID, Toast.LENGTH_LONG).show();
                    likeCount += 1;
                    fbStore.collection("Cakes").document(refCakeID)
                            .update("likes", likeCount);

                    // Update collection "LIKES" di User field: "isLiked"
                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
                            .update("isLiked", true);

                    // Update collection "LIKES" di User field: "likes"
                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
                            .update("likes", likeCount);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Toast.makeText(mContext, "Removed to favorites: " + cakeID, Toast.LENGTH_LONG).show();
                    likeCount -= 1;
                    fbStore.collection("Cakes").document(refCakeID)
                            .update("likes", likeCount);

                    // Update collection "LIKES" di User
                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
                            .update("isLiked", false);

                    // Update collection "LIKES" di User field: "likes"
                    fbStore.collection("User").document(userID).collection("Likes").document(refCakeID)
                            .update("likes", likeCount);

                }
            });
        }*/
    }

    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
