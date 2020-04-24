package umn.ac.cakehistoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Create_order extends AppCompatActivity {

    Button btnUpload,btnSelect;
    ImageButton btnNext_toDelivery;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri imgUri;
    private StorageTask uploadTask;

    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        ViewPager vpPager = (ViewPager)findViewById(R.id.pagerView);
        adapterViewPager = new PagerViewAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        btnUpload = findViewById(R.id.btnUpload);
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
