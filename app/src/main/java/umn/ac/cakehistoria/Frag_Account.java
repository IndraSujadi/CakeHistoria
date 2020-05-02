package umn.ac.cakehistoria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class Frag_Account extends Fragment {

    TextView NamaUser, EmailUser;
    Button btnSign_Out;
    //ImageButton btnFav_Acc, btnHistory_Acc;
    ImageView imgAcc;

    FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_frag_account, container, false);
        // coding sini
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);

        NamaUser = view.findViewById(R.id.NamaUser);
        EmailUser = view.findViewById(R.id.EmailUser);
        imgAcc = view.findViewById(R.id.imgAcc);
        btnSign_Out = view.findViewById(R.id.btnSign_Out);
        //btnFav_Acc = view.findViewById(R.id.btnFav_Acc);
        //btnHistory_Acc = view.findViewById(R.id.btnHistory_Acc);
        mAuth = FirebaseAuth.getInstance();

        /*btnFav_Acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), likes.class);
                startActivity(i);
            }
        });

        btnHistory_Acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Transactions.class);
                startActivity(i);
            }
        });*/

        btnSign_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personID = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            NamaUser.setText(personName);
            EmailUser.setText(personEmail);
            Picasso.get()
                    .load(personPhoto).into(imgAcc);
        }
        return view;

    }

    private void signOut() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you want to leave?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "See you later",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getActivity(), Login_Activity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                getActivity().finish();
                            }
                        });

                mAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(),
                        Login_Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                getActivity().finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
