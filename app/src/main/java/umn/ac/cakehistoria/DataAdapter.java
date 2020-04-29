package umn.ac.cakehistoria;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    private List<TransactionData> mTransactionList;

    DataAdapter(Context mContext, List<TransactionData> mTransactionList) {

        this.mTransactionList = mTransactionList;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new TransactionViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final TransactionViewHolder holder, int position) {

//        holder.mOrderNo.setText(mTransactionList.get(position).getOrderno());
//        holder.mOrderItem.setText(mTransactionList.get(position).getOrderitem());
//        holder.mOrderDate.setText(mTransactionList.get(position).getOrderdate());
//        holder.mPrice.setText(mTransactionList.get(position).getPrice());
//        holder.mDoneEstimation.setText(mTransactionList.get(position).getDoneestimation());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("ABC","ABCD");

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }
}

class TransactionViewHolder extends RecyclerView.ViewHolder {

    TextView mOrderNo, mOrderItem, mOrderDate, mPrice, mDoneEstimation;
    CardView mCardView;

    TransactionViewHolder(View itemView) {
        super(itemView);

        mOrderNo = itemView.findViewById(R.id.txt_OrderNo_Value);
        mOrderItem = itemView.findViewById(R.id.txt_OrderItem_Value);
        mOrderDate = itemView.findViewById(R.id.txt_OrderDate_Value);
        mPrice = itemView.findViewById(R.id.txt_Price_Value);
        mDoneEstimation = itemView.findViewById(R.id.txt_DoneEstimation_Value);
        mCardView = itemView.findViewById(R.id.cardview);

    }
}
