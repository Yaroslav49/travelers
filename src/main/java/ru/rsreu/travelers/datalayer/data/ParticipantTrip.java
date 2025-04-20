package ru.rsreu.travelers.datalayer.data;

/**
 * Represents a participant in a trip with various attributes related to their performance and approval status.
 */
public class ParticipantTrip {

    public final static ParticipantTrip NULL_PARTICIPANT_TRIP = new ParticipantTrip(-1, "", 0, 0, 0, false, 0, 0);
    private int id;
    private String name;
    private int sumMarks;
    private int countMarks;
    private int driverApprove;
    private boolean passengerRefusal;
    private int passengerMark;
    private int driverMark;
    private boolean isBlocked;

    public ParticipantTrip() {
        this.id = -1;
        this.name = "";
        this.sumMarks = 0;
        this.countMarks = 1;
        this.driverApprove = 0;
        this.passengerRefusal = false;
        this.passengerMark = 0;
        this.driverMark = 0;
    }

    public ParticipantTrip(int id, String name, int sumMarks, int countMarks, int driverApprove, boolean passengerRefusal, int passengerMark, int driverMark) {
        this.id = id;
        this.name = name;
        this.sumMarks = sumMarks;
        this.countMarks = countMarks;
        this.driverApprove = driverApprove;
        this.passengerRefusal = passengerRefusal;
        this.passengerMark = passengerMark;
        this.driverMark = driverMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSumMarks() {
        return sumMarks;
    }

    public void setSumMarks(int sumMarks) {
        this.sumMarks = sumMarks;
    }

    public int getCountMarks() {
        return countMarks;
    }

    public void setCountMarks(int countMarks) {
        this.countMarks = countMarks;
    }

    public int getDriverApprove() {
        return driverApprove;
    }

    public void setDriverApprove(int driverApprove) {
        this.driverApprove = driverApprove;
    }

    public boolean isPassengerRefusal() {
        return passengerRefusal;
    }

    public void setPassengerRefusal(boolean passengerRefusal) {
        this.passengerRefusal = passengerRefusal;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getPassengerMark() {
        return passengerMark;
    }

    public void setPassengerMark(int passengerMark) {
        this.passengerMark = passengerMark;
    }

    public int getDriverMark() {
        return driverMark;
    }

    public void setDriverMark(int driverMark) {
        this.driverMark = driverMark;
    }
}
