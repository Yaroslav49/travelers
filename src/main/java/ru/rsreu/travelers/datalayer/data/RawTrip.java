package ru.rsreu.travelers.datalayer.data;

/**
 * Represents a raw trip with essential attributes related to its details, including driver information, date and time, pricing, seating capacity, starting and final points, collection point, and a description.
 * It is used in the TripDAO addTrip method to transfer the data entered by the driver when creating a trip as an object
 */
public class RawTrip {
    private int driverId;
    private String datetime;
    private int price;
    private int numberSeats;
    private int startPoint;
    private int finalPoint;
    private String collectionPoint;
    private String description;

    public RawTrip(int driverId, String datetime, int price, int numberSeats, int startPoint, int finalPoint, String collectionPoint, String description) {
        this.driverId = driverId;
        this.datetime = datetime;
        this.price = price;
        this.numberSeats = numberSeats;
        this.startPoint = startPoint;
        this.finalPoint = finalPoint;
        this.collectionPoint = collectionPoint;
        this.description = description;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public int getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(int finalPoint) {
        this.finalPoint = finalPoint;
    }

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
