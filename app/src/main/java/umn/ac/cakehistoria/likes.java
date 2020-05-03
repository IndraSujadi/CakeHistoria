package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

public class likes extends AppCompatActivity implements LikeAdapter.OnItemClickListener{

    ImageView imgBack_Likes;

    RecyclerView recyclerLikes;

    FirebaseFirestore DB;
    FirebaseAuth fbAuth;
    FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();
    private String userID = fbUser.getUid();

    LikeAdapter likeAdapter;

    List<String> likeListIDs = new ArrayList<>();

    private String cakeID, orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.d("CobaData", "User ID: " + userID);

        DB = FirebaseFirestore.getInstance();

        imgBack_Likes = findViewById(R.id.imgBack_likes);
        imgBack_Likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
                finish();
            }
        });

        recyclerLikes = findViewById(R.id.recyclerLikes);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerLikes.setLayoutManager(mGridLayoutManager);

        DB.collection("User").document(userID).collection("Likes")
                .whereEqualTo("like", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                likeListIDs.add(document.getId());
                            }

                            Log.d("CobaData", "List like berhasil diambil: " + likeListIDs.toString());
                            Log.d("CobaData", "ArrayList size:" + String.valueOf(likeListIDs.size()));
                            for(int i=0; i < likeListIDs.size(); i++){
                                Log.d("CobaData", "Cake ID: " + likeListIDs.get(i) + "\n");
                            }
                        } else {
                            Log.d("CobaData", "List likes gagal diambil" + task.getException());
                        }
                    }
                });


        Query query = DB.collection("User").document(userID).collection("Likes")
                .whereEqualTo("isLiked", true);

        FirestoreRecyclerOptions<class_cake2> option = new FirestoreRecyclerOptions.Builder<class_cake2>()
                .setQuery(query, class_cake2.class)
                .build();

        likeAdapter = new LikeAdapter(option);
        likeAdapter.setOnItemClickListener(this::OnItemClick);

        recyclerLikes.setHasFixedSize(true);
        recyclerLikes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerLikes.setAdapter(likeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        likeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        likeAdapter.stopListening();
    }

    @Override
    public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
        String cakeID = documentSnapshot.getId();
        String orderID = (String) documentSnapshot.get("orderID");
        String cakeCategory = (String) documentSnapshot.get("cakeCategory");

//        Toast.makeText(getApplicationContext(), "Bisa kok", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, individual.class);
        i.putExtra("cakeID", cakeID);
        i.putExtra("orderID", orderID);
        i.putExtra("kategori", cakeCategory);
        startActivity(i);
    }
}

class LikeAdapter extends FirestoreRecyclerAdapter<class_cake2, LikeAdapter.LikeViewHolder>{

    private OnItemClickListener listener;

    public LikeAdapter(@NonNull FirestoreRecyclerOptions<class_cake2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LikeAdapter.LikeViewHolder holder, int position, @NonNull class_cake2 model) {
        holder.txtCategory.setText(model.getCakeCategory());
//        holder.txtCategory.setText("coba");
        holder.txt_namaUser.setText(model.getOwner());
        holder.txtLikes.setText(String.valueOf(model.getLikes()));
        holder.txtHarga.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getCakePrice()))));


        String imageURL = model.getImageURL();
        if (imageURL != null && !imageURL.trim().isEmpty()) {
            Picasso.get().load(model.getImageURL()).into(holder.imgCake);
        } else {
            Picasso.get()
                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                    .into(holder.imgCake);
        }
    }

    @NonNull
    @Override
    public LikeAdapter.LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_list, parent, false);
        return new LikeViewHolder(view);
    }

    public class LikeViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategory, txt_namaUser, txtLikes, txtHarga;
        ImageView imgCake;
        CardView cakeCard;

        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategory = itemView.findViewById(R.id.txtCategory);

            txt_namaUser = itemView.findViewById(R.id.txt_namaUser);
            txtLikes = itemView.findViewById(R.id.txtLikes);
            txtHarga = itemView.findViewById(R.id.txtHarga);

            imgCake = itemView.findViewById(R.id.imgCake);

            cakeCard = itemView.findViewById(R.id.cakeCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.OnItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
