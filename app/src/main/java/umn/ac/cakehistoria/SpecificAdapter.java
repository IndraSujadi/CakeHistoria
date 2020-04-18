package umn.ac.cakehistoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpecificAdapter extends RecyclerView.Adapter<SpecificAdapter.SpecificViewHolder> {

    Context mCtx;
    List<Specific_model> specific_modelList;

    public SpecificAdapter(Context mCtx, List<Specific_model> specific_modelList) {
        this.mCtx = mCtx;
        this.specific_modelList = specific_modelList;
    }

    @NonNull
    @Override
    public SpecificViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_specific,null);
        return new SpecificViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificViewHolder holder, int position) {
        Specific_model model = specific_modelList.get(position);

        holder.imgSpecific.setImageDrawable(mCtx.getResources().getDrawable(model.getImgSpec()));

        holder.btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                fragHome home = new fragHome();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Frame,home).addToBackStack(null).commit();
            }
        });

        holder.btnOrder_similar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Frag_Account account = new Frag_Account();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Frame,account).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return specific_modelList.size();
    }

    class SpecificViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSpecific;
        Button btnUser,btnOrder_similar;

        public SpecificViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSpecific = itemView.findViewById(R.id.imgSpesific);
            btnUser = itemView.findViewById(R.id.btnUser);
            btnOrder_similar = itemView.findViewById(R.id.btnOrder_similars);
        }
    }
}
