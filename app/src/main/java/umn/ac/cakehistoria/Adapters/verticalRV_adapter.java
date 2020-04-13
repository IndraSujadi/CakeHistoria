package umn.ac.cakehistoria.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import umn.ac.cakehistoria.Adapters.Models.HorizontalModel;
import umn.ac.cakehistoria.Adapters.Models.VerticalModel;
import umn.ac.cakehistoria.R;

public class verticalRV_adapter extends RecyclerView.Adapter <verticalRV_adapter.verticalRVViewHolder> {

    Context context;
    ArrayList<VerticalModel> arrayList;

    public verticalRV_adapter(Context context, ArrayList<VerticalModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public verticalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical,parent,false);
        return new verticalRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull verticalRVViewHolder holder, int position) {

        final VerticalModel verticalModel = arrayList.get(position);
        String series = verticalModel.getSeries();
        ArrayList<HorizontalModel> singleItem = verticalModel.getArrayList();

        holder.txtSeries.setText(series);

        HorizontalRV_adapter horizontalRV_adapter = new HorizontalRV_adapter(context,singleItem);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        holder.recyclerView.setAdapter(horizontalRV_adapter);

        holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,verticalModel.getSeries(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class verticalRVViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView txtSeries;
        TextView txtMore;

        public verticalRVViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.rv_cakePreview);
            txtSeries = itemView.findViewById(R.id.txtSeries);
            txtMore = itemView.findViewById(R.id.txtMore);


        }
    }
}
