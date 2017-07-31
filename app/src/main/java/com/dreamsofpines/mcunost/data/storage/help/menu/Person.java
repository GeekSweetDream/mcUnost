package com.dreamsofpines.mcunost.data.storage.help.menu;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class Person {
    private String name;
    private String numb;

    public Person(String name, String numb) {
        this.name = name;
        this.numb = numb;
    }

    public String getName() {
        return name;
    }

    public String getNumb() {
        return numb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }
}
