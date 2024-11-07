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

    public ShowPanel(int ID, Countdowns cds, MainApp app) {
        this.app = app;
        this.ID = ID;

        Countdown cd = cds.getCountdown(ID);

        String name = cd.getName();
        String date = cd.getDate2String();

        name_label.setText(name);
        date_label.setText(date);

        FontIcon closeIcon = FontIcon.of(Feather.FTH_TRASH_2);
        close_button.setIcon(closeIcon);
        FontIcon editIcon = FontIcon.of(Feather.FTH_EDIT_3);
        edit_button.setIcon(editIcon);


        //show_panel.setPreferredSize(new Dimension((int) (app.getWidth()*0.8), (int) ((app.getHeight())*0.2)));
        show_panel.setMinimumSize(new Dimension(Integer.MIN_VALUE, 100));
        show_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        //app.add(show_panel);

        //CountdownTimer timer = new CountdownTimer(cd, ShowPanel.this);



        close_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = show_panel.getParent();
                if (parent != null) {
                    parent.remove(show_panel);
                    parent.revalidate();
                    parent.repaint();

                    cds.removeCountdown(ID);
                    cds.save();

                    CountdownTimer timer = cd.getTimer();
                    timer.stop();


                }

            }
        });

        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String name = name_label.getText();
                //String date = date_label.getText();

                CountdownTimer timer = cd.getTimer();
                timer.stop();

                name_label.setText(cd.getName());
                date_label.setText(cd.getDate2String());

                JTextField name_textfield = app.getName_textfield();
                name_textfield.setText(cd.getName());

                JTextField date_textfield = app.getDate_textfield();
                date_textfield.setText(cd.getDate2String());

                JButton ekleButton = app.getEkleButton();
                ekleButton.setIcon(FontIcon.of(Feather.FTH_EDIT));
                ekleButton.setText("Düzenle");

                app.getEditMode(true, ShowPanel.this);
            }
        });

    }

    public JPanel getPanel() {
        return show_panel;
    }

    public MainApp getApp() {
        return app;
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
}
