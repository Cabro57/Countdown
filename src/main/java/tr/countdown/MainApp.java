package tr.countdown;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.time.TimePicker;
import tr.countdown.utils.EnterListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainApp extends JFrame{
    private JPanel main_panel;
    private JPanel add_panel;
    private JLabel name_label;
    private JTextField name_textfield;
    private JLabel date_label;
    private JFormattedTextField date_textfield;
    private JButton add_button;
    private JPanel show_panel;
    private JScrollPane scroll_pane;
    private JFormattedTextField time_textfield;

    private boolean isEditing = false;
    private ShowPanel editingPanel = null;

    private CountdownDatabase cds;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MainApp() {
        add(main_panel);

        setTitle("Countdown App");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(date_textfield);

        TimePicker timePicker = new TimePicker();
        timePicker.set24HourView(true);
        timePicker.setEditor(time_textfield);

        show_panel.setLayout(new BoxLayout(show_panel, BoxLayout.PAGE_AXIS));

        cds = new CountdownDatabase("variables.csv");
        cds.load();

        for (Countdown cd : cds.getCountdowns()) {
            ShowPanel showPanel = new ShowPanel(cd, MainApp.this);
            show_panel.add(showPanel.getPanel());
        }

        show_panel.revalidate();
        show_panel.repaint();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        add_button.setIcon(FontIcon.of(Feather.FTH_PLUS, 20 , Color.LIGHT_GRAY));
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = name_textfield.getText();
                String dateString = date_textfield.getText() + " " + time_textfield.getText();
                System.out.println(dateString);
                LocalDateTime date = LocalDateTime.parse(dateString, formatter);


                if (name_textfield.getText().isEmpty() && date_textfield.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainApp.this,
                            "Başlık ve Tarih alanları boş olamaz!!!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Güncelleme
                if (isEditing && editingPanel != null) {
                    editingPanel.getName_label().setText(name);
                    editingPanel.getDate_label().setText(date.toString());

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
                    add_button.setIcon(FontIcon.of(Feather.FTH_PLUS, 18, Color.LIGHT_GRAY));

                }
                else {
                    Countdown cd = new Countdown(name, date);

                    ShowPanel showPanel = new ShowPanel(cd, MainApp.this);

                    cds.addCountdown(cd);
                    cds.save();

                    show_panel.add(showPanel.getPanel());
                    show_panel.revalidate();
                    show_panel.repaint();
                    name_textfield.setText("");
                    date_textfield.setText("");
                }
            }
        });
        date_textfield.addKeyListener(new EnterListener(add_button));


        name_textfield.addKeyListener(new EnterListener(add_button));

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

    public CountdownDatabase getCds() {
        return cds;
    }

    public JPanel getShow_panel() {
        return show_panel;
    }
}
