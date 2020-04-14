package umn.ac.cakehistoria;

import android.content.Context;
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


public class fragHome extends Fragment {

    private RecyclerView rvBeranda;
    private List<Cake_model> cakeList;
    Context Ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_home, container, false);

        rvBeranda = view.findViewById(R.id.rvBeranda);
        CakeAdapter adapter = new CakeAdapter(getContext(),cakeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Ctx, LinearLayoutManager.HORIZONTAL, false);
        rvBeranda.setLayoutManager(layoutManager);
        rvBeranda.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cakeList = new ArrayList<>();

        cakeList.add(new Cake_model(R.drawable.maxime,
                "Birthday",
                200,
                R.drawable.user_circle_solid,
                "Indra Sujadi",
                560000));
        cakeList.add(new Cake_model(R.drawable.birthday2,
                "Birthday",
                200,
                R.drawable.user_circle_solid,
                "Indra Sujadi",
                860000));
        cakeList.add(new Cake_model(R.drawable.maxime,
                "Birthday",
                200,
                R.drawable.user_circle_solid,
                "Indra Sujadi",
                560000));
        cakeList.add(new Cake_model(R.drawable.maxime,
                "Birthday",
                200,
                R.drawable.user_circle_solid,
                "Indra Sujadi",
                560000));
        cakeList.add(new Cake_model(R.drawable.maxime,
                "Birthday",
                200,
                R.drawable.user_circle_solid,
                "Indra Sujadi",
                560000));


    }
}
