package com.example.testdb;

public class StudentManageChild {

    private String roomFloor;
    private String timeSlot;
    private String roomID;

    public StudentManageChild(String roomFloor, String timeSlot, String roomID){
        this.roomFloor = roomFloor;
        this.timeSlot = timeSlot;
        this.roomID = roomID;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public String getTimeSlot() {
        return timeSlot;
    }


    public String getRoomID() {
        return roomID;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
