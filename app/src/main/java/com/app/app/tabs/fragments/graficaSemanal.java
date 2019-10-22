package com.app.app.tabs.fragments;


import android.app.Activity;
import android.content.Context;
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
public class graficaSemanal extends Fragment {

    View view;
    private final String[] semana =  new String[]{"Dom","Lun","Mar","Mie","Jue","Vie","Sab"};
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    BDHelper baseInstance;
    public graficaSemanal() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //baseInstance = new BDHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //BDHelper.getInstance(getActivity()).eliminarTodo();

        baseInstance = BDHelper.getInstance(getActivity());
        view= inflater.inflate(R.layout.fragment_grafica_semanal, container, false);
        //baseInstance = BDHelper.getInstance(getContext());
        createLinearChartWithEntries(R.id.polloSemanal,entradasGraficas("pollos"),semana,"Num. Pollos","Dato por dia de la semana");
        createLinearChartWithEntries(R.id.pesoSemanal,entradasGraficas("peso"),semana, "Peso Promedio","Dato por dia de la semana");
        createLinearChartWithEntries(R.id.consumoSemanal, entradasGraficas("agua"), semana,"Consumo de Agua","Dato por dia de la semana");
        createLinearChartWithEntries(R.id.alimentoSemanal, entradasGraficas("alimento"),semana, "Alimento Consumido","Dato por dia de la semana");
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
                return  XLabels[(int)value-2];
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

    /**
     * @param dia
     * @return
     */
    //DONE
    private boolean estaEnEstaSemana(Date dia){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        int numeroDeSemanaActual = calendario.get(Calendar.WEEK_OF_YEAR);
        calendario.setTime(dia);
        int numeroSemanaBD = calendario.get(Calendar.WEEK_OF_YEAR);
        if(numeroDeSemanaActual == numeroSemanaBD){
            return true;
        }
        return false;
    }

    /**
     * @param nombre
     * @return
     */
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
                PollosDAO dao = new PollosDAO(getActivity());
                arreglo = dao.getNumPollos();
                break;
            case "peso":
                PesoDAO pesoDAO = new PesoDAO(getActivity());
                arreglo = pesoDAO.getPesoPromedio();
                break;
        }
        ArrayList<Integer> dias = new ArrayList<>();
        Date dia = new Date();
        int mes = dia.getMonth();
        Calendar c = Calendar.getInstance();
        for(Pair p :arreglo){
            Date d = null;
            try{
                d = dateFormat.parse(p.getRight().toString());}
            catch(ParseException e){

            }
            if (estaEnEstaSemana(d)){
                c.setTime(d);
                dias.add(c.get(Calendar.DAY_OF_WEEK));
            }
        }
        Collections.sort(dias);
        String[] semana = {"Dom","Lun","Mar","Mie","Jue","Vie","Sab"};
        ArrayList<String> semA = new ArrayList<>();
        for(int i = 0; i < dias.size(); i++){
            semA.add(semana[dias.get(i)-1]);
        }
        String[] a = new String[semA.size()];
        for(int i=0; i<semA.size();i++){
            a[i] = semA.get(i);
            System.out.println(a[i]);
        }
        return a;
    }

    /**
     * @param nombre A string whit the name of the table of the database where you are looking for
     * @return an ArrayList of entry that contains the entrys for the given tipe
     */
    private ArrayList<Entry> entradasGraficas(String nombre){
        ArrayList<Entry> entradasGraficas = new ArrayList<>();
        ArrayList<Pair> arreglo = null;
        switch (nombre){
            case "alimento":
                AlimentoDao alimentoDao = new AlimentoDao(getActivity());
                arreglo = alimentoDao.getAlimento();
                break;
            case "agua":
                AguaDao aguaDao = new AguaDao(getActivity());
                arreglo = aguaDao.getAguaToda();
                break;
            case "pollos":
                PollosDAO dao = new PollosDAO(getActivity());
                arreglo = dao.getNumPollos();
                break;
            case "peso":
                PesoDAO pesoDAO = new PesoDAO(getActivity());
                arreglo = pesoDAO.getPesoPromedio();
                break;
        }
        for(Pair p : arreglo){
            Date d = null;
            try{
                d = dateFormat.parse(p.getRight().toString());}
            catch(ParseException e){
                Toast t = Toast.makeText(getContext(),"PARSE ERROR",Toast.LENGTH_SHORT);
                t.show();
            }
                if(estaEnEstaSemana(d)){
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    System.out.println(d+"+dia"+c.get(Calendar.DAY_OF_WEEK)+" "+ (Float) p.getLeft());
                    entradasGraficas.add(new Entry(c.get(Calendar.DAY_OF_WEEK), (Float) p.getLeft()));
                }
        }
        return entradasGraficas;
    }
}
