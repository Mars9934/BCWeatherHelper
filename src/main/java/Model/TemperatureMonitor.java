package Model;

import java.util.ArrayList;
import java.util.List;

public class TemperatureMonitor {
    private List<Integer> temps;
    private Integer fromT;
    private Integer toT;

    public TemperatureMonitor() {
        temps = new ArrayList<Integer>();
        temps.add(-2);
        temps.add(0);
        temps.add(5);
        temps.add(10);
        temps.add(15);
        temps.add(20);
        temps.add(25);
        temps.add(30);
        temps.add(35);
        temps.add(37);
        fromT = -10;
        toT = -10;
    }

    public List<Integer> getTemps() {
        return temps;
    }

    public Boolean hasTemps() {
        return fromT != -10 & toT != 10;
    }

    public Boolean compareTemps(Double t) {
        return t < fromT | t > toT;
    }

    public void setFromT(int i) {
        fromT = i;
    }
    public void setToT(int i) {
        toT = i;
    }
}
