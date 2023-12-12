package org.example.model;

public class Resort {

    private String name;
    private String town;

    public Resort(){}

    public Resort(String name, String town){
        this.name = name;
        this.town = town;
    }

    public String getName(){
        return name;
    }

    public String getTown() {
        return town;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTown(String town){
        this.town = town;
    }
}
