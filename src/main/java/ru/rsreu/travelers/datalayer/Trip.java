package ru.rsreu.travelers.datalayer;


/**
 * Represents a trip with various attributes related to its details, including start and end points, date and time, passenger information, driver details, and the state of the trip.
 * Used to display the trip.
 */
public class Trip {
    private int id;
    private String startPoint;
    private String finalPoint;
    private String dateTime;
    private int numberPassengers;
    private int numberSeats;
    private int price;
    private String driverName;
    private int driverSumMarks;
    private int driverCountMarks;

    private int state;
    private String collectionPoint;
    private String description;

    public Trip() {
        this.id = -1;
        this.startPoint = "";
        this.finalPoint = "";
        this.dateTime = "";
        this.numberPassengers = 0;
        this.numberSeats = 0;
        this.price = 0;
        this.driverName = "";
        this.driverSumMarks = 0;
        this.driverCountMarks = 1;
    }

    public Trip(int id, String startPoint, String finalPoint, String dateTime, int numberPassengers, int numberSeats, int price, String driverName, int driverSumMarks, int driverCountMarks, int state) {
        this.id = id;
        this.startPoint = startPoint;
        this.finalPoint = finalPoint;
        this.dateTime = dateTime;
        this.numberPassengers = numberPassengers;
        this.numberSeats = numberSeats;
        this.price = price;
        this.driverName = driverName;
        this.driverSumMarks = driverSumMarks;
        this.driverCountMarks = driverCountMarks;
        this.state = state;
        this.collectionPoint = "";
        this.description = "";
    }

    public Trip(int id, String startPoint, String finalPoint, String dateTime, int numberPassengers, int numberSeats, int price, String driverName, int driverSumMarks, int driverCountMarks, int state, String collectionPoint, String description) {
        this.id = id;
        this.startPoint = startPoint;
        this.finalPoint = finalPoint;
        this.dateTime = dateTime;
        this.numberPassengers = numberPassengers;
        this.numberSeats = numberSeats;
        this.price = price;
        this.driverName = driverName;
        this.driverSumMarks = driverSumMarks;
        this.driverCountMarks = driverCountMarks;
        this.state = state;
        this.collectionPoint = collectionPoint;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getFinalPoint() {
        return finalPoint;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getNumberPassengers() {
        return numberPassengers;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public int getPrice() {
        return price;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getDriverSumMarks() {
        return driverSumMarks;
    }

    public int getDriverCountMarks() {
        return driverCountMarks;
    }

    public int getState() { return state; }


    public void setId(int id) {
        this.id = id;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setFinalPoint(String finalPoint) {
        this.finalPoint = finalPoint;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setNumberPassengers(int numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverSumMarks(int driverSumMarks) {
        this.driverSumMarks = driverSumMarks;
    }

    public void setDriverCountMarks(int driverCountMarks) {
        this.driverCountMarks = driverCountMarks;
    }

    public void setState(int state) { this.state = state; }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
