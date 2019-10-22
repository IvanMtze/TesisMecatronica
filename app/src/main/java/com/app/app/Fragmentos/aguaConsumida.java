package com.app.app.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.DAO.AguaDao;
import com.app.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class aguaConsumida extends Fragment implements View.OnClickListener {

    View view;
    BDHelper baseInstance = BDHelper.getInstance(getActivity());

    public aguaConsumida() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(baseInstance.existeActividad("agua")){
            view = inflater.inflate(R.layout.done,container,false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_agua_consumida, container, false);
            Button b = (Button) view.findViewById(R.id.button5);
            b.setOnClickListener(this);
        }
        return view;
    }

    public void save(){
        EditText txt = (EditText) view.findViewById(R.id.aguaTXT);
        AguaDao dao = new AguaDao(getActivity());
        dao.insertarAgua(Double.parseDouble(txt.getText().toString()),getActivity());
    }

    @Override
    public void onClick(View v) {
        save();
    }
}
