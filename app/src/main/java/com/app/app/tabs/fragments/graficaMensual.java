package com.app.app.tabs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.app.DATA.BaseData.BDHelper;
import com.app.app.DATA.DAO.AguaDao;
import com.app.app.DATA.DAO.AlimentoDao;
import com.app.app.DATA.DAO.PesoDAO;
import com.app.app.DATA.DAO.PollosDAO;
import com.app.app.DATA.Pair;
import com.app.app.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class graficaMensual extends Fragment {

    View view;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public graficaMensual() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grafica_mensual, container, false);
        createLinearChartWithEntries(R.id.polloMensual,entradasGraficas("pollos"), getDias("pollos"),"Num. Pollos","Dato por dia del mes" );
        createLinearChartWithEntries(R.id.pesoMensual,entradasGraficas("peso"),getDias("peso"), "Peso Promedio","Dato por dia del mes" );
        createLinearChartWithEntries(R.id.consumoMensual, entradasGraficas("agua"), getDias("agua"),"Consumo de Agua","Dato por dia del mes" );
        createLinearChartWithEntries(R.id.alimentoMensual, entradasGraficas("alimento"), getDias("alimento"), "Alimento Consumido","Dato por dia del mes" );
        return view;
    }
    private void createLinearChartWithEntries(int xmlView, ArrayList<Entry> dataEntries, final String[] XLabels, String description, String Label){

        Description des = new Description();
        des.setText(description);
        final LineChart chart = view.findViewById(xmlView);
        System.out.println("mes ; " +XLabels.length);
        chart.setDescription(des);
        LineDataSet dataSet = new LineDataSet(dataEntries, Label);
        dataSet.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        dataSet.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Customizing x axis value

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // return the string va
                return  String.valueOf((int)value);
            }
        });

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        //xAxis.setValueFormatter(formatter);


        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Setting Data
        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.animateX(1000);
        chart.setDrawGridBackground(false);
        //refresh
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                                                  @Override
                                                  public void onValueSelected(Entry e, Highlight h) {
                                                      Log.i("Entry selected", e.toString());
                                                      Toast.makeText(getContext(),"Dia: " + (int)e.getX() + " Valor: " + e.getY(),Toast.LENGTH_SHORT).show();
                                                      chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex()).getAxisDependency(), 1000);
                                                  }

                                                  @Override
                                                  public void onNothingSelected() {
                                                  }
                                              }
        );
    }

    private ArrayList<Entry> entradasGraficas(String nombre){
        ArrayList<Entry> entradasGraficas = new ArrayList<>();
        ArrayList<Pair> arreglo = null;
        switch (nombre){
            case "alimento":
                AlimentoDao alimentoDao = new AlimentoDao(getActivity());
                arreglo = alimentoDao.getAlimento();
                break;
            case "agua":
                AguaDao daoAgua = new AguaDao(getActivity());
                arreglo = daoAgua.getAguaToda();
                break;
            case "pollos":
                PollosDAO daoPollos = new PollosDAO(getActivity());
                arreglo = daoPollos.getNumPollos();
                break;
            case "peso":
                PesoDAO daoPeso = new PesoDAO(getActivity());
                arreglo = daoPeso.getPesoPromedio();
                break;
        }
        int i = 0;
        Calendar c = Calendar.getInstance();
        for(Pair p : arreglo){
            Date d = null;
            try{
                d = dateFormat.parse(p.getRight().toString());}
            catch(ParseException e){
                Toast t = Toast.makeText(getContext(),"PARSE ERROR",Toast.LENGTH_SHORT);
                t.show();
            }
            if(estaEnEsteMes(d)){
                c.setTime(d);
                entradasGraficas.add(new Entry(c.get(Calendar.DAY_OF_MONTH), (Float) p.getLeft()));
                i++;
            }
        }
        return entradasGraficas;
    }
    private boolean estaEnEsteMes(Date dia){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        int numeroMesActual = calendario.get(Calendar.MONTH);
        calendario.setTime(dia);
        int numeroMesBD = calendario.get(Calendar.MONTH);
        if(numeroMesActual == numeroMesActual){
            return true;
        }
        return false;
    }
    private String[] getDias(String nombre) {
        ArrayList<Pair> arreglo = null;
        switch (nombre) {
            case "alimento":
                AlimentoDao alimentoDao = new AlimentoDao(getActivity());
                arreglo = alimentoDao.getAlimento();
                break;
            case "agua":
                AguaDao aguaDao = new AguaDao(getActivity());
                arreglo = aguaDao.getAguaToda();
                break;
            case "pollos":
                PollosDAO pollosDAO = new PollosDAO(getActivity());
                arreglo = pollosDAO.getNumPollos();
                break;
            case "peso":
                PesoDAO pesoDAO = new PesoDAO(getActivity());
                arreglo = pesoDAO.getPesoPromedio();
                break;
        }
        ArrayList<Integer> dias = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for(Pair p :arreglo){
            Date d = null;
            try{
                d = dateFormat.parse(p.getRight().toString());}
            catch(ParseException e){

            }
            if (estaEnEsteMes(d)){
                c.setTime(d);
                dias.add(c.get(Calendar.DAY_OF_MONTH));
            }
        }
        Collections.sort(dias);
        String[] a = new String[dias.size()];
        for(int  i=0 ; i<dias.size(); i++){
            a[i] = String.valueOf(dias.get(i));
        }
        return a;
    }

}