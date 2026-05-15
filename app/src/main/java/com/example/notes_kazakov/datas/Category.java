package com.example.notes_kazakov.datas;
public class Category {
    public int id;
    public String Name;
    public boolean Active;

    public Category(int id, String name) {
        this.id = id;
        this.Name = name;
        this.Active = false;
    }

    public void setActive(boolean active) { this.Active = active; }
    public boolean isActive() { return Active; }
    public String getName() { return Name; }
}