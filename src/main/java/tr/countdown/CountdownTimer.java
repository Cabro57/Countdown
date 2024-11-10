package tr.countdown;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer extends TimerTask {
    private Countdown countdown;
    private ShowPanel showPanel;
    private LocalDateTime targetDate;
    private Timer timer = new Timer();

    public CountdownTimer(Countdown countdown, ShowPanel showPanel) {
        this.countdown = countdown;
        this.showPanel = showPanel;
        this.targetDate = countdown.getDate();
    }

    public void start() {
        timer.scheduleAtFixedRate(this, 0, 1000); // Her 1 saniyede bir çalışacak
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void run() {
        Duration duration = Duration.between(LocalDateTime.now(), targetDate);

        if (duration.isNegative() || duration.isZero()) {
            showPanel.getDate_label().setText("Geri Sayım Bitti!");
            timer.cancel();
            return;
        }

        long mounths = duration.toDays() / 30;
        long days = duration.toDays() % 30;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        showPanel.getDate_label().setText(
                mounths + " ay " + days + " gün " + hours + " saat " + minutes + " dakika " + seconds + " saniye"
        );
    }
}
