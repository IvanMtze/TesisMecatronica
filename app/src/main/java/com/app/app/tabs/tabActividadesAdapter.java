package com.app.app.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.app.app.Fragmentos.aguaConsumida;
import com.app.app.Fragmentos.alimentoConsumido;
import com.app.app.Fragmentos.mortandadFragment;

public class tabActividadesAdapter extends FragmentStatePagerAdapter {
    int counter = 3;
    String tabArray[] = new String[]{"Natalidad","Alimento","Agua"};

    public tabActividadesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabArray[position];
    }
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new mortandadFragment();
            case 1:
                return new alimentoConsumido();
            case 2:
                return new aguaConsumida();
        }
        return null;
    }

    @Override
    public int getCount() {

        return counter;
    }
}
