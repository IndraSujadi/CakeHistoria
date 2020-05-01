package umn.ac.cakehistoria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class fragHome extends Fragment implements CakeAdapter.OnItemClickListener{

    private RecyclerView recyclerBirthday, recyclerWedding, recyclerValentine, recyclerOthers;
    FirebaseFirestore fbStore;
    //private FirestoreRecyclerAdapter adapterB, adapterW, adapterV, adapterO;
    Context Ctx;

    private String imgCake;


    ImageButton btnHistory, btnFav;

    private CakeAdapter birthdayCakeAdapter;
    private CakeAdapter weddingCakeAdapter;
    private CakeAdapter valentineCakeAdapter;
    private CakeAdapter othersCakeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_home, container, false);
        //-------------------------------------------  Intent yang button tolong tro sini aja yaa -----------------------------------------
        btnFav = view.findViewById(R.id.btnFav);
        btnHistory = view.findViewById(R.id.btnHistory);

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Masuk ke activity history di sini...
                Intent i = new Intent(getActivity(), Transactions.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        // -------------------------------------------- END of Button Intent =-------------------------------------------------------

        //-------------------------------------------- RECYCLER VIEW ----------------------------------------------------------------
        fbStore = FirebaseFirestore.getInstance();
        recyclerBirthday = view.findViewById(R.id.recyclerBirthday);
        recyclerWedding = view.findViewById(R.id.recyclerWedding);
        recyclerValentine = view.findViewById(R.id.recyclerValentine);
        recyclerOthers = view.findViewById(R.id.recyclerOthers);

        LinearLayoutManager linearLayoutB = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutW = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutV = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutO = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        // ---------------------------------------------BIRTHDAY --------------------------------------------------------------------

        //recyclerBirthday.setLayoutManager(linearLayoutB);

        // Query the Data
        Query queryB = fbStore.collection("Cakes")
                .whereEqualTo("cakeCategory","Birthday")
                .whereEqualTo("isPosted","yes");

        // FirebaseRecyclerOptions & FirebaseRecyclerAdapter
        FirestoreRecyclerOptions<class_cake> options = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryB, class_cake.class)
                .build();

        birthdayCakeAdapter = new CakeAdapter(options);
        birthdayCakeAdapter.setOnItemClickListener(this::OnItemClick);

        recyclerBirthday.setHasFixedSize(true);
        recyclerBirthday.setLayoutManager(linearLayoutB);
        recyclerBirthday.setAdapter(birthdayCakeAdapter);

        //---------------------------------------------- WEDDING---------------------------------------------------------------------
        Query queryW = fbStore.collection("Cakes")
                .whereEqualTo("cakeCategory","Wedding")
                .whereEqualTo("isPosted","yes");

        FirestoreRecyclerOptions<class_cake> optionsW = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryW,class_cake.class)
                .build();

        weddingCakeAdapter = new CakeAdapter(optionsW);
        weddingCakeAdapter.setOnItemClickListener(this::OnItemClick);

        recyclerWedding.setHasFixedSize(true);
        recyclerWedding.setLayoutManager(linearLayoutW);
        recyclerWedding.setAdapter(weddingCakeAdapter);
        //---------------------------------------------- VALENTINE---------------------------------------------------------------------
        Query queryV = fbStore.collection("Cakes")
                .whereEqualTo("cakeCategory","Valentine")
                .whereEqualTo("isPosted","yes");

        FirestoreRecyclerOptions<class_cake> optionsV = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryV,class_cake.class)
                .build();

        valentineCakeAdapter = new CakeAdapter(optionsV);
        valentineCakeAdapter.setOnItemClickListener(this::OnItemClick);

        recyclerValentine.setHasFixedSize(true);
        recyclerValentine.setLayoutManager(linearLayoutV);
        recyclerValentine.setAdapter(valentineCakeAdapter);
        //---------------------------------------------- OTHERS---------------------------------------------------------------------
        Query queryO = fbStore.collection("Cakes")
                .whereEqualTo("cakeCategory","Others")
                .whereEqualTo("isPosted","yes");

        FirestoreRecyclerOptions<class_cake> optionsO = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryO,class_cake.class)
                .build();

        othersCakeAdapter = new CakeAdapter(optionsO);
        othersCakeAdapter.setOnItemClickListener(this::OnItemClick);

        recyclerOthers.setHasFixedSize(true);
        recyclerOthers.setLayoutManager(linearLayoutO);
        recyclerOthers.setAdapter(othersCakeAdapter);
      //====================================================================END OF RECYCLER VIEW======================================================

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        adapterB.startListening();
        birthdayCakeAdapter.startListening();
        weddingCakeAdapter.startListening();
        valentineCakeAdapter.startListening();
        othersCakeAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        birthdayCakeAdapter.stopListening();
        weddingCakeAdapter.stopListening();
        valentineCakeAdapter.startListening();
        othersCakeAdapter.stopListening();
    }

    @Override
    public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
        String cakeID = documentSnapshot.getId();
        String orderID = (String) documentSnapshot.get("orderID");

        Intent i = new Intent(getActivity(), individual.class);
        i.putExtra("cakeID", cakeID);
        i.putExtra("orderID", orderID);
        startActivity(i);
        getActivity().finish();
    }
}

class CakeAdapter extends FirestoreRecyclerAdapter<class_cake, CakeAdapter.BirthdayCakeViewHolder>{

    private OnItemClickListener listener;

    public CakeAdapter(@NonNull FirestoreRecyclerOptions<class_cake> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CakeAdapter.BirthdayCakeViewHolder holder, int position, @NonNull class_cake model) {
        holder.txtCategory.setText(model.getCakeCategory());
        holder.txt_namaUser.setText(model.getOwner());
        holder.txtLikes.setText(String.valueOf(model.getLikes()));
        holder.txtHarga.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getCakePrice()))));
        if (model.getImageURL() != "") {
            Picasso.get().load(model.getImageURL()).into(holder.imgCake);
        } else {
            Picasso.get()
                    .load("https://firebasestorage.googleapis.com/v0/b/historiacake.appspot.com/o/no.png?alt=media&token=edcf1cca-322a-4dd6-be66-34522f5e71e0")
                    .into(holder.imgCake);
        }
    }

    @NonNull
    @Override
    public CakeAdapter.BirthdayCakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_list, parent, false);
        return new BirthdayCakeViewHolder(view);
    }

    public class BirthdayCakeViewHolder extends RecyclerView.ViewHolder{
        TextView txtCategory, txt_namaUser, txtLikes, txtHarga;
        ImageView imgCake;
        CardView cakeCard;

        public BirthdayCakeViewHolder(@NonNull View itemView) {
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

    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}




