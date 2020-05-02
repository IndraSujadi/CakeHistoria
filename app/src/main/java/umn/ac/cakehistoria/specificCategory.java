package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class specificCategory extends AppCompatActivity implements adapterSpecific.OnItemClickListener {

    private String KEY = "series";
    private String kategori;

    RecyclerView recyclerSpecific;
    ImageView imgback;

    private adapterSpecific adapter;

    FirebaseFirestore DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);

        recyclerSpecific = findViewById(R.id.recyclerSpecific);

        /*imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
            }
        });*/
//==================================================BAGIAN RECYCLER VIEW=================================================================
        Bundle extras = getIntent().getExtras();
        kategori = extras.getString(KEY);

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

    private CakeAdapter.OnItemClickListener listener;

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

    }

    @NonNull
    @Override
    public specificViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specific, parent, false);
        return new specificViewHolder(view);
    }

    public class specificViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSpecific;

        public specificViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSpecific = itemView.findViewById(R.id.imgSpesific);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.OnItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(CakeAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
