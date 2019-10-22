package com.app.app.Fragmentos;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.app.R;
import com.app.app.tabs.tabActividadesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActDiarias extends Fragment {


    public ActDiarias() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_act_diarias, container, false);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.tabActividadesDiarias);
        PagerAdapter adapter = new tabActividadesAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabs = (TabLayout)view.findViewById(R.id.tabActividades);
        tabs.setupWithViewPager(viewPager);
        return view;
    }

}
