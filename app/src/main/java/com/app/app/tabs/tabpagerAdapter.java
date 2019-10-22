package com.app.app.tabs;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.app.tabs.fragments.*;

public class tabpagerAdapter extends FragmentStatePagerAdapter {
    public tabpagerAdapter(FragmentManager fm) {
        super(fm);
    }
    String[] tabArray = new String[]{"Semanal","Mensual"};
    int counter = 2;
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                graficaSemanal g = new graficaSemanal();
                return g;
            case 1:
                graficaMensual k = new graficaMensual();
                return k;
        }
        return null;
    }

    @Override
    public int getCount() {
        return counter;
    }
}
