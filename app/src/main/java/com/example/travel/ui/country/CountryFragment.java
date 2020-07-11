package com.example.travel.ui.country;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travel.CountryAdapter;
import com.example.travel.CountryItem;
import com.example.travel.R;

import java.util.ArrayList;

public class CountryFragment extends Fragment {

    private ArrayList<CountryItem> countryItems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_country, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new CountryAdapter(countryItems, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        countryItems.add(new CountryItem(R.drawable.sri, "Sri Lanka","0","0"));
        countryItems.add(new CountryItem(R.drawable.italy, "Italy","1","0"));
        countryItems.add(new CountryItem(R.drawable.nz, "New Zealand","3","0"));
        countryItems.add(new CountryItem(R.drawable.chi, "China","4","0"));
        countryItems.add(new CountryItem(R.drawable.ame, "America","5","0"));
        countryItems.add(new CountryItem(R.drawable.jap, "Japan","6","0"));
        countryItems.add(new CountryItem(R.drawable.eng, "England","7","0"));
        countryItems.add(new CountryItem(R.drawable.indu, "Indonesia","8","0"));
        countryItems.add(new CountryItem(R.drawable.aus, "Australia","9","0"));



        return root;
    }
}