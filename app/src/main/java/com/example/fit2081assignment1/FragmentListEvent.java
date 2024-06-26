package com.example.fit2081assignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit2081assignment1.provider.EMAViewmodel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {
    ArrayList<EventEvent> listEvent = new ArrayList<>();
    EventAdapter eventAdapter;
    RecyclerView eventRecyclerView;
    RecyclerView.LayoutManager eventLayoutManager;
    EMAViewmodel emaViewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_list_event.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
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
        View eventView = inflater.inflate(R.layout.fragment_list_event, container, false);

        // Initialize your EMAViewmodel here
        emaViewModel = new ViewModelProvider(this).get(EMAViewmodel.class);

        // Finding view of category first
        eventRecyclerView = eventView.findViewById(R.id.eventRecyclerView);
        // Creating new instance of lot manager and passing the current fragment context
        eventLayoutManager = new LinearLayoutManager(getContext());
        // Setting the recycler view to the category layout manager
        eventRecyclerView.setLayoutManager(eventLayoutManager);
        // Creating an instance of Category Adapter here
        eventAdapter = new EventAdapter();
        // Setting the recycler views adapter to the instance created
        eventRecyclerView.setAdapter(eventAdapter);

        // Observe the LiveData only after the ViewModel is properly initialized
        emaViewModel.getAllEventEventLiveData().observe(getViewLifecycleOwner(), newData -> {
            eventAdapter.setData(new ArrayList<EventEvent>(newData));
            eventAdapter.notifyDataSetChanged();
        });

        return eventView;
    }
}
