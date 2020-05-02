package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.Authentication;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.CustomerDetails;


import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;



import java.util.ArrayList;

public class Payment_Activity extends AppCompatActivity implements TransactionFinishedCallback {

    // Database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;

    TextView txtNama, txtEmail, txtNomor, txtAlamat, txtRequestDate, txtCakeType, txtCakeDetails, txtLetterCard
            , txtHargaProduk, txtHargaDeliv, txtTotal1, txtTotal2;

    private String refNama, refEmail, refNomor, refAlamat, refRequestDate, refCakeType, refCakeDetails, refLetterCard;

    private String refCakeShape, refCakeSize, refCakeTier;

    private int refHargaProduk, refHargaDeliv, refTotal1, refTotal2;

    private boolean includeLettercard;

    private String orderID, cakeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);

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
        txtTotal2 = findViewById(R.id.txtTotal2);

        txtLetterCard.setVisibility(View.INVISIBLE);

        findViewById(R.id.btnPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPay();
            }
        });

        makePayment();

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
//                        txtTotal1.setText(String.valueOf(refTotal1));
                        txtTotal1.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refTotal1))));

                        refTotal2 = document.get("totalPrice", Integer.class);
//                        txtTotal2.setText(String.valueOf(refTotal2));
                        txtTotal2.setText("Rp " + String.format("%, d", Integer.parseInt(String.valueOf(refTotal2))));
                    } else {
                        Log.d("CobaData", "No such document");
                    }
                } else {
                    Log.d("CobaData", "get failed with ", task.getException());
                }
            }
        });

    }

    private void makePayment(){
        SdkUIFlowBuilder.init()
                .setContext(this)
                .setMerchantBaseUrl(BuildConfig.BASE_URL)
                .setClientKey(BuildConfig.CLIENT_KEY)
                .setTransactionFinishedCallback(this)
                .enableLog(true)
                .setColorTheme(new CustomColorTheme("#777777","#f77474" , "#3f0d0d"))
                .buildSDK();
    }

    private void clickPay(){
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("101",2000, 1, "susus murni"));
        MidtransSDK.getInstance().startPaymentUiFlow(Payment_Activity.this );

//        Toast.makeText(this, "Coba Click" , Toast.LENGTH_LONG).show();

    }

    public static CustomerDetails customerDetails(){
        CustomerDetails cd = new CustomerDetails();
        cd.setFirstName("YOUR_PRODUCT");
        cd.setEmail("your_email@gmail.com");
        cd.setPhone("your_phone");
        return cd;
    }

    public static TransactionRequest transactionRequest(String id, double price, int qty, String name){
        TransactionRequest request =  new TransactionRequest(System.currentTimeMillis() + " " , 2000 );
        request.setCustomerDetails(customerDetails());
        ItemDetails details = new ItemDetails(id, price, qty, name);

        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        itemDetails.add(details);
        request.setItemDetails(itemDetails);
        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(Authentication.AUTH_RBA);
        creditCard.setBank(BankType.MANDIRI);

        request.setCreditCard(creditCard);
        return request;
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if(result.getResponse() != null){
            switch (result.getStatus()){
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Sukses " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        }else if(result.isTransactionCanceled()){
            Toast.makeText(this, "Transaction Failed", Toast.LENGTH_LONG).show();
        }else{
            if(result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))){
                Toast.makeText(this, "Transaction Invalid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_LONG).show();
            }
        }
    }


}
