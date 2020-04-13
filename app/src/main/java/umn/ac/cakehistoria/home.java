package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import umn.ac.cakehistoria.Adapters.Models.HorizontalModel;
import umn.ac.cakehistoria.Adapters.Models.VerticalModel;
import umn.ac.cakehistoria.Adapters.verticalRV_adapter;

public class home extends AppCompatActivity {

    RecyclerView verticalrv;
    verticalRV_adapter adapter;
    ArrayList<VerticalModel> arrayListVertical;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrayListVertical = new ArrayList<>();

        verticalrv = findViewById(R.id.recyclerview);
        verticalrv.setHasFixedSize(true);

        verticalrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new verticalRV_adapter(this, arrayListVertical);

        verticalrv.setAdapter(adapter);

        /*VerticalModel verticalModel = new VerticalModel();
        verticalModel.setSeries("Birthday");

        ArrayList<HorizontalModel> arrayList = new ArrayList<>();
        HorizontalModel horizontalModel = new HorizontalModel();

        horizontalModel.setCategory("Birthday");
        horizontalModel.setLikes(150);
        horizontalModel.setImgPath_user(R.drawable.user_circle_solid);
        horizontalModel.setNamaUser("Vito Candra");
        horizontalModel.setHarga(560000);
        horizontalModel.setImgPath_cake(R.drawable.maxime);

        arrayList.add(horizontalModel);

        verticalModel.setArrayList(arrayList);

        arrayListVertical.add(verticalModel);*/
        //setData();
    }

    /*private void setData() {

        for (int i = 1;i<=5;i++) {
            VerticalModel  mverticalModel = new VerticalModel();

            mverticalModel.setSeries("Birthday");

            ArrayList<HorizontalModel> arrayList = new ArrayList<>();

            for (int j = 0; j<= 5;j++) {
                HorizontalModel mhorizontalModel = new HorizontalModel();

                mhorizontalModel.setCategory("Birthday");
                mhorizontalModel.setLikes(150);
                mhorizontalModel.setImgPath_user(R.drawable.user_circle_solid);
                mhorizontalModel.setNamaUser("Vito Candra");
                mhorizontalModel.setHarga(560000);
                mhorizontalModel.setImgPath_cake(R.drawable.maxime);

                arrayList.add(mhorizontalModel);
            }
            mverticalModel.setArrayList(arrayList);

            arrayListVertical.add(mverticalModel);

        }
        adapter.notifyDataSetChanged();

    }*/
}
