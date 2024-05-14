package com.example.fit2081assignment1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentListCategory extends Fragment {
    ArrayList<EventCategory> listCategory = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView;
    LayoutManager categoryLayoutManager;
    EMAViewmodel emaViewModel;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListCategory() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View categoryView = inflater.inflate(R.layout.fragment_list_category, container, false);
        emaViewModel = new ViewModelProvider(this).get(EMAViewmodel.class);


        super.onViewCreated(categoryView, savedInstanceState);
        // Finding view of category first
        categoryRecyclerView = categoryView.findViewById(R.id.categoryRecyclerView);
        // Creating new instance of lot manager and passing the current fragment context
        categoryLayoutManager = new LinearLayoutManager(getContext());
        // Setting the recycler view to the category layout manager
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        // Creating an instance of Category Adapter here
        categoryAdapter = new CategoryAdapter();
        // Setting the recycler views adapter to the instance created
        categoryRecyclerView.setAdapter(categoryAdapter);
        emaViewModel.getAllEventCategoryLiveData().observe(getViewLifecycleOwner(), newData ->{
            categoryAdapter.setData(new ArrayList<EventCategory>(newData));
            categoryAdapter.notifyDataSetChanged();
        });





        return categoryView;
    }
}

