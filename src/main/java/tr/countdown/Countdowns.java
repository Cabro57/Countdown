package tr.countdown;

import java.io.*;
import java.util.ArrayList;

public class Countdowns {
    private String filePath;
    private ArrayList<Countdown> countdowns;

    public Countdowns(String dosya_adi) {
        this.countdowns = new ArrayList<>();

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

    public void addCountdown(Countdown countdown) {
        int newID = 0;

        while (true) {
            int finalnewID = newID;

            if (!countdowns.stream().anyMatch(c -> c.getID() == finalnewID)) {
                break;
            }
            else {
                newID++;
            }
        }

        countdown.setID(newID);
        countdowns.add(countdown);
    }

    public void removeCountdown(int id) {
        countdowns.removeIf(countdown -> countdown.getID() == id);

    }

    public void updateCountdown(int id, String newName, String newDate) {
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
                String date = veriler[2].trim();

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
