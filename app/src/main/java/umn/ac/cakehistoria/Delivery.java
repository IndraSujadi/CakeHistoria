package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import umn.ac.cakehistoria.api.ApiClient;
import umn.ac.cakehistoria.api.ApiInterface;
import umn.ac.cakehistoria.model.Data;
import umn.ac.cakehistoria.model.Region;
import umn.ac.cakehistoria.model.UniqueCode;

public class Delivery extends AppCompatActivity {
    // Database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;

    // Layout elements:
    Spinner spinProvinsi, spinKota, spinKecamatan, spinKelurahan;
    EditText edtNama, edtTelp, edtEmail, edtAlamat, edtKodePos;
    CheckBox checkAsuransi;
    TextView txtHargaDeliv;
    ImageButton btnNext_toPayment;

    private String receiverName = "";
    private String receiverPhone = "";
    private String receiverEmail = "";
    private String receiverProvinsi = "";
    private String receiverKota = "";
    private String receiverKecamatan = "";
    private String receiverKelurahan = "";
    private String receiverZip = "";
    private String receiverFullAddress = "";

    private Boolean asuransi = false;
    private int delivPrice = 9000;
    private int refCakePrice = 0;
    private int totalPrice;

    private String orderID;
    private String cakeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Intent i = getIntent();
        orderID = i.getStringExtra("orderID");
        cakeID = i.getStringExtra("cakeID");

        DocumentReference dbOrders = db.collection("Orders").document(orderID);

        dbOrders.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("CobaData", "DocumentSnapshot data: " + document.getData());
                        refCakePrice = document.get("cakePrice", Integer.class);
                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });

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

        edtNama = findViewById(R.id.edtName);
        edtTelp = findViewById(R.id.edtTelp);
        edtEmail = findViewById(R.id.edtEmail);
        edtAlamat = findViewById(R.id.edtAlamat);
        edtKodePos = findViewById(R.id.edtKodePos);
        checkAsuransi = findViewById(R.id.checkAsuransi);
        txtHargaDeliv = findViewById(R.id.txtHargaDeliv);

        btnNext_toPayment = findViewById(R.id.btnNext_toPayment);

        // API daftar provinsi, kab/kota, kecamatan, kelurahan Indonesia
        loadUniqueCode();

        btnNext_toPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiverName = edtNama.getText().toString();
                receiverPhone = edtTelp.getText().toString();
                receiverEmail = edtEmail.getText().toString();
                receiverProvinsi = spinProvinsi.getSelectedItem().toString();
                receiverKota = spinKota.getSelectedItem().toString();
                receiverKecamatan = spinKecamatan.getSelectedItem().toString();
                receiverKelurahan = spinKelurahan.getSelectedItem().toString();
                receiverZip = edtKodePos.getText().toString();
                receiverFullAddress = edtAlamat.getText().toString();

                if(checkAsuransi.isChecked()){
                    asuransi = true;
                    delivPrice+=1000;
                }

                totalPrice = refCakePrice + delivPrice;

                Map<String, Object> addtOrder = new HashMap<>();
                addtOrder.put("includeInsurance", asuransi);
                addtOrder.put("delivPrice", delivPrice);
                addtOrder.put("totalPrice", totalPrice);

                db.collection("Orders").document(orderID)
                        .set(addtOrder, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "Tambahan document ORDER Berhasil dimasukkan: " + orderID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CobaData", "Tambahan document ORDER gagal dimasukkan");
                            }
                        });

                Map<String, Object> receiverObj = new HashMap<>();
                receiverObj.put("receiverName", receiverName);
                receiverObj.put("receiverPhone", receiverPhone);
                receiverObj.put("receiverEmail", receiverEmail);
                receiverObj.put("receiverProvinsi", receiverProvinsi);
                receiverObj.put("receiverKota", receiverKota);
                receiverObj.put("receiverKecamatan", receiverKecamatan);
                receiverObj.put("receiverKelurahan", receiverKelurahan);
                receiverObj.put("receiverZip", receiverZip);
                receiverObj.put("receiverFullAddress", receiverFullAddress);
                Map<String, Object> receiverSubdoc = new HashMap<>();
                receiverSubdoc.put("receiverInfo", receiverObj);

                db.collection("Orders").document(orderID)
                        .set(receiverSubdoc, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "Subdocument Receiver Info berhasil dimasukkan: " + orderID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CobaData", "Subdocument Receiver Info gagal dimasukkan");
                            }
                        });

                Intent i = new Intent( Delivery.this, Payment_Activity.class);
                i.putExtra("cakeID", cakeID);
                i.putExtra("orderID", orderID);
                startActivity(i);
            }
        });
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
                Log.d("API_Provinsi", "Gabisa weh: " + t.getMessage());
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



