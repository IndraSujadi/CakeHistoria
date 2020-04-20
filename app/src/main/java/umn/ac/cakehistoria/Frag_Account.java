package umn.ac.cakehistoria;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class Frag_Account extends Fragment {

    TextView NamaUser, EmailUser;
    Button btnSign_Out;

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
        btnSign_Out = view.findViewById(R.id.btnSign_Out);
        mAuth = FirebaseAuth.getInstance();

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
            /*String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personID = account.getId();
            Uri personPhoto = account.getPhotoUrl();*/

            NamaUser.setText(personName);
            EmailUser.setText(personEmail);
        }
        return view;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "See you later",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });
    }
}
