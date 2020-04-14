package umn.ac.cakehistoria;

import java.util.ArrayList;

public class VerticalSeries_model {

    String series;
    ArrayList<Cake_model> arrayList;

    /*public VerticalSeries_model(String series, ArrayList<Cake_model> arrayList) {
        this.series = series;
        this.arrayList = arrayList;
    }*/

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public ArrayList<Cake_model> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Cake_model> arrayList) {
        this.arrayList = arrayList;
    }
}
