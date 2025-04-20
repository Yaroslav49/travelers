package ru.rsreu.travelers.datalayer.data;

import ru.rsreu.travelers.datalayer.Trip;

import java.util.List;

public class ListTrip {
    private List<Trip> data;
    private String name;

    public ListTrip(String name, List<Trip> data) {
        this.data = data;
        this.name = name;
    }

    public List<Trip> getData() {
        return data;
    }

    public void setData(List<Trip> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
