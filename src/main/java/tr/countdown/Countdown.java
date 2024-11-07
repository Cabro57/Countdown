package tr.countdown;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Countdown {
    private int ID;
    private String name;
    private String date;
    private CountdownTimer timer;

    public Countdown(int ID, String name, String date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
    }

    public Countdown(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Countdown(String name, LocalDateTime date) {
        this.name = name;
        this.date = date.toString();
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate2String() {
        return date;
    }

    public LocalDateTime getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime ddate = LocalDateTime.parse(date, formatter);
        return ddate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date.toString();
    }

    public CountdownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountdownTimer timer) {
        this.timer = timer;
    }
}
