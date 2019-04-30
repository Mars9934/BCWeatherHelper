package Model;

import java.util.*;

public class CitiesAndIDs {
    private Map citiesAndIDs;
    private List listOfCities = new ArrayList();

    public CitiesAndIDs () {
        citiesAndIDs = new HashMap();
        citiesAndIDs.put("Abbotsford", 5881791);
        citiesAndIDs.put("Armstrong", 5887048);
        citiesAndIDs.put("Burnaby", 5911606);
        citiesAndIDs.put("Campbell River", 5914132);
        citiesAndIDs.put("Castlegar", 5918118);
        citiesAndIDs.put("Chilliwack", 5921357);
        citiesAndIDs.put("Colwood", 5926268);
        citiesAndIDs.put("Coquitlam", 5927689);
        citiesAndIDs.put("Courtenay", 5930890);
        citiesAndIDs.put("Cranbrook", 5931800);
        citiesAndIDs.put("Dawson Creek", 5935804);
        citiesAndIDs.put("Delta", 5937615);
        citiesAndIDs.put("Duncan", 5943865);
        citiesAndIDs.put("Enderby", 5948770);
        citiesAndIDs.put("Fernie", 5952370);
        citiesAndIDs.put("Fort St.John", 5881688);
        citiesAndIDs.put("Grand Forks", 5964401);
        citiesAndIDs.put("Greenwood", 5966689);
        citiesAndIDs.put("Kamloops", 5989045);
        citiesAndIDs.put("Kelowna", 5990579);
        citiesAndIDs.put("Kimberley", 5992059);
        citiesAndIDs.put("Langford", 6049388);
        citiesAndIDs.put("Langley", 6049430);
        citiesAndIDs.put("Maple Ridge", 6065686);
        citiesAndIDs.put("Merritt", 6072350);
        citiesAndIDs.put("Nanaimo", 6085772);
        citiesAndIDs.put("Nelson", 6086871);
        citiesAndIDs.put("New Westminster", 6087844);
        citiesAndIDs.put("North Vancouver", 6090786);
        citiesAndIDs.put("Parksville", 6098642);
        citiesAndIDs.put("Penticton", 6101141);
        citiesAndIDs.put("Pitt Meadows", 6105816);
        citiesAndIDs.put("Port Alberni", 6111632);
        citiesAndIDs.put("Port Coquitlam", 6111706);
        citiesAndIDs.put("Port Moody", 6111962);
        citiesAndIDs.put("Powell River", 6112608);
        citiesAndIDs.put("Prince George", 6113365);
        citiesAndIDs.put("Prince Rupert", 6113406);
        citiesAndIDs.put("Quesnel", 6115187);
        citiesAndIDs.put("Revelstoke", 6121621);
        citiesAndIDs.put("Richmond", 6122085);
        citiesAndIDs.put("Rossland", 6127950);
        citiesAndIDs.put("Salmon Arm", 6139417);
        citiesAndIDs.put("Surrey", 6159905);
        citiesAndIDs.put("Terrace", 6162949);
        citiesAndIDs.put("Trail", 6168326);
        citiesAndIDs.put("Vancouver", 6173331);
        citiesAndIDs.put("Vernon", 6173864);
        citiesAndIDs.put("Victoria", 6174041);
        citiesAndIDs.put("West Kelowna", 7281936);
        citiesAndIDs.put("White Rock", 6180961);

        listOfCities.addAll(citiesAndIDs.keySet());
    }

    public Integer getID(String s) {
        if (citiesAndIDs.containsKey(s)) {
             return (Integer) citiesAndIDs.get(s);
        }
        return 0;
    }

    public List getCities() {
        java.util.Collections.sort(listOfCities);
        return listOfCities;
    }
}
