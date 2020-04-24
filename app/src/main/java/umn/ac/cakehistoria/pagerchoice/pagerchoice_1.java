package umn.ac.cakehistoria.pagerchoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import umn.ac.cakehistoria.R;

public class pagerchoice_1 extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerchoice_1, container, false);

        view.findViewById(R.id.sponge).setOnClickListener(this);
        view.findViewById(R.id.butter).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){

            case R.id.sponge:
                Toast.makeText(getActivity(), "A", Toast.LENGTH_LONG ).show();
                break;

            case R.id.butter:
                Toast.makeText(getActivity(), "B", Toast.LENGTH_LONG ).show();
                break;
        }
    }



}
