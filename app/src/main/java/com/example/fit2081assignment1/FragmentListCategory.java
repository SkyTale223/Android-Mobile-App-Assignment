package com.example.fit2081assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit2081assignment1.CategoryRecyclerAdapter;
import com.example.fit2081assignment1.EventCategory;
import com.example.fit2081assignment1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentListCategory extends Fragment {
    ArrayList<EventCategory> listCategory = new ArrayList<>();

    // Initialize RecyclerView adapter with the dataset
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    RecyclerView categoryRecyclerView;
    RecyclerView.LayoutManager categoryLayoutManager;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences("CATEGORY_INFORMATION", Context.MODE_PRIVATE);

        String tempRestored = sharedPreferences.getString("CATEGORIES", "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<EventCategory>>() {}.getType();
        listCategory = gson.fromJson(tempRestored,type);


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentListCategory fragment = new FragmentListCategory();
        fragmentTransaction.replace(R.id.fragmentcatlist, fragment);
        fragmentTransaction.commit();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        // Initialize RecyclerView and set its layout manager
        categoryRecyclerView = view.findViewById(R.id.recyclerView);
        categoryLayoutManager = new LinearLayoutManager(requireContext());
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);

        // Set RecyclerView adapter
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);

        return view;
    }

    public void refresh() {
        if (categoryRecyclerAdapter != null) {
            categoryRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
