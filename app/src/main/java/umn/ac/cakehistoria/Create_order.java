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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Field;
import java.util.List;

import umn.ac.cakehistoria.pagerchoice.pagerchoice_adapter;

public class Create_order extends AppCompatActivity {

    Button btnUpload,btnSelect;
    ImageButton btnNext_toDelivery;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri imgUri;
    private StorageTask uploadTask;

    FragmentPagerAdapter adapterViewPager;

    public View currView;

    // Public variables that used as data that will written into database:
    String cakeType;
    String cakeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        ViewPager vpPager = (ViewPager)findViewById(R.id.pagerView);
        adapterViewPager = new PagerViewAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        ViewPager vpPager2 = (ViewPager)findViewById(R.id.pagerChoice);
        adapterViewPager = new pagerchoice_adapter(getSupportFragmentManager());
        vpPager2.setAdapter(adapterViewPager);

        // -------------------- GET VIEWPAGER DATA ---------------------------
        vpPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String TAG = "ViewPager";
                if (positionOffset >= 0) {
                    currView = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pagerChoice + ":" + position).getView();
                    if (currView != null) {
                        Log.d(TAG, "The View object has already catched. Now get the layout instance(s)");
                        if (position == 0) {
                            RadioGroup rg;
                            rg = currView.findViewById(R.id.type);

                            RadioButton rb;

                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

                            cakeType = rb.getText().toString();
                            Log.d(TAG, "Cake Type: " + cakeType);
                        }
                        else if (position == 1) {
//                            textView = currView.findViewById(R.id.txtColor);
                            RadioGroup rg;
                            rg = currView.findViewById(R.id.rgColor);

                            RadioButton rb;

                            int selectedRbId = rg.getCheckedRadioButtonId();
                            rb = currView.findViewById(selectedRbId);

//                            masih error getText nya, masih null.
//                            cakeColor = rb.getText().toString();
//                            Log.d(TAG, "Cake Type: " + cakeColor);
                        }
                        else if (position ==2) {
//                            textView = currView.findViewById(R.id.textDecor);
                        }
                        else if (position ==3) {
//                            textView = currView.findViewById(R.id.txtTheme);
                        }
                        else if (position ==4) {
//                            textView = currView.findViewById(R.id.txtFlavor);
                        }
                        else if (position ==5) {
//                            textView = currView.findViewById(R.id.txtTier);
                        }
                        else if (position ==6) {
//                            textView = currView.findViewById(R.id.txtShape);
                        }
                    }else {
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


//        FragmentManager fragManager = getSupportFragmentManager();
//        FragmentTransaction fragTransaction = fragManager.beginTransaction();

        int item = vpPager2.getCurrentItem();
        Fragment fragPagerChoice = adapterViewPager.getItem(item);



//        Toast.makeText(getApplicationContext(), String.valueOf(view), Toast.LENGTH_LONG).show();
//        fragTransaction.add(R.id.pagerChoice, fragPagerChoice);
//        fragTransaction.commit();

//        TextView coba = findViewById(R.id.txtCakeType);
//        Toast.makeText(getApplicationContext(), coba.getText(), Toast.LENGTH_LONG).show();

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
                if(imgUri == null){
                    Toast.makeText(Create_order.this,"Silahkan Pilih Gambar yang ingin di Upload",Toast.LENGTH_LONG).show();
                } else if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(Create_order.this,"Upload in progress",Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });

        btnNext_toDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Create_order.this, Delivery.class);
                startActivity(i);
            }
        });

    }

    private View getCurrentView(ViewPager viewPager) {
        String TAG = "View_Pager";
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
        Toast.makeText(Create_order.this,"Upload in progress",Toast.LENGTH_LONG).show();

        StorageReference Ref = storageReference.child(System.currentTimeMillis()+"."+getExtension(imgUri));

        uploadTask = Ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        Toast.makeText(Create_order.this,"Upload Image Berhasil",Toast.LENGTH_SHORT).show();
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

    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void  selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
        }
    }
}
