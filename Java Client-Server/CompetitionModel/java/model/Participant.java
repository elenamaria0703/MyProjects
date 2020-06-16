package model;

import java.io.Serializable;

public class Participant extends Entity<Integer> implements Serializable {
    private String name;
    private Integer age;
    private SportEvent firstEvent;
    private SportEvent secondEvent;
    private Integer nrEventsAttended;


    public Participant(String name, Integer age, SportEvent firstEvent, SportEvent secondEvent, Integer nrEventsAttended) {
        this.name = name;
        this.age = age;
        this.firstEvent = firstEvent;
        this.secondEvent = secondEvent;
        this.nrEventsAttended = nrEventsAttended;
    }

    public Participant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SportEvent getFirstEvent() {
        return firstEvent;
    }

    public void setFirstEvent(SportEvent firstEvent) {
        this.firstEvent = firstEvent;
    }

    public SportEvent getSecondEvent() {
        return secondEvent;
    }

    public void setSecondEvent(SportEvent secondEvent) {
        this.secondEvent = secondEvent;
    }

    public Integer getNrEventsAttended() {
        return nrEventsAttended;
    }

    public void setNrEventsAttended(Integer nrEventsAttended) {
        this.nrEventsAttended = nrEventsAttended;
    }

    @Override
    public String toString() {
        String participant=name+" "+age+" ";
        if(firstEvent!=null)
            participant+=firstEvent.getName()+" ";
        else
            participant+=null+" ";
        if(secondEvent!=null)
            participant+=firstEvent.getName()+" ";
        else
            participant+=null+" ";
        return participant+nrEventsAttended;
    }
}
