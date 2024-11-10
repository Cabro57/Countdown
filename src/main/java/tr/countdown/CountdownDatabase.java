package tr.countdown;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CountdownDatabase {
    private String filePath;
    private ArrayList<Countdown> countdowns;

    private int ID;

    public CountdownDatabase(String dosya_adi) {
        this.countdowns = new ArrayList<>();

        this.ID = 0;

        this.filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\.countdown\\" + dosya_adi;
        File file = new File(filePath);

        // Dosya mevcut değilse oluştur
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("Dosya oluşturuldu!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Countdown> getCountdowns() {
        return countdowns;
    }

    public Countdown getCountdown(int id) {
        for (Countdown countdown : countdowns) {
            if (countdown.getID() == id) {
                return countdown;
            }
        }
        return null;
    }

    public int getSafeID() {
        while (countdowns.stream().anyMatch(c -> c.getID() == ID)) {
            ID++;
        }
        return ID;
    }

    public void addCountdown(Countdown countdown) {

        countdowns.add(countdown);
    }

    public void removeCountdown(int id) {

        countdowns.removeIf(countdown -> countdown.getID() == id);
    }

    public void updateCountdown(int id, String newName, LocalDateTime newDate) {

        Countdown countdown = getCountdown(id);
        countdown.setName(newName);
        countdown.setDate(newDate);
    }

    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] veriler = line.split(",");

                int id = Integer.parseInt(veriler[0].trim());
                String name = veriler[1].trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime date = LocalDateTime.parse(veriler[2].trim(), formatter);

                Countdown countdown = new Countdown(id, name, date);
                countdowns.add(countdown);
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Countdown countdown : countdowns) {
                writer.write(countdown.getID() + "," + countdown.getName() + "," + countdown.getDate2String());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
