package Model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.List;

public class TextMessenger {
    private String textOut;
    public static final String ACCOUNT_SID = "ACda6108ccb7d00f572275da620e7c3a03";
    public static final String AUTH_TOKEN = "a6fb699731180bd991c1f3c7c2e4c08d";

    public TextMessenger() {
        textOut = "";
    }

    public void sendText() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        if (textOut == null | textOut.equals("")) {
            textOut = "No reports fit criteria.";
        }
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+17788989939"),
                new com.twilio.type.PhoneNumber("+17787714535"),
                textOut)
                .create();

        System.out.println(message.getSid());
    }

    public void organizeReport(List lor) {
        StringBuilder out = new StringBuilder();

        for (Object l : lor) {
            List list = (List) l;
            String time = list.get(4).toString();
            String weather = list.get(1).toString() + ": " + list.get(2).toString();
            String temp = "Temp: " + list.get(0).toString() + "°C";
            String wind = "Wind: " + list.get(3).toString();
            out.append("\n").append(time).append("\n").append(weather).append("\n").append(temp).append("\n").append(wind).append("\n\n");
        }
        textOut = out.toString();
    }
}
