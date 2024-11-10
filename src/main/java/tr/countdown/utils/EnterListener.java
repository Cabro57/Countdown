package tr.countdown.utils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterListener extends KeyAdapter {

    JButton add_button;

    public EnterListener(JButton add_button) {
        this.add_button = add_button;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            add_button.doClick();
        }
    }
}
