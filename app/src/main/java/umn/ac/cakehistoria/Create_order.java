package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.HashMap;
import java.util.List;
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
    // Datenya belom
    CheckBox checkLetterCard;
    EditText edtLetterMessage;
    TextView txtCakePrice;
    Button btnNext;

    String ownerName = "";
    String userID = "";

    // Cake's detail
    private String cakeID = "";
    private String orderID = "This is Order ID";
    private String cakeType = "Cake Type";
    private String cakeColor = "Cake Color";
    private String cakeDecor = "Cake Decor";
    private String cakeTheme = "Cake Theme";
    private String cakeFlavor = "Cake Flavor";
    private String cakeTier = "Cake TIer";
    private String cakeShape = "Cake Shape";
    private String cakeSize = "12x12 cm";
    private String cakeCategory = "This is the category";

    private String addtText = "Addt Text";
    private String specialOrders = "Special Orders";
    private boolean includeLetterCard = false;
    private String letterMessage = "Letter Message";
    private String figureURL = "This will be the Storage's URL";
    private String requestDate = "10-10-2010";

    private String testimony = "This will be the testimony";
    private int rating = 1;
    private String testimonyID;

    private int likes = 0;
    private String imageURL = "This is cake's image URL";


    private int cakePrice = 100000;

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
        edtAdditionalText = findViewById(R.id.edtAdditionalText);
        edtSpecialOrders = findViewById(R.id.edtSpecialOrders);
        // Datenya belom
        checkLetterCard = findViewById(R.id.checkLettercard);
        edtLetterMessage = findViewById(R.id.edtLetterMessage);
        txtCakePrice = findViewById(R.id.txtCakePrice);
        btnNext_toDelivery = findViewById(R.id.btnNext_toDelivery);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();


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

                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

                            cakeTier = rb.getText().toString();

                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int selectedRbId) {
                                    RadioButton rb;
                                    rb = currView.findViewById(selectedRbId);

                                    cakeTier = rb.getText().toString();
                                    Log.d(TAG, "Cake Tier: " + cakeTier);
                                }
                            });

                            Log.d(TAG, "Cake Tier: " + cakeTier);

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

                            Log.d(TAG, "Cake Shape: " + cakeShape);
                        }
                    } else {
                        Log.d(TAG, "The View object is null");
                        Log.e(TAG, "The View object is null");
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
                Log.d("CobaData", "Cake Type: " + cakeType);
                Log.d("CobaData", "Additional Text: " + addtText);
                // RETRIEVE DATA
                // cakeCategory = spinCategory.getSelectedItem().toString();
                ownerName = fbUser.getDisplayName();
                addtText = edtAdditionalText.getText().toString();
                specialOrders = edtSpecialOrders.getText().toString();
                if (checkLetterCard.isChecked()) {
                    includeLetterCard = true;
                }
                letterMessage = edtLetterMessage.getText().toString();
                figureURL = "Figure URL";
                // requestDate = ...;
                // cakeSize = ...;

                // Create new Cake's Document Based on Generated ID
                Map<String, Object> cakeColl = new HashMap<>();
                cakeColl.put("kategori", cakeCategory);
                cakeColl.put("owner", ownerName);
                cakeColl.put("imageURL", imageURL);
                cakeColl.put("likes", likes);
                cakeColl.put("orderID", orderID);

                db.collection("Cakes")
                        .add(cakeColl)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                cakeID = documentReference.getId();

                                // Insert subdocument: Testimony
                                Map<String, Object> testimonyObj = new HashMap<>();
                                testimonyObj.put("testimonyText", testimony);
                                testimonyObj.put("rating", rating);

                                Map<String, Object> testimonySubdoc = new HashMap<>();
                                testimonySubdoc.put("testimony", testimonyObj);

                                db.collection("Cakes").document(cakeID)
                                        .set(testimonySubdoc, SetOptions.merge());

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

                                Map<String, Object> cakeDetailsSubcoll = new HashMap<>();
                                cakeDetailsSubcoll.put("CakeDetails", cakeDetailsObj);

                                db.collection("Cakes").document(cakeID)
                                        .set(cakeDetailsSubcoll, SetOptions.merge());

                                Log.d("CobaData", "Data Collection Cake berhasil masuk: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("CobaData", "Error adding document", e);
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
//                Intent i = new Intent(Create_order.this, Delivery.class);
//                startActivity(i);
            }
        });

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
