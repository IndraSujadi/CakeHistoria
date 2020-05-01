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
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onprogress extends Fragment implements OrderAdapter2.OnItemClickListener{

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

//    private FirestoreRecyclerAdapter adapter;

    private OrderAdapter2 adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_onprogress, container, false);

        fbStore = FirebaseFirestore.getInstance();

        mRecyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        // Query the data
        Query query = fbStore.collection("Orders")
                .whereIn("orderStatus", Arrays.asList("Order is processed", "On Making", "On Delivery"))
                .whereEqualTo("userID", userID);

        // FirebaseRecyclerOptions & FirebaseRecyclerAdapter
        FirestoreRecyclerOptions<class_order> options = new FirestoreRecyclerOptions.Builder<class_order>()
                .setQuery(query, class_order.class)
                .build();

        adapter = new OrderAdapter2(options);
        adapter.setOnItemClickListener(this::onItemClick);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return view;
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


    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
        String orderID = documentSnapshot.getId();
        String cakeID = (String) documentSnapshot.get("cakeID");
//        Toast.makeText(getActivity(), "cake ID: " + cakeID, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), OnProgress_Detail.class);
        i.putExtra("orderID", orderID);
        i.putExtra("cakeID", cakeID);
        startActivity(i);
    }
}

class OrderAdapter2 extends FirestoreRecyclerAdapter<class_order, OrderAdapter2.OrderViewHolder>{

    private String orderStatus;
    private String cakeID;
    private String orderID;

    private OnItemClickListener listener;

    public OrderAdapter2(@NonNull FirestoreRecyclerOptions<class_order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter2.OrderViewHolder holder, int position, @NonNull class_order model) {
        holder.txt_OrderNo_Value.setText(model.getOrderID());
        holder.txt_OrderItem_Value.setText(model.getOrderName());
        holder.txt_OrderDate_Value.setText(model.getOrderDateTime().toString());
//                holder.txt_Price_Value.setText("Rp " + String.valueOf(model.getTotalPrice()));
        holder.txt_Price_Value.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(model.getTotalPrice()))));
        holder.txt_DoneEstimation_Value.setText(model.getRequestDate());
        holder.txtOrderStatus.setText(model.getOrderStatus());

//        cakeID = model.getCakeID();
//        orderID = model.getOrderID();
//        Button btnReceive;
//        btnReceive = holder.btnReceive;
//        btnReceive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), Review_Activity.class);
//                i.putExtra("cakeID", cakeID);
//                i.putExtra("orderID", orderID);
//                startActivity(i);
//            }
//        });

    }

    @NonNull
    @Override
    public OrderAdapter2.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_OrderNo_Value, txt_OrderItem_Value, txt_OrderDate_Value, txt_Price_Value, txt_DoneEstimation_Value
                , txtOrderStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_OrderNo_Value = itemView.findViewById(R.id.txt_OrderNo_Value);
            txt_OrderItem_Value = itemView.findViewById(R.id.txt_OrderItem_Value);
            txt_OrderDate_Value = itemView.findViewById(R.id.txt_OrderDate_Value);
            txt_Price_Value = itemView.findViewById(R.id.txt_Price_Value);
            txt_DoneEstimation_Value = itemView.findViewById(R.id.txt_DoneEstimation_Value);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);

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
