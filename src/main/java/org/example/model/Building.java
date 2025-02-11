package org.example.model;

public class Building {
    private int id;
    private String streetName;
    private int houseNumber;
    private int paymentMonthPerSqM;

    public Building(String streetName,int houseNumber,int paymentMonthPerSqM){
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.paymentMonthPerSqM = paymentMonthPerSqM;
    }

    public Building(int id, String streetName,int houseNumber,int paymentMonthPerSqM){
        this.id = id;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.paymentMonthPerSqM = paymentMonthPerSqM;
    }

    public String getStreetName(){
        return this.streetName;
    }

    public void setStreetName(String streetName){
        this.streetName = streetName;
    }


    public int getHouseNumber(){
        return this.houseNumber;
    }

    public void setHouseNumber(int houseNumber){
        this.houseNumber = houseNumber;
    }


    public int getPaymentMonthPerSqM(){
        return this.paymentMonthPerSqM;
    }

    public void setPaymentMonthPerSqM(int paymentMonthPerSqM) {
        this.paymentMonthPerSqM = paymentMonthPerSqM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object[] getTableData() {
        return new Object[]{this.id, this.streetName, this.houseNumber, this.paymentMonthPerSqM, "delete"};
    }
}
