package umn.ac.cakehistoria.pagerchoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import umn.ac.cakehistoria.R;

public class pagerchoice_5 extends Fragment implements CompoundButton.OnCheckedChangeListener {

    RadioButton rb1, rb2, rb3, rb4, rb5;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerchoice_5, container, false);
        rb1 = (RadioButton) view.findViewById(R.id.chocolate);
        rb1.setChecked(true);
        rb1.setOnCheckedChangeListener(this);
        rb2 = (RadioButton) view.findViewById(R.id.strawberry);
        rb2.setOnCheckedChangeListener(this);
        rb3 = (RadioButton) view.findViewById(R.id.cheese);
        rb3.setOnCheckedChangeListener(this);

        rb4 = (RadioButton) view.findViewById(R.id.cappucino);
        rb4.setOnCheckedChangeListener(this);
        rb5 = (RadioButton) view.findViewById(R.id.green_tea);
        rb5.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.chocolate) {
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);


            }
            if (buttonView.getId() == R.id.strawberry) {
                rb1.setChecked(false);
                rb2.setChecked(true);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);


            }

            if (buttonView.getId() == R.id.cheese) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(true);
                rb4.setChecked(false);
                rb5.setChecked(false);


            }

            if (buttonView.getId() == R.id.cappucino) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(true);
                rb5.setChecked(false);


            }

            if (buttonView.getId() == R.id.green_tea) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(true);


            }

        }
    }
}
