package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onprogress extends Fragment {

    private RecyclerView mRecyclerView;
    List<TransactionData> mOrderList = new ArrayList();
    TransactionData mTransactionData;

    private String orderStatus;

    // Firebase
    private FirebaseFirestore fbStore;
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();
    private String userID = fbUser.getUid();

    private String cakeID;
    private String orderID;

    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_onprogress, container, false);

        fbStore = FirebaseFirestore.getInstance();

        mRecyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

//        mTransactionList = new ArrayList<>();
//        mTransactionData = new TransactionData("TR-003","Birthday Custom Cake", "15-03-2020", "990000", "7");
//        mTransactionList.add(mTransactionData);
//
//        mTransactionData = new TransactionData("TR-004","Sweet 17th Cake", "17-01-2019", "190000", "5");
//        mTransactionList.add(mTransactionData);
//
//        mTransactionData = new TransactionData("TR-005","Anniversary Cake", "17-01-2019", "250000", "17");
//        mTransactionList.add(mTransactionData);
//
//        DataAdapter dataAdapter = new DataAdapter(getActivity(), mTransactionList);
//        mRecyclerView.setAdapter(dataAdapter);

        // Query the data
        Query query = fbStore.collection("Orders")
                .whereIn("orderStatus", Arrays.asList("Order is processed", "On Making", "On Delivery"))
                .whereEqualTo("userID", userID);

        // FirebaseRecyclerOptions & FirebaseRecyclerAdapter
        FirestoreRecyclerOptions<class_order> options = new FirestoreRecyclerOptions.Builder<class_order>()
                .setQuery(query, class_order.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<class_order, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull class_order model) {
                holder.txt_OrderNo_Value.setText(model.getOrderID());
                holder.txt_OrderItem_Value.setText(model.getOrderName());
                holder.txt_OrderDate_Value.setText(model.getOrderDateTime().toString());
//                holder.txt_Price_Value.setText("Rp " + String.valueOf(model.getTotalPrice()));
                holder.txt_Price_Value.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getTotalPrice()))));
                holder.txt_DoneEstimation_Value.setText(model.getRequestDate());
                holder.txtOrderStatus.setText(model.getOrderStatus());

                holder.receiveContainer.setVisibility(View.GONE);
                orderStatus = model.getOrderStatus();

                if(orderStatus.equalsIgnoreCase("On Delivery")){
                    holder.receiveContainer.setVisibility(View.VISIBLE);
                }

                cakeID = model.getCakeID();
                orderID = model.getOrderID();
                Button btnReceive;
                btnReceive = holder.btnReceive;
                btnReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), Review_Activity.class);
                        i.putExtra("cakeID", cakeID);
                        i.putExtra("orderID", orderID);
                        startActivity(i);
                    }
                });

            }
        };

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return view;
    }


    private class OrderViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_OrderNo_Value, txt_OrderItem_Value, txt_OrderDate_Value, txt_Price_Value, txt_DoneEstimation_Value
                , txtOrderStatus;

        LinearLayout receiveContainer;
        Button btnReceive;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_OrderNo_Value = itemView.findViewById(R.id.txt_OrderNo_Value);
            txt_OrderItem_Value = itemView.findViewById(R.id.txt_OrderItem_Value);
            txt_OrderDate_Value = itemView.findViewById(R.id.txt_OrderDate_Value);
            txt_Price_Value = itemView.findViewById(R.id.txt_Price_Value);
            txt_DoneEstimation_Value = itemView.findViewById(R.id.txt_DoneEstimation_Value);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);

            receiveContainer = itemView.findViewById(R.id.receiveContainer);
            btnReceive = itemView.findViewById(R.id.btnReceive);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
