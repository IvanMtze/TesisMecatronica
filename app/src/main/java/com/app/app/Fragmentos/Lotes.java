package com.app.app.Fragmentos;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.app.app.DATA.SALIDA.POST_PESO;
import com.app.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lotes extends Fragment implements View.OnClickListener {
    View view;
    EditText txt ;
    BDHelper baseInstance = BDHelper.getInstance(getActivity());

    public Lotes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_lotes, container, false);
        Button btn = (Button)view.findViewById(R.id.button2);
        txt = (EditText)view.findViewById(R.id.lotetxt);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        final Double lote = Double.parseDouble(txt.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta accion borrara toda la informacion hasta ahora.¿Desea continuar?");
        builder.setTitle("Confirmación");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baseInstance.eliminarTodo();
                PollosDAO daoPollos = new PollosDAO(getActivity());
                daoPollos.insertarPollos(lote,getActivity());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"CANCELADO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        AlertDialog dialogo = builder.create();
        dialogo.show();
    }
}
