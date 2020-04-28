package umn.ac.cakehistoria.pagerchoice;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import umn.ac.cakehistoria.R;

public class pagerchoice_7 extends Fragment implements View.OnClickListener{
    @Nullable
    Spinner spinSize;
    ArrayAdapter< CharSequence > sizeAdapter1;
    ArrayAdapter< CharSequence > sizeAdapter2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerchoice_7, container, false);
        view.findViewById(R.id.round).setOnClickListener(this);
        view.findViewById(R.id.square).setOnClickListener(this);
        spinSize = view.findViewById(R.id.spinSize);

        sizeAdapter1 = ArrayAdapter.createFromResource(getContext(), R.array.cake_size_lists1, android.R.layout.simple_spinner_item);
        sizeAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sizeAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.cake_size_lists2, android.R.layout.simple_spinner_item);
        sizeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinSize.setAdapter(sizeAdapter1);

        return view;
    }

    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){

            case R.id.round:
//                Toast.makeText(getActivity(), "A", Toast.LENGTH_LONG ).show();
                spinSize.setAdapter(sizeAdapter1);
                break;

            case R.id.square:
//                Toast.makeText(getActivity(), "B", Toast.LENGTH_LONG ).show();
                spinSize.setAdapter(sizeAdapter2);
                break;

        }
    }
}
