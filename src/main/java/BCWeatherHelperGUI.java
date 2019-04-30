import Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BCWeatherHelperGUI {
    private JPanel MainForm;
    private JTabbedPane tabbedPane1;
    private JTextField weather;
    private JTextField temp;
    private JTextField windSpeed;
    private JTextField lastUpdate;
    private JComboBox location;
    private JButton updateButton;
    private JComboBox fromTempCBox;
    private JComboBox toTempCBox;
    private JButton sendNext24HrButton;
    private JCheckBox drizzleCheckBox;
    private JCheckBox rainCheckBox;
    private JCheckBox snowCheckBox;
    private JCheckBox fogCheckBox;
    private JCheckBox clearCheckBox;
    private JCheckBox smokeCheckBox;
    private JCheckBox tornadoCheckBox;
    private JCheckBox cloudsCheckBox;
    private JCheckBox thunderstormCheckBox;
    private JCheckBox hazeCheckBox;
    private JLabel time;
    private JCheckBox onlyIncludeCheckBox;
    private JCheckBox tempRangeCheckBox;
    private JLabel toTemp;
    private JLabel fromTemp;
    private JCheckBox ignoreOnlyIfBothCheckBox;
    private JLabel weatherIcon;
    private JLabel butterflies1;
    private JLabel butterflies2;
    private List<JCheckBox> listOfWeather;

    private String loc;

    private TemperatureMonitor tempMtr;
    private List listOfFiltered;

    public BCWeatherHelperGUI() throws ParseException, IOException {
        loc = "Richmond";
        listOfWeather = new ArrayList<>();
        tempMtr = new TemperatureMonitor();
        listOfFiltered = new ArrayList();
        initialize();

        onlyIncludeCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!onlyIncludeCheckBox.isSelected()) {
                    for (JCheckBox j : listOfWeather) {
                        j.setEnabled(false);
                    }
                    if (tempRangeCheckBox.isSelected()) {
                        ignoreOnlyIfBothCheckBox.setEnabled(false);
                    }
                } else {
                    for (JCheckBox j : listOfWeather) {
                        j.setEnabled(true);
                    }
                    if (tempRangeCheckBox.isSelected()) {
                        ignoreOnlyIfBothCheckBox.setEnabled(true);
                    }
                }
            }
        });

        tempRangeCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!tempRangeCheckBox.isSelected()) {
                    fromTemp.setEnabled(false);
                    toTemp.setEnabled(false);
                    fromTempCBox.setEnabled(false);
                    toTempCBox.setEnabled(false);
                    if (onlyIncludeCheckBox.isSelected()) {
                        ignoreOnlyIfBothCheckBox.setEnabled(false);
                    }
                } else {
                    fromTemp.setEnabled(true);
                    toTemp.setEnabled(true);
                    fromTempCBox.setEnabled(true);
                    toTempCBox.setEnabled(true);
                    if (onlyIncludeCheckBox.isSelected()) {
                        ignoreOnlyIfBothCheckBox.setEnabled(true);
                    }
                }

            }
        });
        location.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loc = (String) location.getSelectedItem();
                location.setSelectedItem(location.getSelectedItem());
                CurrentParser c = null;
                try {
                    c = new CurrentParser(loc);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                temp.setText(c.getTemp());
                weather.setText(c.getWeather());
                weatherIcon.setIcon(new ImageIcon(c.getWeatherIcon()));
                windSpeed.setText(c.getWind());
                lastUpdate.setText(c.getTimeOfInfo());
            }
        });
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    CurrentParser c = new CurrentParser(loc);
                    temp.setText(c.getTemp());
                    weather.setText(c.getWeather());
                    weatherIcon.setIcon(new ImageIcon(c.getWeatherIcon()));
                    windSpeed.setText(c.getWind());
                    lastUpdate.setText(c.getTimeOfInfo());


                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        fromTempCBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fromTempCBox.getSelectedItem() != null) {
                    tempMtr.setFromT((Integer) fromTempCBox.getSelectedItem());
                }
            }
        });
        toTempCBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toTempCBox.getSelectedItem() != null) {
                    tempMtr.setToT((Integer) toTempCBox.getSelectedItem());
                }
            }
        });

        sendNext24HrButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TextMessenger tm = new TextMessenger();

                if (!tempRangeCheckBox.isSelected() & !onlyIncludeCheckBox.isSelected()) {
                    try {
                        listOfFiltered = new ForecastParser(loc).getListOfReports();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    tm.organizeReport(listOfFiltered);
                    tm.sendText();
                }

                else if (!ignoreOnlyIfBothCheckBox.isSelected()) {
                    if (onlyIncludeCheckBox.isSelected() & tempRangeCheckBox.isSelected()) {
                        listOfFiltered = filterWeather();
                        listOfFiltered = filterTemp();
                    }
                    else if (onlyIncludeCheckBox.isSelected()) {
                        listOfFiltered = filterWeather();
                    }
                    else {listOfFiltered = filterTemp();}

                    tm.organizeReport(listOfFiltered);
                    tm.sendText();
                }
                else {
                    listOfFiltered = filterBoth();
                    tm.organizeReport(listOfFiltered);
                    tm.sendText();
                }
            }
        });
    }


    public static void main(String[] args) throws ParseException, IOException {
        JFrame frame = new JFrame("BCWeatherHelperGUI");
        frame.setContentPane(new BCWeatherHelperGUI().MainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void initialize() throws ParseException, IOException {
        butterflies1.setIcon(new ImageIcon(this.getClass().getResource("butterflies.jpeg")));
        butterflies2.setIcon(new ImageIcon(this.getClass().getResource("butterflies.jpeg")));
        initLocation();
        initCurrConditions();
        initCheckBoxes();
        initTempCBoxes();
        initTime();

        init24HourReport();
    }

    private void initCurrConditions() throws IOException {
        CurrentParser c = new CurrentParser((String) location.getSelectedItem());
        temp.setText(c.getTemp());
        weather.setText(c.getWeather());
        windSpeed.setText(c.getWind());
        lastUpdate.setText(c.getTimeOfInfo());
        weatherIcon.setIcon(new ImageIcon(c.getWeatherIcon()));
    }

    private void initTempCBoxes() {
        for (Integer i : tempMtr.getTemps()) {
            fromTempCBox.addItem(i);
            toTempCBox.addItem(i);
        }
    }

    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH);
        Date date = new Date();
        sdf.applyPattern("EEEE, MMM d, yyyy");
        String sDate = sdf.format(date);
        time.setText(sDate);
    }

    private void initCheckBoxes() {
        listOfWeather.add(drizzleCheckBox);
        listOfWeather.add(rainCheckBox);
        listOfWeather.add(snowCheckBox);
        listOfWeather.add(fogCheckBox);
        listOfWeather.add(clearCheckBox);
        listOfWeather.add(smokeCheckBox);
        listOfWeather.add(tornadoCheckBox);
        listOfWeather.add(cloudsCheckBox);
        listOfWeather.add(thunderstormCheckBox);
        listOfWeather.add(hazeCheckBox);
    }

    private void initLocation() {
        for (Object s: new CitiesAndIDs().getCities()) {
            location.addItem(s.toString());
        }
        location.setSelectedItem("Richmond");
    }

    private void init24HourReport() throws IOException {
        ForecastParser f = new ForecastParser(loc);
    }


    private List filterWeather() {
        ForecastParser f = null;
        try {
            f = new ForecastParser(loc);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List filteredList = new ArrayList();
        for (JCheckBox j : listOfWeather) {
            if (j.isSelected()) {
                for (Object l : f.getListOfReports()) {

                    if (((List) l).get(1).equals(j.getText())) {
                        filteredList.add(l);
                    }
                }
            }
        }
        return filteredList;
    }


    private List filterTemp() {
        List filteredList = new ArrayList();

        if (!onlyIncludeCheckBox.isSelected()) {
            ForecastParser f = null;
            try {
                f = new ForecastParser(loc);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            for (Object l : f.getListOfReports()) {
                List list = (List) l;
                if (tempMtr.hasTemps()) {
                    if (tempMtr.compareTemps((Double) list.get(0))) {
                        filteredList.add(l);
                    }
                }
            }
        }

        else{
                for (Object l : listOfFiltered) {
                    List list = (List) l;
                    if (tempMtr.hasTemps()) {
                        if (tempMtr.compareTemps((Double) list.get(0))) {
                            filteredList.add(l);
                        }
                    }
                }
            }
          return filteredList;
        }

    private List filterBoth() {
        ForecastParser f = null;
        try {
            f = new ForecastParser(loc);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List filteredList = new ArrayList();
        for (JCheckBox j : listOfWeather) {
            if (j.isSelected()) {
                for (Object l : f.getListOfReports()) {

                    if (((List) l).get(1).equals(j.getText()) | tempMtr.compareTemps((Double) ((List) l).get(0))) {
                        filteredList.add(l);
                    }
                }
            }
        }
        return filteredList;
    }
}
