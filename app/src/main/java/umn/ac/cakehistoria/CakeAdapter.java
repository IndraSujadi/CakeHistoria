package umn.ac.cakehistoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {

    Context mCtx;
    ArrayList<Cake_model> cakeModelArrayList;

    public CakeAdapter(Context mCtx, ArrayList<Cake_model> cakeModelArrayList) {
        this.mCtx = mCtx;
        this.cakeModelArrayList = cakeModelArrayList;
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
        final Cake_model model = cakeModelArrayList.get(position);

        holder.imgCake.setImageDrawable(mCtx.getResources().getDrawable(model.getImgCake()));
        holder.imgUser.setImageDrawable(mCtx.getResources().getDrawable(model.getImgUser()));

        holder.catergory.setText(model.getCategory());
        holder.likes.setText(String.valueOf(model.getLikes()));
        holder.namaUser.setText(model.getNamaUser());
        holder.harga.setText(String.valueOf(model.getHarga()));

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Individual_Item individualItem = new Individual_Item();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Frame,individualItem).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {

        return cakeModelArrayList.size();
    }

    class CakeViewHolder extends RecyclerView.ViewHolder{

        ImageView imgCake, imgUser;
        TextView catergory, likes, namaUser, harga;
        Button btnOrder_similar, btnDetail;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCake = itemView.findViewById(R.id.imgCake);
            imgUser = itemView.findViewById(R.id.imgUser);
            catergory = itemView.findViewById(R.id.txtCategory);
            likes = itemView.findViewById(R.id.txtLikes);
            namaUser = itemView.findViewById(R.id.txt_namaUser);
            harga = itemView.findViewById(R.id.txtHarga);

            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnOrder_similar = itemView.findViewById(R.id.btnOrder_similar);
        }
    }
}
