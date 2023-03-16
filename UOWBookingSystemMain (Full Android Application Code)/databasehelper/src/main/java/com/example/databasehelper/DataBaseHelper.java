
package com.example.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;


import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DataBaseHelper extends SQLiteOpenHelper {
    //TYPE STAFF,SUPER,STU
    public static final String DATABASE_NAME="CSIT214Pp.db";
    public static final String USERS_TABLE_NAME ="USERS_TABLE";
    public static final String USERS_TABLE_USERNAME="USERNAME";
    public static final String USERS_TABLE_PASSWORD ="PASSWORD";
    public static final String USERS_TABLE_TYPE ="TYPE";
    public static String Cur_User="";

    //ROOM STATUS ALL CAPS
    //NOT APPROVED
    //APPROVED
    //NOT BOOKED
    //BOOKED


    //ROOM TIMESLOTS
    //0900hrs - 1200hrs
    //1200hrs - 1500hrs
    //1500hrs - 1800hrs
    //1800hrs - 2100hrs

    //DATE FORMAT
    //DD/MM/YYYY


    public static final String ROOMS_TABLE_NAME = "ROOMS_TABLE";
    public static final String ROOMS_TABLE_ID = "ID";
    public static final String ROOMS_TABLE_FLOOR = "FLOOR_NUM";
    public static final String ROOMS_TABLE_TIMESLOT = "TIMESLOT";
    public static final String ROOMS_TABLE_ROOM = "ROOM_NUM";
    public static final String ROOMS_TABLE_CREATOR = "CREATOR";
    public static final String ROOMS_TABLE_DATE = "DATE";
    public static final String ROOMS_TABLE_CAPACITY = "CAPACITY";
    public static final String ROOMS_TABLE_PRICE = "PRICE";
    public static final String ROOMS_TABLE_PROMOCODE = "PROMOCODE";
    public static final String ROOMS_TABLE_STATUS = "STATUS";

    //Cost is final cost when the booking is paid
    public static final String BOOKING_TABLE_NAME="BOOKING_TABLE";
    public static final String BOOKING_TABLE_ID="ID";
    public static final String BOOKING_TABLE_ROOMID = "ROOM_ID";
    public static final String BOOKING_TABLE_USER = "USER";
    public static final String BOOKING_TABLE_COST ="COST";


    public static final String CreateUserTableStatement="CREATE TABLE "+ USERS_TABLE_NAME +"("+USERS_TABLE_USERNAME+" TEXT PRIMARY KEY, "+ USERS_TABLE_PASSWORD +" TEXT, "+ USERS_TABLE_TYPE +" TEXT);";
    public static final String CreateRoomTableStatement = "CREATE TABLE " + ROOMS_TABLE_NAME + "(" + ROOMS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROOMS_TABLE_FLOOR + " TEXT, " + ROOMS_TABLE_ROOM + " TEXT, " + ROOMS_TABLE_CREATOR + " TEXT, " + ROOMS_TABLE_DATE + " TEXT, " + ROOMS_TABLE_TIMESLOT + " TEXT, " + ROOMS_TABLE_CAPACITY + " INTEGER, " + ROOMS_TABLE_PRICE + " REAL, " + ROOMS_TABLE_PROMOCODE + " TEXT, " + ROOMS_TABLE_STATUS + " TEXT " + ");";
    public static final String CreateBookingTableStatement="CREATE TABLE "+ BOOKING_TABLE_NAME +"(" + BOOKING_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ BOOKING_TABLE_ROOMID + " INTEGER, " +  BOOKING_TABLE_COST+ " REAL, "+ BOOKING_TABLE_USER+ " STRING);";
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreateUserTableStatement);
        db.execSQL(CreateRoomTableStatement);
        db.execSQL(CreateBookingTableStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ ROOMS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE_NAME);
        onCreate(db);
    }


    //Data validation to add user
    public boolean Validate_User(String username)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor curs = db.rawQuery("SELECT " + USERS_TABLE_USERNAME + " FROM " + USERS_TABLE_NAME+ " WHERE( " + USERS_TABLE_USERNAME + " = " + "'" + username + "'" +");", null);
        curs.moveToFirst();
        while (curs.isAfterLast() == false) {
            String tempUsername = curs.getString(curs.getColumnIndex(USERS_TABLE_USERNAME));
            if (tempUsername.equals(username)) {
                return false;
            }
        }
        return true;
    }



    //Method to add user
    public boolean addUser(String username,String password) {
        //Class instances
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        if (username.contains("STAFF")) {
            contentValues.put(USERS_TABLE_TYPE, "STAFF");
            contentValues.put(USERS_TABLE_USERNAME, username);
            contentValues.put(USERS_TABLE_PASSWORD, password);
            long result = db.insert(USERS_TABLE_NAME, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }
        if (username.contains("STU")) {
            contentValues.put(USERS_TABLE_TYPE, "STU");
            contentValues.put(USERS_TABLE_USERNAME, username);
            contentValues.put(USERS_TABLE_PASSWORD, password);
            long result = db.insert(USERS_TABLE_NAME, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }

        if (username.contains("SUPER")) {
            contentValues.put(USERS_TABLE_TYPE, "SUPER");
            contentValues.put(USERS_TABLE_USERNAME, username);
            contentValues.put(USERS_TABLE_PASSWORD, password);
            long result = db.insert(USERS_TABLE_NAME, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }


    //Cursor to view room
    public Cursor readAllRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ROOMS_TABLE_NAME + " ORDER BY DATE, TIMESLOT;";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    //Method to verify user to login
    public boolean checkUser(String username,String password){
        //Get data from database
        String[]  cols= {"username"};
        String loginquery=USERS_TABLE_USERNAME+ "=?" + " and "+ USERS_TABLE_PASSWORD + "=?";
        String[] selectionArgs={username,password};

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(USERS_TABLE_NAME,cols,loginquery,selectionArgs,null,null,null);
        int count=cursor.getCount();

        //close cursor and db when done
        cursor.close();
        db.close();

        if (count>0) {
            Cur_User = username;
            return true;
        }
        else
            return false;

    }

    //gets all dates with status NOT BOOKED or AVAILABLE on or after current date and returns an arraylist of calendar objects
    public ArrayList<Calendar> getAvailRoomDates(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Calendar> roomDatesArray = new ArrayList<>();
        Cursor curs = db.rawQuery("SELECT " + ROOMS_TABLE_DATE + " FROM " + ROOMS_TABLE_NAME + " WHERE( " + ROOMS_TABLE_STATUS + " = " + "'NOT BOOKED'" + ");", null);
        while(curs.moveToNext()){
            Calendar tempDate = stringToCalendar(curs.getString(curs.getColumnIndex(ROOMS_TABLE_DATE)));
            Calendar testDate = Calendar.getInstance();
            if(tempDate.getTimeInMillis() >= testDate.getTimeInMillis()){
                roomDatesArray.add(tempDate);
            }
        }
        return roomDatesArray;
    }

    public ArrayList<Calendar> getAvailRoomDates(String exemptedRoomID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Calendar> roomDatesArray = new ArrayList<>();
        Cursor curs = db.rawQuery("SELECT " + ROOMS_TABLE_DATE + ", " + ROOMS_TABLE_ID + " FROM " + ROOMS_TABLE_NAME + " WHERE( " + ROOMS_TABLE_STATUS + " = " + "'NOT BOOKED');", null);
        while(curs.moveToNext()){
            Calendar tempDate = stringToCalendar(curs.getString(curs.getColumnIndex(ROOMS_TABLE_DATE)));
            Calendar testDate = Calendar.getInstance();
            if(tempDate.getTimeInMillis() >= testDate.getTimeInMillis() && exemptedRoomID.equals(curs.getString(curs.getColumnIndex(ROOMS_TABLE_ID))) == false){
                roomDatesArray.add(tempDate);
            }
        }
        return roomDatesArray;
    }

    public ArrayList<String> getRoomIds(String startDate,String endDate,String roomNo,String floorNo) {

        String query = "SELECT * FROM ROOMS_TABLE WHERE (STATUS = 'BOOKED'" + " AND ROOM_NUM = " + "'" + roomNo + "'" + " AND FLOOR_NUM = " + "'" + floorNo + "'" + ") ORDER BY DATE, TIMESLOT;";
        Calendar sd = stringToCalendar(startDate);
        Calendar ed = stringToCalendar(endDate);
        ArrayList<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Calendar temp = stringToCalendar(cursor.getString(cursor.getColumnIndex("DATE")));
                System.out.println("TempDate: " + String.valueOf(temp.getTimeInMillis() >= sd.getTimeInMillis()));
                System.out.println("StartDate: " + String.valueOf(sd.getTimeInMillis()));
                System.out.println("EndDate: " + String.valueOf(ed.getTimeInMillis()));
                if ((temp.getTimeInMillis() >= sd.getTimeInMillis())&& (temp.getTimeInMillis() <= (ed.getTimeInMillis()+1000000)))
                {
                    results.add(cursor.getString(cursor.getColumnIndex("ID")));
                }
            }
        }
        return results;
    }

    public ArrayList<String> getDataFromRoomID(String roomID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ROOMS_TABLE WHERE( ID = '" + roomID + "');";
        Cursor cursor = null;
        ArrayList result = new ArrayList<>();
        if(db!= null){
            cursor = db.rawQuery(query, null);
            cursor.moveToNext();
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_ID)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_FLOOR)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_ROOM)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_CREATOR)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_DATE)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_TIMESLOT)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_CAPACITY)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_PRICE)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_PROMOCODE)));
            result.add(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_STATUS)));

//            result.add("#" + cursor.getString(0) + "-" + cursor.getString(1));
//            result.add(cursor.getString(2));
//            result.add(cursor.getString(3));
        }
        return result;
    }


    //converts dd-mm-yyyy string into calendar object
    public Calendar stringToCalendar(String date){
        Calendar resultCalendar = Calendar.getInstance();
        String[] dateSplit = date.split("-");
        int year = Integer.parseInt((String) Array.get(dateSplit, 2));
        int month = Integer.parseInt((String) Array.get(dateSplit, 1)) - 1; //month is counted as JANUARY = 0, DECEMBER = 11 thus if user writes 12 as december, deduct one to get proper calendar month
        int dayOfMonth = Integer.parseInt((String) Array.get(dateSplit, 0));
        resultCalendar.set(Calendar.YEAR, year);
        resultCalendar.set(Calendar.MONTH, month);
        resultCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return resultCalendar;
    }

    //Returns cursor containing rooms available on the date specified
    public Cursor getRoomsOnDate(String date){
        String query = "SELECT " + ROOMS_TABLE_ID + ", " + ROOMS_TABLE_ROOM + ", " + ROOMS_TABLE_FLOOR + ", " + ROOMS_TABLE_TIMESLOT + " FROM " + ROOMS_TABLE_NAME + " WHERE( " + ROOMS_TABLE_DATE + " = " + "'" + date + "'" + " AND " + ROOMS_TABLE_STATUS + " = " + "'NOT BOOKED'" + ") ORDER BY TIMESLOT ASC;";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //Returns cursor containing NOT APPROVED ROOM
    public Cursor getRoomNotApproved(){
        String query = "SELECT " +"*"+ " FROM "+ROOMS_TABLE_NAME+" WHERE( " + ROOMS_TABLE_STATUS + " = " + "'NOT APPROVED'" + ");";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // TODO: 10/5/2021 add validation to make sure no room can have same tuple data(date, room, floor, timeslot)
    public boolean addRoom(String Date, String Timeslot, String  Floor, String  Room, String  Capacity, String Price, String Promocode) {
        //Class instances
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Put in values
        contentValues.put(ROOMS_TABLE_DATE, String.valueOf(Date));
        contentValues.put(ROOMS_TABLE_CREATOR,Cur_User);
        contentValues.put(ROOMS_TABLE_TIMESLOT, String.valueOf(Timeslot));
        contentValues.put(ROOMS_TABLE_FLOOR, String.valueOf(Floor));
        contentValues.put(ROOMS_TABLE_ROOM, String.valueOf(Room));
        contentValues.put(ROOMS_TABLE_CAPACITY, String.valueOf(Capacity));
        contentValues.put(ROOMS_TABLE_PRICE, String.valueOf(Price));
        contentValues.put(ROOMS_TABLE_PROMOCODE, String.valueOf(Promocode));
        contentValues.put(ROOMS_TABLE_STATUS, "NOT APPROVED");
        long result = db.insert(ROOMS_TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;


    }

    public String getCost(String roomID){
        String query = "SELECT " + ROOMS_TABLE_PRICE + " FROM " + ROOMS_TABLE_NAME + " WHERE ( " + ROOMS_TABLE_ID + " = " + "'" + roomID + "'" + ");";
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "";
        Cursor cursor = null;
        if (db!= null){
            cursor = db.rawQuery(query, null);
            while(cursor.moveToNext()){
                result = cursor.getString(0);
            }
        }
        return result;
    }

    public boolean isValidPromocode(String roomID, String promocode){
        String query = "SELECT * FROM " + ROOMS_TABLE_NAME + " WHERE( " + ROOMS_TABLE_ID + " = " + "'" + roomID + "'" + " AND " + ROOMS_TABLE_PROMOCODE + " = " + "'" + promocode + "'" + ");";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query, null);
            if(cursor.getCount()==0){
                return false;

            }
            return true;
        }
        return false;
    }

    public boolean isValidRoom(String date, String room,String floor,String timeslot){
        String query = "SELECT * FROM " + ROOMS_TABLE_NAME + " WHERE( " + ROOMS_TABLE_DATE + " = " + "'" + date + "'" + " AND "+ ROOMS_TABLE_ROOM + " = " + "'" + room + "'" + " AND " + ROOMS_TABLE_FLOOR + " = " + "'" + floor + "'" + " AND "+ ROOMS_TABLE_TIMESLOT + " = " + "'" + timeslot + "'" + ");";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query, null);
            if(cursor.getCount() == 0)
                return true;
            else
                return false;
        }
        return true;
    }

    public void addBooking(String roomID, String cost){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "UPDATE ROOMS_TABLE SET STATUS = 'BOOKED' WHERE ID = " + "'" + roomID + "';";
        db.execSQL(query);

        contentValues.put(BOOKING_TABLE_ROOMID, roomID);
        contentValues.put(BOOKING_TABLE_USER, Cur_User);
        contentValues.put(BOOKING_TABLE_COST, cost);
        db.insert(BOOKING_TABLE_NAME, null, contentValues);
    }

    public void approveRoom(String roomID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "UPDATE ROOMS_TABLE SET STATUS = 'APPROVED' WHERE ID = " + "'" + roomID + "';";
        db.execSQL(query);
    }

    public void rejectRoom(String roomID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "UPDATE ROOMS_TABLE SET STATUS = 'REJECTED' WHERE ID = " + "'" + roomID + "';";
        db.execSQL(query);

        contentValues.put(BOOKING_TABLE_ROOMID, roomID);
        contentValues.put(BOOKING_TABLE_USER, Cur_User);
        db.insert(BOOKING_TABLE_NAME, null, contentValues);
    }


    // new method to update room data to database
    public boolean updateRoom(String row_id, String Date, String Timeslot, String  Floor, String  Room, String  Capacity, String Price, String Promocode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // put values to update onto content values
        cv.put(ROOMS_TABLE_DATE, String.valueOf(Date));
        cv.put(ROOMS_TABLE_TIMESLOT, String.valueOf(Timeslot));
        cv.put(ROOMS_TABLE_FLOOR, String.valueOf(Floor));
        cv.put(ROOMS_TABLE_ROOM, String.valueOf(Room));
        cv.put(ROOMS_TABLE_CAPACITY, String.valueOf(Capacity));
        cv.put(ROOMS_TABLE_PRICE, String.valueOf(Price));
        cv.put(ROOMS_TABLE_PROMOCODE, String.valueOf(Promocode));

        db.update(ROOMS_TABLE_NAME, cv, ROOMS_TABLE_ID + " = ?", new String[]{row_id});
        return true;
    }

    // new method to delete room data from database
    public void deleteRoom(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(ROOMS_TABLE_NAME, ROOMS_TABLE_ID + " = ?", new String[]{row_id});
    }

    // new method to update status
    public boolean launchRoom(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ROOMS_TABLE_STATUS, "NOT BOOKED");
        long result = db.update(ROOMS_TABLE_NAME, cv, ROOMS_TABLE_ID + " = ?", new String[]{row_id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getRoomsOnNumber(String FloorNo,String RoomNo){
        String query = "SELECT " +"*"+ " FROM " + ROOMS_TABLE_NAME + " WHERE( " + ROOMS_TABLE_FLOOR + " = " + "'" + FloorNo + "'" + " AND " + ROOMS_TABLE_ROOM+ " = " + "'"+RoomNo+"'" + ") ORDER BY DATE, TIMESLOT;";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<String> getStudentBookedDates(){
        ArrayList<String> resultArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT ROOMS_TABLE.DATE FROM ROOMS_TABLE INNER JOIN BOOKING_TABLE ON ROOMS_TABLE.ID = BOOKING_TABLE.ROOM_ID WHERE BOOKING_TABLE.USER = '" + Cur_User + "' ORDER BY ROOMS_TABLE.DATE ASC;";
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            while(cursor.moveToNext()){
                resultArray.add(cursor.getString(0));
            }
        }
        return resultArray;
    }
    //returns arraylist of the timeslots in a particular date that a user has booked in asc order
    public ArrayList<String> getStudentBookedSlots(String date){
        ArrayList<String> resultArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT ROOM_ID FROM BOOKING_TABLE WHERE( USER = '" + Cur_User + ");";
        String query = "SELECT ROOMS_TABLE.ID, ROOMS_TABLE.TIMESLOT FROM ROOMS_TABLE INNER JOIN BOOKING_TABLE ON ROOMS_TABLE.ID = BOOKING_TABLE.ROOM_ID WHERE(BOOKING_TABLE.USER = '" + Cur_User + "' AND ROOMS_TABLE.DATE = '" + date + "') ORDER BY ROOMS_TABLE.TIMESLOT ASC;";
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            while(cursor.moveToNext()){
                resultArray.add(cursor.getString(0));
            }
        }
        return resultArray;
    }
    //gets string formatted date of a specific roomID
    public String getDateFromRoomID(String roomID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + ROOMS_TABLE_DATE + " FROM " + ROOMS_TABLE_NAME + " WHERE (ID = '" + roomID + "');";
        Cursor cursor = null;
        String result = "";
        if(db != null){
            cursor = db.rawQuery(query, null);
            cursor.moveToNext();
            Calendar temp = stringToCalendar(cursor.getString(cursor.getColumnIndex(ROOMS_TABLE_DATE)));
            result = DateFormat.getDateInstance(DateFormat.MEDIUM).format(temp.getTime());
        }
        return result;
    }

    //gets room details based on roomID and returns an array list with two entries: the room+floor string(formatted) and the timeslot
    public ArrayList<String> getRoomDataFromRoomID(String roomID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ROOM_NUM, FLOOR_NUM, TIMESLOT, DATE FROM ROOMS_TABLE WHERE( ID = '" + roomID + "');";
        Cursor cursor = null;
        ArrayList result = new ArrayList<>();
        if(db!= null){
            cursor = db.rawQuery(query, null);
            cursor.moveToNext();
            result.add("#" + cursor.getString(1) + "-" + cursor.getString(0));
            result.add(cursor.getString(2));
            result.add(cursor.getString(3));
        }
        return result;
    }

    public String getBookingCost(String roomID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COST FROM BOOKING_TABLE WHERE ROOM_ID = '" + roomID + "';";
        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return "";
    }

    public boolean hasBookings(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM BOOKING_TABLE;";
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int countBooking = cursor.getInt(0);
        if(countBooking == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public void revertBookedStatus(String roomID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE ROOMS_TABLE SET STATUS = 'NOT BOOKED' WHERE ID = " + "'" + roomID + "';";
        db.execSQL(query);

        String deleteQuery = "DELETE FROM BOOKING_TABLE WHERE ROOM_ID = " + "'" + roomID + "';";
        db.execSQL(deleteQuery);
    }

    public boolean hasAvailRoomStatus(String floorNo, String roomNo){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT count(*) FROM ROOMS_TABLE WHERE(ROOM_NUM = '" + roomNo + "' AND FLOOR_NUM = '" + floorNo + "');";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 0){
            return false;
        }
        return true;
    }
}