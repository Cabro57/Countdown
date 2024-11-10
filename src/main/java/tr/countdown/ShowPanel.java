package tr.countdown;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPanel {
    private MainApp app;

    private JButton close_button;
    private JButton edit_button;
    private JLabel name_label;
    private JLabel date_label;
    private JPanel show_panel;

    private int ID;

    private CountdownTimer timer;

    private CountdownDatabase countdowns;
    private Countdown countdown;

    public ShowPanel(Countdown countdown, MainApp app) {
        this.app = app;
        this.ID = countdown.getID();
        this.countdown = countdown;
        this.countdowns = app.getCds();

        setup();
    }

    public void setup() {

        String name = countdown.getName();
        String date = countdown.getDate2String();

        name_label.setText(name);
        date_label.setText(date);

        //show_panel.setPreferredSize(new Dimension((int) (app.getWidth()*0.8), (int) ((app.getHeight())*0.2)));
        show_panel.setMinimumSize(new Dimension(Integer.MIN_VALUE, 100));
        show_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        timer = new CountdownTimer(countdown, this);
        timer.start();


        close_button.setIcon(FontIcon.of(Feather.FTH_TRASH_2, 20, Color.LIGHT_GRAY));
        close_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = app.getShow_panel();
                //Container parent = show_panel.getParent();
                if (parent != null) {
                    parent.remove(show_panel);
                    parent.revalidate();
                    parent.repaint();

                    countdowns.removeCountdown(ID);
                    countdowns.save();

                    timer.stop();


                }

            }
        });

        edit_button.setIcon(FontIcon.of(Feather.FTH_EDIT_3, 20, Color.LIGHT_GRAY));
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String name = name_label.getText();
                //String date = date_label.getText();

                timer.stop();

                name_label.setText(countdown.getName());
                date_label.setText(countdown.getDate2String());

                JTextField name_textfield = app.getName_textfield();
                name_textfield.setText(countdown.getName());

                JTextField date_textfield = app.getDate_textfield();
                date_textfield.setText(countdown.getDate2String());

                JButton ekleButton = app.getEkleButton();
                ekleButton.setIcon(FontIcon.of(Feather.FTH_EDIT));
                ekleButton.setText("Düzenle");

                app.getEditMode(true, ShowPanel.this);
            }
        });

    }

    public MainApp getApp() {
        return app;
    }

    public JPanel getPanel() {
        return show_panel;
    }

    public JLabel getName_label() {
        return name_label;
    }

    public JLabel getDate_label() {
        return date_label;
    }

    public int getID() {
        return ID;
    }

    public CountdownDatabase getCountdowns() {
        return countdowns;
    }

    public CountdownTimer getTimer() {
        return timer;
    }
}
