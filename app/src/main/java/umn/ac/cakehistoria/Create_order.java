package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import umn.ac.cakehistoria.pagerchoice.pagerchoice_adapter;

public class Create_order extends AppCompatActivity {

    // Database
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri imgUri;
    private StorageTask uploadTask;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;

    // Layout elements:
    Button btnUpload, btnSelect;
    ImageButton btnNext_toDelivery;
    FragmentPagerAdapter adapterViewPager;
    View currView;
    Spinner spinCategory;
    EditText edtAdditionalText;
    EditText edtSpecialOrders;
    EditText edtOtherCategory;
    EditText edtReqDate;
    CheckBox checkLetterCard;
    EditText edtLetterMessage;
    TextView txtCakePrice;
    Button btnNext;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String ownerName = "";
    String userID = "";

    // Cake's detail
    private String cakeID = "";
    private String orderID = "";
    private String cakeType = "Sponge";
    private String cakeColor = "Brownish";
    private String cakeDecor = "Butter Cream";
    private String cakeTheme = "Colorful Sprinkle";
    private String cakeFlavor = "Chocolate";
    private String cakeTier = "One tier";
    private String nTier = "";
    private String cakeShape = "Round";
    private String cakeSize = "Diameter 16 cm";
    private String cakeCategory = "Birthday";

    private String addtText = "";
    private String specialOrders = "";
    private boolean includeLetterCard = false;
    private String letterMessage = "";
    private String figureURL = "";

    private String testimony = "";
    private int rating = 0;
    private String testimonyID;

    private int likes = 0;
    private String imageURL = "";
    private int cakePrice = 0;

    private String requestDate = "";
    private Date orderDateTime;
    private String orderStatus = "Order is processed";

    // MASTER CLASS:
    class_cake Cake = new class_cake();
    String cakeTypeCoba;

    private String refKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        ViewPager vpPager = (ViewPager) findViewById(R.id.pagerView);
        adapterViewPager = new PagerViewAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        ViewPager vpPager2 = (ViewPager) findViewById(R.id.pagerChoice);
        adapterViewPager = new pagerchoice_adapter(getSupportFragmentManager());
        vpPager2.setAdapter(adapterViewPager);

        spinCategory = findViewById(R.id.spinCategory);
        edtOtherCategory = findViewById(R.id.edtOtherCategory);
        edtAdditionalText = findViewById(R.id.edtAdditionalText);
        edtSpecialOrders = findViewById(R.id.edtSpecialOrders);
        edtReqDate = findViewById(R.id.edtReqDate);
        checkLetterCard = findViewById(R.id.checkLettercard);
        edtLetterMessage = findViewById(R.id.edtLetterMessage);
        txtCakePrice = findViewById(R.id.txtCakePrice);
        btnNext_toDelivery = findViewById(R.id.btnNext_toDelivery);

        ArrayAdapter <CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.cake_category_lists, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(categoryAdapter);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

        edtOtherCategory.setVisibility(View.INVISIBLE);
        edtLetterMessage.setVisibility(View.INVISIBLE);

        checkLetterCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    edtLetterMessage.setVisibility(View.VISIBLE);
                }
                else{
                    edtLetterMessage.setVisibility(View.INVISIBLE);
                }
            }
        });

//         DATE PICKER
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edtReqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        // -------------------- GET VIEWPAGER DATA ---------------------------

        vpPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String TAG = "ViewPager";
                if (positionOffset >= 0) {
                    currView = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pagerChoice + ":" + position).getView();
                    if (currView != null) {
//                        Log.d(TAG, "The View object has already catched. Now get the layout instance(s)");
                        // CAKE TYPE
                        if (position == 0) {
                            RadioGroup rg;
                            rg = currView.findViewById(R.id.type);

                            RadioButton rb;

                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

                            cakeType = rb.getText().toString();

                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int selectedRbId) {
                                    RadioButton rb;
                                    rb = currView.findViewById(selectedRbId);

                                    cakeType = rb.getText().toString();
                                    Cake.setCakeType(cakeType);
                                    Log.d(TAG, "Cake Type: " + cakeType);
                                }
                            });

                            Log.d(TAG, "Cake Type: " + cakeType);
                        }
                        // CAKE COLOR
                        else if (position == 1) {
                            RadioButton brown, red, pink, blue, green, rainbow, purple, creamy;

                            brown = currView.findViewById(R.id.brown);
                            red = currView.findViewById(R.id.red);
                            pink = currView.findViewWithTag(R.id.pink); // ini null object reference gatau kenapa
                            blue = currView.findViewById(R.id.blue);
                            green = currView.findViewById(R.id.green);
                            rainbow = currView.findViewById(R.id.rainbow);
                            purple = currView.findViewById(R.id.purple);
                            creamy = currView.findViewById(R.id.creamy);

                            if (brown.isChecked()) {
                                cakeColor = brown.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }
                            if (red.isChecked()) {
                                cakeColor = red.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }
//                            if (pink.isChecked()) {
//                                cakeColor = pink.getText().toString();
//                                Log.d(TAG, "Cake Color: " + cakeColor);
//                            }
                            if (blue.isChecked()) {
                                cakeColor = blue.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }
                            if (green.isChecked()) {
                                cakeColor = green.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }
                            if (rainbow.isChecked()) {
                                cakeColor = rainbow.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }
                            if (purple.isChecked()) {
                                cakeColor = purple.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }
                            if (creamy.isChecked()) {
                                cakeColor = creamy.getText().toString();
                                Log.d(TAG, "Cake Color: " + cakeColor);
                            }

                            brown.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    brown.setChecked(true);
                                    cakeColor = brown.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });

                            red.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    red.setChecked(true);
                                    cakeColor = red.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });

//                            pink.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    pink.setChecked(true);
//                                    cakeColor = pink.getText().toString();
//                                    Log.d(TAG, "Cake Color: " + cakeColor);
//                                }
//                            });

                            blue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    blue.setChecked(true);
                                    cakeColor = blue.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });

                            green.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    green.setChecked(true);
                                    cakeColor = green.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });

                            rainbow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rainbow.setChecked(true);
                                    cakeColor = rainbow.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });

                            purple.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    purple.setChecked(true);
                                    cakeColor = purple.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });

                            creamy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    creamy.setChecked(true);
                                    cakeColor = creamy.getText().toString();
                                    Log.d(TAG, "Cake Color: " + cakeColor);
                                }
                            });
                        }

                        // CAKE COATING DECORATION
                        else if (position == 2) {
                            RadioGroup rg;
                            rg = currView.findViewById(R.id.rgDecor);

                            RadioButton rb;

                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

                            cakeDecor = rb.getText().toString();

                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int selectedRbId) {
                                    RadioButton rb;
                                    rb = currView.findViewById(selectedRbId);

                                    cakeDecor = rb.getText().toString();
                                    Log.d(TAG, "Cake Decor: " + cakeDecor);
                                }
                            });

                            Log.d(TAG, "Cake Decor: " + cakeDecor);
                        }
                        // CAKE THEME
                        else if (position == 3) {
                            RadioButton sprinkle, fruity, flourish, romance, kids, youth;

                            sprinkle = currView.findViewById(R.id.sprinkle);
                            fruity = currView.findViewById(R.id.fruity);
                            flourish = currView.findViewWithTag(R.id.flourish);
                            romance = currView.findViewById(R.id.romance);
                            kids = currView.findViewById(R.id.kids);
                            youth = currView.findViewById(R.id.youth);

                            if (sprinkle.isChecked()) {
                                cakeTheme = sprinkle.getText().toString();
                                Log.d(TAG, "Cake Theme: " + cakeTheme);
                            }
                            if (fruity.isChecked()) {
                                cakeTheme = fruity.getText().toString();
                                Log.d(TAG, "Cake Theme: " + cakeTheme);
                            }
//                            if(flourish.isChecked()){
//                                cakeTheme = flourish.getText().toString();
//                                Log.d(TAG, "Cake Theme: " + cakeTheme);
//                            }
                            if (romance.isChecked()) {
                                cakeTheme = romance.getText().toString();
                                Log.d(TAG, "Cake Theme: " + cakeTheme);
                            }
                            if (kids.isChecked()) {
                                cakeTheme = kids.getText().toString();
                                Log.d(TAG, "Cake Theme: " + cakeTheme);
                            }
                            if (youth.isChecked()) {
                                cakeTheme = youth.getText().toString();
                                Log.d(TAG, "Cake Theme: " + cakeTheme);
                            }

                            sprinkle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sprinkle.setChecked(true);
                                    cakeTheme = sprinkle.getText().toString();
                                    Log.d(TAG, "Cake Theme: " + cakeTheme);
                                }
                            });

                            fruity.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    fruity.setChecked(true);
                                    cakeTheme = fruity.getText().toString();
                                    Log.d(TAG, "Cake Theme: " + cakeTheme);
                                }
                            });

//                            flourish.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    flourish.setChecked(true);
//                                    cakeTheme = flourish.getText().toString();
//                                    Log.d(TAG, "Cake Theme: " + cakeTheme);
//                                }
//                            });

                            romance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    romance.setChecked(true);
                                    cakeTheme = romance.getText().toString();
                                    Log.d(TAG, "Cake Theme: " + cakeTheme);
                                }
                            });

                            kids.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    kids.setChecked(true);
                                    cakeTheme = kids.getText().toString();
                                    Log.d(TAG, "Cake Theme: " + cakeTheme);
                                }
                            });

                            youth.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    youth.setChecked(true);
                                    cakeTheme = youth.getText().toString();
                                    Log.d(TAG, "Cake Theme: " + cakeTheme);
                                }
                            });
                        }
                        // CAKE FLAVOR
                        else if (position == 4) {
                            RadioButton chocolate, cappucino, strawberry, green_tea, cheese;

                            chocolate = currView.findViewById(R.id.chocolate);
                            cappucino = currView.findViewById(R.id.cappucino);
                            strawberry = currView.findViewWithTag(R.id.strawberry);
                            green_tea = currView.findViewById(R.id.green_tea);
                            cheese = currView.findViewById(R.id.cheese);

                            if (chocolate.isChecked()) {
                                cakeFlavor = chocolate.getText().toString();
                                Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                            }
                            if (cappucino.isChecked()) {
                                cakeFlavor = cappucino.getText().toString();
                                Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                            }
//                            if(strawberry.isChecked()){
//                                cakeFlavor = strawberry.getText().toString();
//                                Log.d(TAG, "Cake Flavor: " + cakeFlavor);
//                            }
                            if (green_tea.isChecked()) {
                                cakeFlavor = green_tea.getText().toString();
                                Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                            }
                            if (cheese.isChecked()) {
                                cakeFlavor = cheese.getText().toString();
                                Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                            }

                            chocolate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    chocolate.setChecked(true);
                                    cakeFlavor = chocolate.getText().toString();
                                    Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                                }
                            });

                            cappucino.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cappucino.setChecked(true);
                                    cakeFlavor = cappucino.getText().toString();
                                    Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                                }
                            });

//                            strawberry.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    strawberry.setChecked(true);
//                                    cakeFlavor = strawberry.getText().toString();
//                                    Log.d(TAG, "Cake Flavor: " + cakeFlavor);
//                                }
//                            });

                            green_tea.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    green_tea.setChecked(true);
                                    cakeFlavor = green_tea.getText().toString();
                                    Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                                }
                            });

                            cheese.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cheese.setChecked(true);
                                    cakeFlavor = cheese.getText().toString();
                                    Log.d(TAG, "Cake Flavor: " + cakeFlavor);
                                }
                            });
                        }
                        // CAKE TIER
                        else if (position == 5) {
                            RadioGroup rg;
                            rg = currView.findViewById(R.id.rgTier);

                            RadioButton rb;

                            Spinner spinTier = currView.findViewById(R.id.spinTiers);


                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

                            cakeTier = rb.getText().toString();

                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int selectedRbId) {
                                    RadioButton rb;
                                    rb = currView.findViewById(selectedRbId);

                                    if(selectedRbId == R.id.multi){
                                        nTier = " " + spinTier.getSelectedItem().toString();
                                        cakeTier = rb.getText().toString() + nTier;
                                        Log.d(TAG, "cakeTier pas baru masuk rb Multi: " + cakeTier);
                                        spinTier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                nTier = " " + spinTier.getSelectedItem().toString();
                                                Log.d(TAG, "nTier: " + nTier);
                                                cakeTier = rb.getText().toString() + nTier;
                                                Log.d(TAG, "cakeTier: " + cakeTier);
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                    else{
                                        nTier = "";
                                        cakeTier = rb.getText().toString() + nTier;
                                        Log.d(TAG, "nTier: " + nTier);
                                    }

                                    Log.d(TAG, "Cake Tier: " + cakeTier);
                                }
                            });
                            cakeTier = rb.getText().toString() + nTier;
                            Log.d(TAG, "Cake Tier Akhir: " + cakeTier);

                        }
                        // CAKE SHAPE
                        else if (position == 6) {
                            RadioGroup rg;
                            rg = currView.findViewById(R.id.rgShape);

                            RadioButton rb;

                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

                            cakeShape = rb.getText().toString();

                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int selectedRbId) {
                                    RadioButton rb;
                                    rb = currView.findViewById(selectedRbId);

                                    cakeShape = rb.getText().toString();
                                    Log.d(TAG, "Cake Shape: " + cakeShape);
                                }
                            });

                            Spinner spinSize;
                            spinSize = currView.findViewById(R.id.spinSize);

                            cakeSize = spinSize.getSelectedItem().toString();

                            spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    cakeSize = spinSize.getSelectedItem().toString();
                                    Log.d(TAG, "Cake Size: " + cakeSize);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            Log.d(TAG, "Cake Shape: " + cakeShape);
                            Log.d(TAG, "Cake Size: " + cakeSize);
                        }
                    } else {
                        Log.d(TAG, "The View object is null");
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // -------------------- END OF GET VIEWPAGER DATA ---------------------------


        int item = vpPager2.getCurrentItem();
        Fragment fragPagerChoice = adapterViewPager.getItem(item);

        btnUpload = findViewById(R.id.btnUpload);
        FragmentActivity fragmentActivity = fragPagerChoice.getActivity();
        btnSelect = findViewById(R.id.btnSelect);
        btnNext_toDelivery = findViewById(R.id.btnNext_toDelivery);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Images");

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgUri == null) {
                    Toast.makeText(Create_order.this, "Silahkan Pilih Gambar yang ingin di Upload", Toast.LENGTH_LONG).show();
                } else if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(Create_order.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });

        btnNext_toDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ViewPager", "Cake Tier hasil akhir: " + cakeTier);
                orderID = generateRefKey(20);
                cakeID = generateRefKey(20);

                // RETRIEVE CAKE'S DATA
                cakeCategory = spinCategory.getSelectedItem().toString();
                ownerName = fbUser.getDisplayName();
                addtText = edtAdditionalText.getText().toString();
                specialOrders = edtSpecialOrders.getText().toString();
                if (checkLetterCard.isChecked()) {
                    includeLetterCard = true;
                }
                letterMessage = edtLetterMessage.getText().toString();
                if (figureURL == null) {
                    figureURL = "";
                }else {

                }

                if(cakeShape.equalsIgnoreCase("Round")){
                    switch(cakeSize){
                        case "Diameter 16 cm":
                            switch (cakeTier){
                                case "Cupcake":
                                    cakePrice += 100000;
                                    break;
                                case "One tier":
                                    cakePrice += 105000;
                                    break;
                                case "Multi-tier":
                                    cakePrice += 155000;
                                    break;
                                case "Multi-tier 2":
                                    cakePrice += 155000;
                                    break;
                                case "Multi-tier 3":
                                    cakePrice += 205000;
                                    break;
                                case "Multi-tier 4":
                                    cakePrice += 255000;
                                    break;
                                default:
                                    cakePrice += 0;
                            }
                            break;
                        case "Diameter 20 cm":
                            switch (cakeTier){
                                case "Cupcake":
                                    cakePrice += 120000;
                                    break;
                                case "One tier":
                                    cakePrice += 125000;
                                    break;
                                case "Multi-tier":
                                    cakePrice += 175000;
                                    break;
                                case "Multi-tier 2":
                                    cakePrice += 175000;
                                    break;
                                case "Multi-tier 3":
                                    cakePrice += 225000;
                                    break;
                                case "Multi-tier 4":
                                    cakePrice += 275000;
                                    break;
                                default:
                                    cakePrice += 0;
                            }
                            break;
                        case "Diameter 26 cm":
                            switch (cakeTier){
                                case "Cupcake":
                                    cakePrice += 140000;
                                    break;
                                case "One tier":
                                    cakePrice += 145000;
                                    break;
                                case "Multi-tier":
                                    cakePrice += 195000;
                                    break;
                                case "Multi-tier 2":
                                    cakePrice += 195000;
                                    break;
                                case "Multi-tier 3":
                                    cakePrice += 245000;
                                    break;
                                case "Multi-tier 4":
                                    cakePrice += 295000;
                                    break;
                                default:
                                    cakePrice += 0;
                            }
                            break;
                        default:
                            cakePrice += 0;
                    }
                }
                if(cakeShape.equalsIgnoreCase("Square")){
                    switch(cakeSize){
                        case "24x24 cm":
                            switch (cakeTier){
                                case "Cupcake":
                                    cakePrice += 100000;
                                    break;
                                case "One tier":
                                    cakePrice += 105000;
                                    break;
                                case "Multi-tier":
                                    cakePrice += 155000;
                                    break;
                                case "Multi-tier 2":
                                    cakePrice += 155000;
                                    break;
                                case "Multi-tier 3":
                                    cakePrice += 205000;
                                    break;
                                case "Multi-tier 4":
                                    cakePrice += 255000;
                                    break;
                                default:
                                    cakePrice += 0;
                            }
                            break;
                        case "30x30 cm":
                            switch (cakeTier){
                                case "Cupcake":
                                    cakePrice += 120000;
                                    break;
                                case "One tier":
                                    cakePrice += 125000;
                                    break;
                                case "Multi-tier":
                                    cakePrice += 175000;
                                    break;
                                case "Multi-tier 2":
                                    cakePrice += 175000;
                                    break;
                                case "Multi-tier 3":
                                    cakePrice += 225000;
                                    break;
                                case "Multi-tier 4":
                                    cakePrice += 275000;
                                    break;
                                default:
                                    cakePrice += 0;
                            }
                            break;
                        case "45x45 cm":
                            switch (cakeTier){
                                case "Cupcake":
                                    cakePrice += 140000;
                                    break;
                                case "One tier":
                                    cakePrice += 145000;
                                    break;
                                case "Multi-tier":
                                    cakePrice += 195000;
                                    break;
                                case "Multi-tier 2":
                                    cakePrice += 195000;
                                    break;
                                case "Multi-tier 3":
                                    cakePrice += 245000;
                                    break;
                                case "Multi-tier 4":
                                    cakePrice += 295000;
                                    break;
                                default:
                                    cakePrice += 0;
                            }
                            break;
                        default:
                            cakePrice += 0;
                    }
                }

                if(addtText != null && !addtText.isEmpty()){
                    cakePrice += 20000;
                }

                if(includeLetterCard){
                   cakePrice += 8000;
                }

                Log.d("Variabel", "Cake Shape: " + cakeShape);
                Log.d("Variabel", "Cake Size: " + cakeSize);
                Log.d("Variabel", "Cake Tier: " + cakeTier);

                Log.d("Variabel", "Cake Price: " + cakePrice);

                // Create new Cake's Document Based on Generated ID
                Map<String, Object> cakeColl = new HashMap<>();
                cakeColl.put("owner", ownerName);
                cakeColl.put("imageURL", imageURL);
                cakeColl.put("likes", likes);
                cakeColl.put("cakePrice", cakePrice);
                cakeColl.put("orderID", orderID);

                db.collection("Cakes").document(cakeID)
                        .set(cakeColl)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "DocumentSnapshot successfully written: " + cakeID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("CobaData", "Error writing document", e);
                            }
                        });

                // Insert subdocument: Testimony
                Map<String, Object> testimonyObj = new HashMap<>();
                testimonyObj.put("testimonyText", testimony);
                testimonyObj.put("rating", rating);

                Map<String, Object> testimonySubdoc = new HashMap<>();
                testimonySubdoc.put("testimony", testimonyObj);

                db.collection("Cakes").document(cakeID)
                        .set(testimonySubdoc, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "Subdocument Testimony berhasil dimasukkan: " + cakeID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CobaData", "Subdocument Testimony gagal dimasukkan");
                            }
                        });

                // Insert subdocument: cake details
                Map<String, Object> cakeDetailsObj = new HashMap<>();
                cakeDetailsObj.put("cakeCategory", cakeCategory);
                cakeDetailsObj.put("cakeType", cakeType);
                cakeDetailsObj.put("cakeColor", cakeColor);
                cakeDetailsObj.put("cakeDecor", cakeDecor);
                cakeDetailsObj.put("cakeTheme", cakeTheme);
                cakeDetailsObj.put("cakeFlavor", cakeFlavor);
                cakeDetailsObj.put("cakeTier", cakeTier);
                cakeDetailsObj.put("cakeShape", cakeShape);
                cakeDetailsObj.put("cakeSize", cakeSize);
                cakeDetailsObj.put("figureURL", figureURL);
                cakeDetailsObj.put("addtText", addtText);
                cakeDetailsObj.put("includeLetterCard", includeLetterCard);
                cakeDetailsObj.put("letterMessage", letterMessage);
                cakeDetailsObj.put("specialOrders", specialOrders);

                Map<String, Object> cakeDetailsSubcoll = new HashMap<>();
                cakeDetailsSubcoll.put("CakeDetails", cakeDetailsObj);

                db.collection("Cakes").document(cakeID)
                        .set(cakeDetailsSubcoll, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "Subdocument Cake Details berhasil dimasukkan: " + cakeID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CobaData", "Subdocument Cake Details gagal dimasukkan");
                            }
                        });


                // RETRIEVE ORDER'S DATA:
                userID = fbUser.getUid();
                orderDateTime = Calendar.getInstance().getTime();
                requestDate = edtReqDate.getText().toString();

                // INSERT ORDER DATA:
                Map<String, Object> orderColl = new HashMap<>();
                orderColl.put("orderID", orderID);
                orderColl.put("userID", userID);
                orderColl.put("cakeID", cakeID);
                orderColl.put("orderDateTime", orderDateTime);
                orderColl.put("requestDate", requestDate);
                orderColl.put("cakePrice", cakePrice);
                orderColl.put("orderStatus", orderStatus);
                orderColl.put("orderName", cakeCategory + " Custom Cake");
//                orderColl.put("refKey", refKey);

                db.collection("Orders").document(orderID)
                        .set(orderColl)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CobaData", "Document ORDERS berhasil dimasukkan: " + orderID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CobaData", "Document ORDERS gagal dimasukkan");
                            }
                        });


                // GO TO NEXT PAGE
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                Intent i = new Intent( Create_order.this, Delivery.class);
                i.putExtra("cakeID", cakeID);
                i.putExtra("orderID", orderID);
                startActivity(i);
            }
        });

    }

    private void showDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtReqDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + + 3*24*60*60*1000);
        datePickerDialog.show();
    }

    private String generateRefKey(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    private View getCurrentView(ViewPager viewPager) {
        String TAG = "ViewPager";
        try {
            final int currentItem = viewPager.getCurrentItem();
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                final View child = viewPager.getChildAt(i);
                final ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) child.getLayoutParams();

                Field f = layoutParams.getClass().getDeclaredField("position"); //NoSuchFieldException
                f.setAccessible(true);
                int position = (Integer) f.get(layoutParams); //IllegalAccessException

                if (!layoutParams.isDecor && currentItem == position) {
                    return child;
                }
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    private void uploadImage() {
        Toast.makeText(Create_order.this, "Upload in progress", Toast.LENGTH_LONG).show();

        StorageReference Ref = storageReference.child(System.currentTimeMillis() + "." + getExtension(imgUri));

        uploadTask = Ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                figureURL = String.valueOf(uri);
                            }
                        });
                        Toast.makeText(Create_order.this, "Upload Image Berhasil", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
        }
    }
}