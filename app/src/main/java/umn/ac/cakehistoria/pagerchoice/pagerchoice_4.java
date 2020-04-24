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

public class pagerchoice_4 extends Fragment implements CompoundButton.OnCheckedChangeListener {

    RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerchoice_4, container, false);
        rb1 = (RadioButton) view.findViewById(R.id.sprinkle);
        rb1.setOnCheckedChangeListener(this);
        rb2 = (RadioButton) view.findViewById(R.id.flourish);
        rb2.setOnCheckedChangeListener(this);
        rb3 = (RadioButton) view.findViewById(R.id.kids);
        rb3.setOnCheckedChangeListener(this);

        rb4 = (RadioButton) view.findViewById(R.id.fruity);
        rb4.setOnCheckedChangeListener(this);
        rb5 = (RadioButton) view.findViewById(R.id.romance);
        rb5.setOnCheckedChangeListener(this);
        rb6 = (RadioButton) view.findViewById(R.id.youth);
        rb6.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.sprinkle) {
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);

            }
            if (buttonView.getId() == R.id.flourish) {
                rb1.setChecked(false);
                rb2.setChecked(true);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);

            }

            if (buttonView.getId() == R.id.kids) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(true);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);

            }

            if (buttonView.getId() == R.id.fruity) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(true);
                rb5.setChecked(false);
                rb6.setChecked(false);

            }

            if (buttonView.getId() == R.id.romance) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(true);
                rb6.setChecked(false);

            }

            if (buttonView.getId() == R.id.youth) {
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(true);

            }

        }
    }
}
