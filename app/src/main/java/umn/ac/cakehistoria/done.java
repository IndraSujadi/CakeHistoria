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

import java.util.ArrayList;
import java.util.List;

public class done extends Fragment {

    RecyclerView mRecyclerView;
    List<TransactionData> mTransactionList;
    TransactionData mTransactionData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_done, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mTransactionList = new ArrayList<>();

        mTransactionData = new TransactionData("TR-002","Birthday Custom Cake", "15-03-2020", "990000", "7");
        mTransactionList.add(mTransactionData);

//        mTransactionData = new TransactionData("TR-002","Birthday Custom Cake", "15-03-2020", "990000", "7");
//        mTransactionList.add(mTransactionData);



        DataAdapter dataAdapter = new DataAdapter(getActivity(), mTransactionList);
        mRecyclerView.setAdapter(dataAdapter);

        return view;
    }
}
