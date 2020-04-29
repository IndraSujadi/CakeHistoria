package umn.ac.cakehistoria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class onprogress extends Fragment {

    RecyclerView mRecyclerView;
    List<TransactionData> mTransactionList;
    TransactionData mTransactionData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_onprogress, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mTransactionList = new ArrayList<>();
        mTransactionData = new TransactionData("TR-003","Birthday Custom Cake", "15-03-2020", "990000", "7");
        mTransactionList.add(mTransactionData);

        mTransactionData = new TransactionData("TR-004","Sweet 17th Cake", "17-01-2019", "190000", "5");
        mTransactionList.add(mTransactionData);

        mTransactionData = new TransactionData("TR-005","Anniversary Cake", "17-01-2019", "250000", "17");
        mTransactionList.add(mTransactionData);

        DataAdapter dataAdapter = new DataAdapter(getActivity(), mTransactionList);
        mRecyclerView.setAdapter(dataAdapter);


        return view;
    }
}
