package com.example.contactsavertest2.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.contactsavertest2.MainActivity;
import com.example.contactsavertest2.R;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private List<Integer> transferList;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_details, container, false);
        populateSpinners(view);
        return view;


    }

    private void populateSpinners(View view)
    {
        MainActivity main = (MainActivity) getActivity();
        transferList = new ArrayList<Integer>();
        transferList.add(0);
        transferList.add(0);
        transferList.add(0);
        transferList.add(0);
        transferList.add(0);
        transferList.add(0);
        transferList.add(2023);
        transferList.add(0);
        main.setTransferList(transferList);

        ArrayList<String> years = new ArrayList<String>();
        for (int i = 2022; i <= 2035; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, years);

        Spinner spinYear = (Spinner)view.findViewById(R.id.Yearspinner);
        spinYear.setAdapter(adapter);
        spinYear.setSelection(1);

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(6, Integer.parseInt( spinYear.getSelectedItem().toString()));
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayList<String> sems = new ArrayList<String>();
        sems.add("Summer");
        sems.add("Winter");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, sems);
        Spinner spinSems = (Spinner)view.findViewById(R.id.Semesterspinner);
        spinSems.setAdapter(adapter2);
        spinSems.setSelection(0);

        spinSems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(5, spinSems.getSelectedItemPosition());
                main.setTransferList(transferList);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        ArrayList<Character> Columns = new ArrayList<Character>();
        for (char i = 'A'; i <= 'Z'; i++) {
            Columns.add(i);
        }

        ArrayAdapter<Character> adapter3 = new ArrayAdapter<Character>(this.getContext(), android.R.layout.simple_spinner_item, Columns);
        Spinner spinFName = (Spinner)view.findViewById(R.id.FNamespinner);
        spinFName.setAdapter(adapter3);

        spinFName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(0, spinFName.getSelectedItemPosition());
                main.setTransferList(transferList);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner spinLName = (Spinner)view.findViewById(R.id.LNamespinner);
        spinLName.setAdapter(adapter3);

        spinLName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(1, spinLName.getSelectedItemPosition());
                main.setTransferList(transferList);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner spinDegree = (Spinner)view.findViewById(R.id.Degreespinner);
        spinDegree.setAdapter(adapter3);

        spinDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(2, spinDegree.getSelectedItemPosition());
                main.setTransferList(transferList);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner spinCountry = (Spinner)view.findViewById(R.id.Countryspinner);
        spinCountry.setAdapter(adapter3);

        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(3, spinCountry.getSelectedItemPosition());
                main.setTransferList(transferList);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner spinPhNumber = (Spinner)view.findViewById(R.id.Numberspinner);
        spinPhNumber.setAdapter(adapter3);

        spinPhNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transferList.set(4, spinPhNumber.getSelectedItemPosition());
                main.setTransferList(transferList);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton:
                        transferList.set(7, 1);
                        main.setTransferList(transferList);
                        break;
                    case R.id.radioButton2:
                        transferList.set(7, 0);
                        main.setTransferList(transferList);

                        break;

                }
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // TODO: Use the ViewModel
    }

}