package umn.ac.cakehistoria.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import umn.ac.cakehistoria.Adapters.Models.HorizontalModel;
import umn.ac.cakehistoria.R;

public class HorizontalRV_adapter extends RecyclerView.Adapter<HorizontalRV_adapter.HorizontalRVViewHolder> {

    Context mContext;
    ArrayList<HorizontalModel> arrayList;

    public HorizontalRV_adapter(Context mContext, ArrayList<HorizontalModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HorizontalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_list,parent,false);
        return new HorizontalRVViewHolder(view);
        // note : catalog list sebagai item_horizontal
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRVViewHolder holder, int position) {

        HorizontalModel horizontalModel = arrayList.get(position);

        holder.txtCategory.setText(horizontalModel.getCategory());
        holder.txtLikes.setText(horizontalModel.getLikes());
        holder.txt_namaUser.setText(horizontalModel.getNamaUser());
        holder.txtHarga.setText(String.valueOf(horizontalModel.getHarga()));

        holder.imgUser.setImageDrawable(mContext.getResources().getDrawable(horizontalModel.getImgPath_user()));
        holder.imgCake.setImageDrawable(mContext.getResources().getDrawable(horizontalModel.getImgPath_cake()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HorizontalRVViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategory,txtLikes,txt_namaUser,txtHarga;
        ImageView imgCake, imgUser;

        public HorizontalRVViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtLikes = itemView.findViewById(R.id.txtLikes);
            txt_namaUser = itemView.findViewById(R.id.txt_namaUser);
            txtHarga = itemView.findViewById(R.id.txtHarga);
            imgCake = itemView.findViewById(R.id.imgCake);
            imgUser  = itemView.findViewById(R.id.imgUser);
        }
    }
}
