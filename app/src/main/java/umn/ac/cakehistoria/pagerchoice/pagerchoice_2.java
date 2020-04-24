package umn.ac.cakehistoria.pagerchoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import umn.ac.cakehistoria.R;

public class pagerchoice_2 extends Fragment implements CompoundButton.OnCheckedChangeListener {


    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerchoice_2, container, false);
        rb1 = (RadioButton) view.findViewById(R.id.brown);
        rb1.setOnCheckedChangeListener(this);
        rb2 = (RadioButton) view.findViewById(R.id.red);
        rb2.setOnCheckedChangeListener(this);
        rb3 = (RadioButton) view.findViewById(R.id.pink);
        rb3.setOnCheckedChangeListener(this);

        rb4 = (RadioButton) view.findViewById(R.id.blue);
        rb4.setOnCheckedChangeListener(this);
        rb5 = (RadioButton) view.findViewById(R.id.green);
        rb5.setOnCheckedChangeListener(this);
        rb6 = (RadioButton) view.findViewById(R.id.rainbow);
        rb6.setOnCheckedChangeListener(this);

        rb7 = (RadioButton) view.findViewById(R.id.purple);
        rb7.setOnCheckedChangeListener(this);
        rb8 = (RadioButton) view.findViewById(R.id.creamy);
        rb8.setOnCheckedChangeListener(this);

        return view;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.brown) {
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);
                rb7.setChecked(false);
                rb8.setChecked(false);
            }
            if (buttonView.getId() == R.id.red) {
                rb1.setChecked(false);
                rb2.setChecked(true);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);
                rb7.setChecked(false);
                rb8.setChecked(false);
            }

            if (buttonView.getId() == R.id.pink) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(true);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);
                rb7.setChecked(false);
                rb8.setChecked(false);
            }

            if (buttonView.getId() == R.id.blue) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(true);
                rb5.setChecked(false);
                rb6.setChecked(false);
                rb7.setChecked(false);
                rb8.setChecked(false);
            }

            if (buttonView.getId() == R.id.green) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(true);
                rb6.setChecked(false);
                rb7.setChecked(false);
                rb8.setChecked(false);
            }

            if (buttonView.getId() == R.id.rainbow) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(true);
                rb7.setChecked(false);
                rb8.setChecked(false);
            }

            if (buttonView.getId() == R.id.purple) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);
                rb7.setChecked(true);
                rb8.setChecked(false);
            }

            if (buttonView.getId() == R.id.creamy) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);
                rb7.setChecked(false);
                rb8.setChecked(true);
            }


        }
    }



}


