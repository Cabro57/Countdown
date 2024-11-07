package tr.countdown;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.time.TimePicker;

import javax.swing.*;
import java.awt.event.*;

public class MainApp extends JFrame{
    private JTextField name_textfield;
    private JTextField date_textfield;
    private JButton add_button;
    private JLabel name_label;
    private JLabel date_label;
    private JPanel add_panel;
    private JScrollPane scroll_pane;
    private JPanel show_panel;
    private JPanel main_panel;

    private boolean isEditing = false;
    private ShowPanel editingPanel = null;

    private Countdowns cds;

    public MainApp() {
        add(main_panel);

        setTitle("Countdown App");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        show_panel.setLayout(new BoxLayout(show_panel, BoxLayout.PAGE_AXIS));

        FontIcon addIcon = FontIcon.of(Feather.FTH_PLUS);
        add_button.setIcon(addIcon);

        cds = new Countdowns("variables.csv");
        cds.load();

        for (Countdown cd : cds.getCountdowns()) {
            ShowPanel showPanel = new ShowPanel(cd.getID(), cds, MainApp.this);
            show_panel.add(showPanel.getPanel());
            CountdownTimer timer = new CountdownTimer(cd, showPanel);
            timer.start();
            cd.setTimer(timer);

        }

        show_panel.revalidate();
        show_panel.repaint();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Countdown cd : cds.getCountdowns()) {
                    CountdownTimer timer = cd.getTimer();
                    timer.stop();

                }
            }
        });

        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = name_textfield.getText();
                String date = date_textfield.getText();

                if (name_textfield.getText().isEmpty() && date_textfield.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainApp.this,
                            "Başlık ve Tarih alanları boş olamaz!!!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Güncelleme
                if (isEditing && editingPanel != null) {
                    editingPanel.getName_label().setText(name);
                    editingPanel.getDate_label().setText(date);

                    name_textfield.setText("");
                    date_textfield.setText("");

                    System.out.println(editingPanel.getID());

                    cds.updateCountdown(editingPanel.getID(), name, date);
                    cds.save();

                    Countdown cd = cds.getCountdown(editingPanel.getID());

                    CountdownTimer timer = new CountdownTimer(cd, editingPanel);
                    timer.start();

                    isEditing = false;
                    editingPanel = null;

                    add_button.setText("Ekle");
                    add_button.setIcon(FontIcon.of(Feather.FTH_PLUS));

                }
                else {

                    Countdown cd = new Countdown(name, date);
                    cds.addCountdown(cd);
                    cds.save();

                    ShowPanel showPanel = new ShowPanel(cd.getID(), cds, MainApp.this);

                    CountdownTimer timer = new CountdownTimer(cd, showPanel);
                    timer.start();

                    cd.setTimer(timer);

                    show_panel.add(showPanel.getPanel());
                    show_panel.revalidate();
                    show_panel.repaint();
                    name_textfield.setText("");
                    date_textfield.setText("");
                }
            }
        });
        date_textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    add_button.doClick();
                }
            }
        });


        name_textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    add_button.doClick();
                }
            }
        });

    }

    public JTextField getName_textfield() {
        return name_textfield;
    }

    public JTextField getDate_textfield() {
        return date_textfield;
    }

    public JButton getEkleButton() {
        return add_button;
    }

    public void getEditMode(boolean isEditing, ShowPanel editingPanel) {
        this.isEditing = isEditing;
        this.editingPanel = editingPanel;
    }
}
