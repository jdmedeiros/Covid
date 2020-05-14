package com.example.covid.ui.testes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.covid.R;
import com.example.covid.Resposta;

import java.util.ArrayList;
import java.util.List;

import static com.example.covid.OkHttpHandler.dadosCovid;

public class TestesFragment extends Fragment {

    private TestesViewModel testesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testesViewModel =
                ViewModelProviders.of(this).get(TestesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testes, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);


        TextView testesTotal = root.findViewById(R.id.testesTotal);
        TextView testesData = root.findViewById(R.id.testesData);
        TextView testesHora = root.findViewById(R.id.testesHora);
        Spinner spinnerC = root.findViewById(R.id.spinnerTestes);

        spinnerC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testesTotal.setText(String.valueOf(dadosCovid.get(parent.getSelectedItemPosition()).getTests().getTotal()));
                testesData.setText(dadosCovid.get(parent.getSelectedItemPosition()).getDay());
                testesHora.setText(dadosCovid.get(parent.getSelectedItemPosition()).getTime().substring(11));
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });





        testesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        testesViewModel.getOsDadosCovid().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List osDadosCovid) {
                ArrayList<String> countries = new ArrayList<>();
                for (Object dado : osDadosCovid) {
                    Resposta temp = (Resposta) dado;
                    countries.add((temp.getCountry()));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, countries);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerC.setAdapter(arrayAdapter);
            }
        });

        return root;
    }
}
