package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class likes extends AppCompatActivity {

    ImageView imgBack_Likes;

    RecyclerView recyclerLikes;

    FirebaseFirestore DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        DB = FirebaseFirestore.getInstance();

        imgBack_Likes = findViewById(R.id.imgBack_likes);
        recyclerLikes = findViewById(R.id.recyclerLikes);

        imgBack_Likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
