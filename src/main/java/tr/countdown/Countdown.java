package tr.countdown;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Countdown {
    private int ID;
    private String name;
    private LocalDateTime date;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Countdown(int ID, String name, LocalDateTime date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
    }

    public Countdown(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
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
        return date.format(formatter);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
