package tr.countdown.utils;

import tr.countdown.ShowPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseButtonListener implements ActionListener {
    ShowPanel sp;

    public CloseButtonListener(JPanel app) {
        this.sp = sp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel parent = sp.getApp().getShow_panel();
        //Container parent = show_panel.getParent();
        if (parent != null) {
            parent.remove(sp.getPanel());
            parent.revalidate();
            parent.repaint();

            sp.getCountdowns().removeCountdown(sp.getID());
            sp.getCountdowns().save();

            sp.getTimer().stop();
        }
    }
}
