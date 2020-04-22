package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionKabAdapter;
import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionKecAdapter;
import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionProvAdapter;
import meridianid.farizdotid.actdaerahindonesia.util.JsonParse;


public class Delivery extends AppCompatActivity {

    private static final String[] provinsi = new String[] {
            "Banten","DKI Jakarta","Jawa Barat", "DI Yogyakarta", "Jawa Tengah", "Jawa Timur", "Aceh","Bali","Bengkulu","Jambi","Gorontalo"
    };

    private static final String[] kota = new String[] {
            "Kota Yogyakarta",
            "Kabupaten Bantul",
            "Kabupaten Lebak",
            "Kabupaten Pandeglang",
            "Kabupaten Serang",
            "Kabupaten Tangerang",
            "Kota Cilegon",
            "Kota Serang",
            "Kota Tangerang",
            "Kota Tangerang selatan",
    };

    private JsonParse jsonParse;
    Spinner spinProvinsi, spinKota, spinKecamatan;
    AutoCompleteTextView autoProvinsi, autoKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        autoProvinsi = findViewById(R.id.autoProvinsi);
        autoKota = findViewById(R.id.autoKota);
        //spinProvinsi = findViewById(R.id.spinProvinsi);
        //spinKota = findViewById(R.id.spinKota);
        spinKecamatan = findViewById(R.id.spinKecamatan);

        ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinsi);
        ArrayAdapter<String> adapterKota = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kota);

        autoProvinsi.setAdapter(adapterProvinsi);
        autoProvinsi.setThreshold(1);

        autoKota.setAdapter(adapterKota);
        autoKota.setThreshold(1);






    }
}
