package com.example.fit2081assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

        super.onViewCreated(eventView, savedInstanceState);
        eventRecyclerView = eventView.findViewById(R.id.eventRecyclerView);
        eventLayoutManager = new LinearLayoutManager(getContext());
        eventRecyclerView.setLayoutManager(eventLayoutManager);
        eventAdapter = new EventAdapter();
        eventRecyclerView.setAdapter(eventAdapter);

        SharedPreferences eventSharedPreference = getActivity().getSharedPreferences("spEvent", Context.MODE_PRIVATE);
        String restoredEventString = eventSharedPreference.getString("keyEvent", "[]");

        Gson gson = new Gson();
        Type typeEvent = new TypeToken<ArrayList<EventEvent>>(){}.getType();
        listEvent = gson.fromJson(restoredEventString, typeEvent);

        if(listEvent == null){
            listEvent = new ArrayList<>();
        } else{
            eventAdapter.setData(listEvent);
            eventRecyclerView.setAdapter(eventAdapter);
        }
        return eventView;
    }

}