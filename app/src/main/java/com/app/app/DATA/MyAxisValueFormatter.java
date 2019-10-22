package com.app.app.DATA;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class MyAxisValueFormatter implements IAxisValueFormatter {
    private String[] values;
    public MyAxisValueFormatter(String[] v){
        values = v;
    }
    public String getFormattedValue(float value, AxisBase axis) {
        return values[(int) value];
    }
}
