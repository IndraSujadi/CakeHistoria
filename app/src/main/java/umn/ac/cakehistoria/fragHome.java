package umn.ac.cakehistoria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class fragHome extends Fragment {

    RecyclerView rvBeranda;
    VerticalSeriesAdapter adapter;
    ArrayList<VerticalSeries_model> seriesList;

    //private List<Cake_model> cakeList;
    Context Ctx;

    ImageButton btnHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_home, container, false);

        /*rvBeranda = view.findViewById(R.id.rvBeranda);
        CakeAdapter adapter = new CakeAdapter(getContext(),cakeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Ctx, LinearLayoutManager.HORIZONTAL, false);
        rvBeranda.setLayoutManager(layoutManager);
        rvBeranda.setAdapter(adapter);*/

        seriesList = new ArrayList<>();

        rvBeranda = view.findViewById(R.id.rvBeranda);
        rvBeranda.setHasFixedSize(true);

        rvBeranda.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        adapter = new VerticalSeriesAdapter(getActivity(),seriesList);

        rvBeranda.setAdapter(adapter);

        setData();

        btnHistory = view.findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Masuk ke activity history di sini...
                Intent i = new Intent(getActivity(), Transactions.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

    private void setData() {
        for (int i=1;i<=4;i++){
            VerticalSeries_model verticalModel = new VerticalSeries_model();
            if (i == 1){
                verticalModel.setSeries("Birthday");
            }
            else if (i==2) {
                verticalModel.setSeries("Wedding");
            }
            else if (i==3) {
                verticalModel.setSeries("Valentine");
            }else {
                verticalModel.setSeries("Other");
            }


            ArrayList<Cake_model> arrayList = new ArrayList<>();

            for (int j = 0;j<=5;j++) {
                Cake_model cakeModel = new Cake_model();

                if (i == 1) {
                    cakeModel.setCategory("Birthday");
                    cakeModel.setHarga(560000);
                    cakeModel.setImgCake(R.drawable.maxime);
                    cakeModel.setImgUser(R.drawable.user_abu);
                    cakeModel.setLikes(250);
                    cakeModel.setNamaUser("Indra Sujadi");
                }
                else if (i == 2) {
                    cakeModel.setCategory("Wedding");
                    cakeModel.setHarga(800000);
                    cakeModel.setImgCake(R.drawable.wedding1);
                    cakeModel.setImgUser(R.drawable.user_abu);
                    cakeModel.setLikes(500);
                    cakeModel.setNamaUser("Vito Juliano");
                }
                else if (i == 3) {
                    cakeModel.setCategory("Valentine");
                    cakeModel.setHarga(650000);
                    cakeModel.setImgCake(R.drawable.valentine);
                    cakeModel.setImgUser(R.drawable.user_circle_solid);
                    cakeModel.setLikes(687);
                    cakeModel.setNamaUser("Darmadi");
                }
                else  {
                    cakeModel.setCategory("Other");
                    cakeModel.setHarga(250000);
                    cakeModel.setImgCake(R.drawable.other);
                    cakeModel.setImgUser(R.drawable.user_abu);
                    cakeModel.setLikes(500);
                    cakeModel.setNamaUser("Hannatassja");
                }

                arrayList.add(cakeModel);
            }

            verticalModel.setArrayList(arrayList);

            seriesList.add(verticalModel);
        }
        adapter.notifyDataSetChanged();
    }

}
