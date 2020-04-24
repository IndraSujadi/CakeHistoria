package umn.ac.cakehistoria.pagerchoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import umn.ac.cakehistoria.R;

public class pagerchoice_6 extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerchoice_6, container, false);
        view.findViewById(R.id.one).setOnClickListener(this);
        view.findViewById(R.id.multi).setOnClickListener(this);
        view.findViewById(R.id.cupcake).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){

            case R.id.one:
                Toast.makeText(getActivity(), "A", Toast.LENGTH_LONG ).show();
                break;

            case R.id.multi:
                Toast.makeText(getActivity(), "B", Toast.LENGTH_LONG ).show();
                break;

            case R.id.cupcake:
                Toast.makeText(getActivity(), "C", Toast.LENGTH_LONG ).show();
                break;
        }
    }
}
