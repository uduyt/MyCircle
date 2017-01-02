package com.mycircle.NearMe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycircle.Person;
import com.mycircle.PersonLab;
import com.mycircle.R;


public class FragmentNearMeList extends Fragment{

    private RecyclerView rvPersons;
    public FragmentNearMeList() {
        // Required empty public constructor
    }

    public static FragmentNearMeList newInstance() {
        FragmentNearMeList fragment = new FragmentNearMeList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_near_me_list, container, false);

        rvPersons = (RecyclerView) rootView.findViewById(R.id.rv_persons);

        Person.PersonAdapter adapter = new Person.PersonAdapter(PersonLab.getPersons());

        rvPersons.setAdapter(adapter);

        rvPersons.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
