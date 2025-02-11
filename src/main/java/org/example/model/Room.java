package org.example.model;

public class Room {
    private int id;
    private Building building;
    private int number;
    private float area;

    public Room(int number,float area){
        this.number = number;
        this.area = area;
    }

    public Room(int id, int number,float area){
        this.id = id;
        this.number = number;
        this.area = area;
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public float getArea() {
        return this.area;
    }

    public void setArea(float area){
        this.area = area;
    }

    public Object[] getTableData() {
        return new Object[]{this.id,this.number, this.area, "delete"};
    }
}
