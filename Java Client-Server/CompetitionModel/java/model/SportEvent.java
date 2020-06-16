package model;

import java.io.Serializable;

public class SportEvent implements Serializable {
    String name;

    public SportEvent() {
    }

    public SportEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
