package umn.ac.cakehistoria;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Specific_Category extends Fragment {

    private RecyclerView rv_specific;
    private List<Specific_model> specificList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_specific_category, container, false);

        rv_specific = view.findViewById(R.id.rv_specific);
        SpecificAdapter adapter = new SpecificAdapter(getContext(),specificList);
        rv_specific.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_specific.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        specificList = new ArrayList<>();

        specificList.add(new Specific_model(R.drawable.maxime_x2));
        specificList.add(new Specific_model(R.drawable.maxime_x2));
        specificList.add(new Specific_model(R.drawable.maxime_x2));
        specificList.add(new Specific_model(R.drawable.maxime_x2));
        specificList.add(new Specific_model(R.drawable.maxime_x2));
        specificList.add(new Specific_model(R.drawable.maxime_x2));
        specificList.add(new Specific_model(R.drawable.maxime_x2));

    }
}
