package umn.ac.cakehistoria.Adapters.Models;

import java.util.ArrayList;

public class VerticalModel {
    String series;
    ArrayList<HorizontalModel> arrayList;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public ArrayList<HorizontalModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HorizontalModel> arrayList) {
        this.arrayList = arrayList;
    }
}
