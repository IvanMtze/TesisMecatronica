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
import com.app.app.DATA.DAO.PollosDAO;
import com.app.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class mortandadFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText txt;
    BDHelper baseInstance = BDHelper.getInstance(getActivity());
    public mortandadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(baseInstance.existeActividad("pollos")){
            view = inflater.inflate(R.layout.done,container,false);
        }

        else{
            view =  inflater.inflate(R.layout.fragment_mortandad, container, false);
            Button btn = (Button)view.findViewById(R.id.button3);
            txt = (EditText)view.findViewById(R.id.mortandadTXT);
            btn.setOnClickListener(this);
        }

        return view;
    }
    @Override
    public void onClick(View v) {
        PollosDAO dao = new PollosDAO(getActivity());
        dao.insertarPollos(Double.parseDouble(txt.getText().toString()),getActivity());
        Toast.makeText(getActivity(),"GUARDADO",Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "Mortandad Guardada", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "ACTUALIZADA", Toast.LENGTH_SHORT).show();
    }
}
