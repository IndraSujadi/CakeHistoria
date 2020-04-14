package umn.ac.cakehistoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerticalSeriesAdapter extends RecyclerView.Adapter<VerticalSeriesAdapter.SeriesViewHolder> {

    Context mCtx;
    ArrayList<VerticalSeries_model> seriesList;

    public VerticalSeriesAdapter(Context mCtx, ArrayList<VerticalSeries_model> seriesList) {
        this.mCtx = mCtx;
        this.seriesList = seriesList;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_series,null);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        final VerticalSeries_model model = seriesList.get(position);

        ArrayList<Cake_model> singleItem = model.getArrayList();

        holder.txtSeries.setText(model.getSeries());
        CakeAdapter cakeAdapter = new CakeAdapter(mCtx,singleItem);

        holder.rvSeries.setHasFixedSize(true);
        holder.rvSeries.setLayoutManager(new LinearLayoutManager(mCtx,LinearLayoutManager.HORIZONTAL,false));

        holder.rvSeries.setAdapter(cakeAdapter);

        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Specific_Category specificCategory = new Specific_Category();
                FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frame,specificCategory);
                fragmentTransaction.commit();*/

                Toast.makeText(mCtx,"Maaf belom tau Caranya, bantu cari boleh kok, Thankyou",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    class SeriesViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rvSeries;
        TextView txtSeries, seeMore;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);

            rvSeries = itemView.findViewById(R.id.rvSeries);
            txtSeries = itemView.findViewById(R.id.txtSeries);
            seeMore = itemView.findViewById(R.id.seeMore);


        }
    }
}
