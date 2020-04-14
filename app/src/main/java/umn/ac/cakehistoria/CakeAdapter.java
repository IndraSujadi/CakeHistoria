package umn.ac.cakehistoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {

    Context mCtx;
    List<Cake_model> cake_modelList;

    public CakeAdapter(Context mCtx, List<Cake_model> cake_modelList) {
        this.mCtx = mCtx;
        this.cake_modelList = cake_modelList;
    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.catalog_list,null);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {
        Cake_model model = cake_modelList.get(position);

        holder.imgCake.setImageDrawable(mCtx.getResources().getDrawable(model.getImgCake()));
        holder.imgUser.setImageDrawable(mCtx.getResources().getDrawable(model.getImgUser()));

        holder.catergory.setText(model.getCategory());
        holder.likes.setText(String.valueOf(model.getLikes()));
        holder.namaUser.setText(model.getNamaUser());
        holder.harga.setText(String.valueOf(model.getHarga()));

    }

    @Override
    public int getItemCount() {

        return cake_modelList.size();
    }

    class CakeViewHolder extends RecyclerView.ViewHolder{

        ImageView imgCake, imgUser;
        TextView catergory, likes, namaUser, harga;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCake = itemView.findViewById(R.id.imgCake);
            imgUser = itemView.findViewById(R.id.imgUser);
            catergory = itemView.findViewById(R.id.txtCategory);
            likes = itemView.findViewById(R.id.txtLikes);
            namaUser = itemView.findViewById(R.id.txt_namaUser);
            harga = itemView.findViewById(R.id.txtHarga);

        }
    }
}
