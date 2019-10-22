package com.app.app.Fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.app.R;
import com.app.app.tabs.tabpagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Resumen extends Fragment {

    public Resumen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_resumen, container, false);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.r);
        PagerAdapter adapter = new tabpagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabs = (TabLayout)view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return view;
    }
}
