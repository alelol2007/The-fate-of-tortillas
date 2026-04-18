package com.example.fateofthetortillas.item;

import com.example.fateofthetortillas.crewMembers.Crew;

public class Tortilla {
    public String name;
    public String description;
    public int price;
    public boolean consumed;

    public float rareness;
    //depending on the level the tortilla gets rearer and rearer, but its effect on healing is greater
    public int level;
    public Tortilla(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.consumed = false;
        this.rareness = (float) (level*0.5* rareness);

    }

    public String consume(Crew crew) {
        crew.gainFaith((int) (this.level*rareness));
        this.consumed = true;
        return "You have eaten thyyy, now I shall perish and you shall live";
    }

    public int getPrice() {
        return price;
    }
}
