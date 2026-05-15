package com.example.notes_kazakov.datas;

public class Item {
    public String Name;
    public String Modell;
    public Integer Price;
    public int id;

    public Item(int id, String name, String modell, Integer price) {
        this.id = id;
        Name = name;
        Modell = modell;
        Price = price;
    }
}