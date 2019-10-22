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
import com.app.app.DATA.DAO.AlimentoDao;
import com.app.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class alimentoConsumido extends Fragment implements View.OnClickListener {

    View view;
    BDHelper baseInstance = BDHelper.getInstance(getActivity());
    public alimentoConsumido() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(baseInstance.existeActividad("alimento")){
            view = inflater.inflate(R.layout.done,container,false);
        }
        else {
            view = inflater.inflate(R.layout.fragment_alimento_consumido, container, false);
            Button btn = (Button) view.findViewById(R.id.alimentoConsumidoBTN);
            btn.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        EditText txt = (EditText) view.findViewById(R.id.alimentoTEXT);
        AlimentoDao dao = new AlimentoDao(getActivity());
        dao.insertarAlimento(Double.parseDouble(txt.getText().toString()),getActivity());

    }
}
