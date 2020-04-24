package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import umn.ac.cakehistoria.api.ApiClient;
import umn.ac.cakehistoria.api.ApiInterface;
import umn.ac.cakehistoria.model.Data;
import umn.ac.cakehistoria.model.Region;
import umn.ac.cakehistoria.model.UniqueCode;

public class Delivery extends AppCompatActivity {

    Spinner spinProvinsi, spinKota, spinKecamatan,spinKelurahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        //autoProvinsi = findViewById(R.id.autoProvinsi);
        //autoKota = findViewById(R.id.autoKota);
        spinProvinsi = findViewById(R.id.spinProvinsi);
        spinKota = findViewById(R.id.spinKota);
        spinKecamatan = findViewById(R.id.spinKecamatan);
        spinKelurahan = findViewById(R.id.spinKelurahan);

        /*ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinsi);
        ArrayAdapter<String> adapterKota = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kota);

        autoProvinsi.setAdapter(adapterProvinsi);
        autoProvinsi.setThreshold(1);

        autoKota.setAdapter(adapterKota);
        autoKota.setThreshold(1);*/

        loadUniqueCode();

    }

    public void loadUniqueCode() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();

        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                String code = "MeP7c5ne" + response.body().getUniqueCode();
                loadProvinceList(code);
            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }

    public void loadProvinceList(final String code) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Data> call = apiService.getProvinceList(code);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();

                // masukkan daftar provinsi ke list string
                List<String> provs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                provs.add(0, "Silahkan Pilih Provinsi");
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    provs.add(daftarProvinsi.get(i).getName());
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(Delivery.this,
                        android.R.layout.simple_spinner_item, provs);
                spinProvinsi.setAdapter(adapter);

                spinProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinProvinsi.getSelectedItem().toString().equals("Silahkan Pilih Provinsi")) {
                            long idProv = daftarProvinsi.get(position - 1).getId();
                            loadKabupatenList(code, idProv);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadKabupatenList(final String code, final long idProv) {
        spinKecamatan.setAdapter(null);
        spinKelurahan.setAdapter(null);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getKabupatenList(code, idProv);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKabupaten = response.body().getData();

                // masukkan daftar kabupaten ke list string
                List<String> kabs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kabs.add(0, "Silahkan Pilih Kabupaten");
                for (int i = 0; i < daftarKabupaten.size(); i++) {
                    kabs.add(daftarKabupaten.get(i).getName());
                }

                // masukkan daftar kabupaten ke spinner
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(Delivery.this,
                        android.R.layout.simple_spinner_item, kabs);
                spinKota.setAdapter(adapter);

                spinKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinKota.getSelectedItem().toString().equals("Silahkan Pilih Kabupaten")) {
                            long idKab = daftarKabupaten.get(position - 1).getId();
                            loadKecamatanList(code, idKab);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadKecamatanList(final String code, long idKab) {
        spinKelurahan.setAdapter(null);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getKecamatanList(code, idKab);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKecamatan = response.body().getData();

                // masukkan daftar kecamatan ke list string
                List<String> kecs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kecs.add(0, "Silahkan Pilih");
                for (int i = 0; i < daftarKecamatan.size(); i++) {
                    kecs.add(daftarKecamatan.get(i).getName());
                }

                // masukkan daftar kecamatan ke spinner
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(Delivery.this,
                        android.R.layout.simple_spinner_item, kecs);
                spinKecamatan.setAdapter(adapter);

                spinKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinKecamatan.getSelectedItem().toString().equals("Silahkan Pilih")) {
                            long idKec = daftarKecamatan.get(position - 1).getId();
                            loadKelurahanList(code, idKec);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadKelurahanList(final String code, final long idKec) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getKelurahanList(code, idKec);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKelurahan = response.body().getData();

                // masukkan daftar kelurahan ke list string
                List<String> kels = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kels.add(0, "Silahkan Pilih");
                for (int i = 0; i < daftarKelurahan.size(); i++) {
                    kels.add(daftarKelurahan.get(i).getName());
                }

                // masukkan daftar kelurahan ke spinner
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(Delivery.this,
                        android.R.layout.simple_spinner_item, kels);
                spinKelurahan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }




}



