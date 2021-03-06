package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class OnProgress_Detail extends AppCompatActivity {

    // Database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView txtNama, txtEmail, txtNomor, txtAlamat, txtRequestDate, txtCakeType, txtCakeDetails, txtLetterCard
            , txtHargaProduk, txtHargaDeliv, txtTotal1, txtOrderStatus, txtOrderID;

    LinearLayout receiveContainer;
    Button btnReceive;

    private String refNama, refEmail, refNomor, refAlamat, refRequestDate, refCakeType, refCakeDetails, refLetterCard;
    private String refCakeShape, refCakeSize, refCakeTier;
    private int refHargaProduk, refHargaDeliv, refTotal1;
    private boolean includeLettercard;
    private String orderID, cakeID;
    private String orderStatus;

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_progress__detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        orderID = i.getStringExtra("orderID");
        cakeID = i.getStringExtra("cakeID");

        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        txtNomor = findViewById(R.id.txtNomor);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtRequestDate = findViewById(R.id.txtRequestDate);
        txtCakeType = findViewById(R.id.txtCakeType);
        txtCakeDetails = findViewById(R.id.txtCakeDetails);
        txtLetterCard = findViewById(R.id.txtLettercard);
        txtHargaProduk = findViewById(R.id.txtHargaProduk);
        txtHargaDeliv = findViewById(R.id.txtHargaDeliv);
        txtTotal1 = findViewById(R.id.txtTotal1);
        txtOrderID = findViewById(R.id.txtOrderID);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);

        receiveContainer = findViewById(R.id.receiveContainer);
        btnReceive = findViewById(R.id.btnReceive);

        txtLetterCard.setVisibility(View.GONE);
        receiveContainer.setVisibility(View.GONE);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DocumentReference dbCakes = db.collection("Cakes").document(cakeID);
        dbCakes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map subdoc = document.getData();
                        Map cakeDetails = (Map) subdoc.get("CakeDetails");

                        refCakeType = (String) document.get("cakeCategory") + " Custom Cake";
                        txtCakeType.setText(refCakeType);

                        refCakeShape = (String) cakeDetails.get("cakeShape");
                        refCakeSize = (String) cakeDetails.get("cakeSize");
                        refCakeTier = (String) cakeDetails.get("cakeTier");

                        refCakeDetails = (String) cakeDetails.get("cakeType") + ", " + (String) cakeDetails.get("cakeColor")
                                + ", " + (String) cakeDetails.get("cakeDecor") + ", " + (String) cakeDetails.get("cakeTheme")
                                + ", " + (String) cakeDetails.get("cakeFlavor") + ", " + (String) cakeDetails.get("cakeTier")
                                + ", " + (String) cakeDetails.get("cakeShape") + ", " + (String) cakeDetails.get("cakeSize");
                        txtCakeDetails.setText(refCakeDetails);

                        refHargaProduk = document.get("cakePrice", Integer.class);
                        txtHargaProduk.setText(String.valueOf(refHargaProduk));

                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });

        DocumentReference dbOrders = db.collection("Orders").document(orderID);
        dbOrders.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        txtOrderID.setText(orderID);

                        orderStatus = document.get("orderStatus", String.class);
                        txtOrderStatus.setText(orderStatus);
                        if(orderStatus.equalsIgnoreCase("On Delivery")){
                            receiveContainer.setVisibility(View.VISIBLE);
                        }


                        Map subdoc = document.getData();
                        Map receiverInfo = (Map) subdoc.get("receiverInfo");

                        refNama = (String) receiverInfo.get("receiverName");
                        txtNama.setText(refNama);

                        refEmail = (String) receiverInfo.get("receiverEmail");
                        txtEmail.setText(refEmail);

                        refNomor = (String) receiverInfo.get("receiverPhone");
                        txtNomor.setText(refNomor);

                        refAlamat = (String) receiverInfo.get("receiverFullAddress") + ", " + (String) receiverInfo.get("receiverKecamatan")
                                + ", " + (String) receiverInfo.get("receiverKota") + ", " + (String) receiverInfo.get("receiverProvinsi")
                                + ", " + (String) receiverInfo.get("receiverZip");
                        txtAlamat.setText(refAlamat);

                        refRequestDate = document.get("requestDate", String.class);
                        txtRequestDate.setText(refRequestDate);

                        refHargaProduk = document.get("cakePrice", Integer.class);
                        txtHargaProduk.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refHargaProduk))));

                        refHargaDeliv = document.get("delivPrice", Integer.class);
                        txtHargaDeliv.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refHargaDeliv))));

                        refTotal1 = document.get("totalPrice", Integer.class);
                        txtTotal1.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refTotal1))));


                        btnReceive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getApplicationContext(), Review_Activity.class);
                                i.putExtra("cakeID", cakeID);
                                i.putExtra("orderID", orderID);
                                startActivity(i);
                                finish();
                            }
                        });

                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });
    }
}
