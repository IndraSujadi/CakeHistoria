package umn.ac.cakehistoria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class fragHome extends Fragment {

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
        Query queryB = fbStore.collection("Cakes").orderBy("orderID");

        // FirebaseRecyclerOptions & FirebaseRecyclerAdapter
        FirestoreRecyclerOptions<class_cake> options = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryB, class_cake.class)
                .build();

        birthdayCakeAdapter = new CakeAdapter(options);

//       adapterB = new FirestoreRecyclerAdapter<Cake_model, cakeViewHolderB> (options) {
//            @NonNull
//            @Override
//            public cakeViewHolderB onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_list, parent, false);
//                return new cakeViewHolderB(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull cakeViewHolderB holder, int position, @NonNull Cake_model model) {
//                //holder.txtCategory.setText(model.getCakeCategory());
//                holder.txtLikes.setText(Integer.parseInt(String.valueOf(model.getLikes())));
//                holder.txt_namaUser.setText(model.getOwner());
//                holder.txtHarga.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getCakePrice()))));
//
//                Glide.with(Ctx).load(model.getImageURL()).into(holder.imgCake);
//
//                imgCake = model.getImageURL();
//                Button btnDetail;
//                btnDetail = holder.btnDetail;
//                btnDetail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(getActivity(),Specific_Category.class);
//                        i.putExtra("imgURL",imgCake);
//                        startActivity(i);
//                    }
//                });
//
//            }
//        };

        recyclerBirthday.setHasFixedSize(true);
        recyclerBirthday.setLayoutManager(linearLayoutB);
        recyclerBirthday.setAdapter(birthdayCakeAdapter);

        //---------------------------------------------- WEDDING---------------------------------------------------------------------
        Query queryW = fbStore.collection("Cakes").orderBy("owner");

        FirestoreRecyclerOptions<class_cake> optionsW = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryW,class_cake.class)
                .build();

        weddingCakeAdapter = new CakeAdapter(optionsW);

        recyclerWedding.setHasFixedSize(true);
        recyclerWedding.setLayoutManager(linearLayoutW);
        recyclerWedding.setAdapter(weddingCakeAdapter);
        //---------------------------------------------- VALENTINE---------------------------------------------------------------------
        Query queryV = fbStore.collection("Cakes").orderBy("cakePrice");

        FirestoreRecyclerOptions<class_cake> optionsV = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryV,class_cake.class)
                .build();

        valentineCakeAdapter = new CakeAdapter(optionsV);

        recyclerValentine.setHasFixedSize(true);
        recyclerValentine.setLayoutManager(linearLayoutV);
        recyclerValentine.setAdapter(valentineCakeAdapter);
        //---------------------------------------------- OTHERS---------------------------------------------------------------------
        Query queryO = fbStore.collection("Cakes").orderBy("likes");

        FirestoreRecyclerOptions<class_cake> optionsO = new FirestoreRecyclerOptions.Builder<class_cake>()
                .setQuery(queryO,class_cake.class)
                .build();

        othersCakeAdapter = new CakeAdapter(optionsO);

        recyclerOthers.setHasFixedSize(true);
        recyclerOthers.setLayoutManager(linearLayoutO);
        recyclerOthers.setAdapter(othersCakeAdapter);
      //====================================================================END OF RECYCLER VIEW======================================================


        return view;
    }

//    private class cakeViewHolderB extends RecyclerView.ViewHolder {
//
//        private TextView txtCategory, txtLikes, txt_namaUser, txtHarga;
//        private ImageView imgCake;
//        Button btnDetail, btnOrder_similar;
//        public cakeViewHolderB(@NonNull View itemView) {
//            super(itemView);
//            txtCategory = itemView.findViewById(R.id.txtCategory);
//            txtLikes = itemView.findViewById(R.id.txtLikes);
//            txt_namaUser = itemView.findViewById(R.id.txt_namaUser);
//            txtHarga = itemView.findViewById(R.id.txtHarga);
//            imgCake = itemView.findViewById(R.id.imgCake);
//
//            btnDetail = itemView.findViewById(R.id.btnDetail);
//            btnOrder_similar = itemView.findViewById(R.id.btnOrder_similar);
//
//        }
//    }

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
//        adapterB.stopListening();
        birthdayCakeAdapter.stopListening();
        weddingCakeAdapter.stopListening();
        valentineCakeAdapter.startListening();
        othersCakeAdapter.stopListening();
    }
}

class CakeAdapter extends FirestoreRecyclerAdapter<class_cake, CakeAdapter.BirthdayCakeViewHolder>{

    public CakeAdapter(@NonNull FirestoreRecyclerOptions<class_cake> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CakeAdapter.BirthdayCakeViewHolder holder, int position, @NonNull class_cake model) {
        holder.txtCategory.setText(model.getCakeCategory());
        holder.txt_namaUser.setText(model.getOwner());
        holder.txtLikes.setText(String.valueOf(model.getLikes()));
        holder.txtHarga.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getCakePrice()))));
        Picasso.get().load(model.getImageURL()).into(holder.imgCake);

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

        public BirthdayCakeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategory = itemView.findViewById(R.id.txtCategory);

            txt_namaUser = itemView.findViewById(R.id.txt_namaUser);
            txtLikes = itemView.findViewById(R.id.txtLikes);
            txtHarga = itemView.findViewById(R.id.txtHarga);

            imgCake = itemView.findViewById(R.id.imgCake);
        }
    }
}




