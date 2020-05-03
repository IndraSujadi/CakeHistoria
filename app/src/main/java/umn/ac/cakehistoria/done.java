package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class done extends Fragment implements OrderAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;

    // Firebase
    private FirebaseFirestore fbStore;
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();
    private String userID = fbUser.getUid();

    private String cakeID;
    private String orderID;
    private int rating;

//    private FirestoreRecyclerAdapter adapter;
    private OrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_done, container, false);

        fbStore = FirebaseFirestore.getInstance();

        mRecyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        // Query the data
        Query query = fbStore.collection("Orders")
                .whereEqualTo("orderStatus", "Done")
                .whereEqualTo("userID", userID);

        // FirebaseRecyclerOptions & FirebaseRecyclerAdapter
        FirestoreRecyclerOptions<class_order2> options = new FirestoreRecyclerOptions.Builder<class_order2>()
                .setQuery(query, class_order2.class)
                .build();

        adapter = new OrderAdapter(options);

        adapter.setOnItemClickListener(this::onItemClick);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        return view;
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
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
        String cakeID = (String) documentSnapshot.get("cakeID");
        Intent i = new Intent(getActivity(), Specific_Review_Activity.class);
        i.putExtra("cakeID", cakeID);
        startActivity(i);
    }
}


// OrderViewHolder External Class
class OrderAdapter extends FirestoreRecyclerAdapter<class_order2, OrderAdapter.OrderViewHolder>{

    // Firebase
    private FirebaseFirestore fbStore;
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();
    private String userID = fbUser.getUid();

    private String cakeID;

    private OnItemClickListener listener;

    public OrderAdapter(@NonNull FirestoreRecyclerOptions<class_order2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position, @NonNull class_order2 model) {
        holder.txtOrderID.setText(model.getCakeID());
        holder.txtOrderName.setText(model.getOrderName());
        holder.txtOrderDate.setText(model.getOrderDateTime().toString());
        holder.txtTotalPrice.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getTotalPrice()))));


        cakeID = model.getCakeID();
        fbStore = FirebaseFirestore.getInstance();
        DocumentReference dbCakes = fbStore.collection("Cakes").document(model.getCakeID());
        dbCakes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("imageURL") != "") {
                            Picasso.get().load((String) document.get("imageURL")).into(holder.imgDone);
                        } else {
                            Picasso.get()
                                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                                    .into(holder.imgDone);
                        }
                        Map subdoc = document.getData();
                        Map testimony = (Map) subdoc.get("testimony");

                        holder.txtTestimony.setText("'" + (String) testimony.get("testimonyText") + "'");
                        Log.d("CobaData", "Document is exist");


                        int rating = ((Long) testimony.get("rating")).intValue();
                        switch (rating) {
                            case 1:
                                holder.star1.setImageResource(R.drawable.rate_clicked);
                                holder.star2.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star3.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 2:
                                holder.star1.setImageResource(R.drawable.rate_clicked);
                                holder.star2.setImageResource(R.drawable.rate_clicked);
                                holder.star3.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 3:
                                holder.star1.setImageResource(R.drawable.rate_clicked);
                                holder.star2.setImageResource(R.drawable.rate_clicked);
                                holder.star3.setImageResource(R.drawable.rate_clicked);
                                holder.star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 4:
                                holder.star1.setImageResource(R.drawable.rate_clicked);
                                holder.star2.setImageResource(R.drawable.rate_clicked);
                                holder.star3.setImageResource(R.drawable.rate_clicked);
                                holder.star4.setImageResource(R.drawable.rate_clicked);
                                holder.star5.setImageResource(R.drawable.rate_not_clicked_yet);
                                break;
                            case 5:
                                holder.star1.setImageResource(R.drawable.rate_clicked);
                                holder.star2.setImageResource(R.drawable.rate_clicked);
                                holder.star3.setImageResource(R.drawable.rate_clicked);
                                holder.star4.setImageResource(R.drawable.rate_clicked);
                                holder.star5.setImageResource(R.drawable.rate_clicked);
                                break;
                            default:
                                holder.star1.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star2.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star3.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star4.setImageResource(R.drawable.rate_not_clicked_yet);
                                holder.star5.setImageResource(R.drawable.rate_not_clicked_yet);
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

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item1, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView txtOrderID, txtOrderName, txtOrderDate, txtTotalPrice, txtTestimony;
        private ImageView star1, star2, star3, star4, star5, imgDone;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderID = itemView.findViewById(R.id.txtOrderID);
            txtOrderName = itemView.findViewById(R.id.txtOrderName);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtTestimony = itemView.findViewById(R.id.txtTestimony);
            imgDone = itemView.findViewById(R.id.imgDone);

            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}