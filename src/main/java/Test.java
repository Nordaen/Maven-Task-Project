import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Test {
    public  void Launch() throws IOException {
    String url = "http://api.openweathermap.org/data/2.5/weather?q=London";
        String API = "&appid=c874ec2627d75d6c96be9048eb67c519";

        URL obj = new URL(url+API);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer responce = new StringBuffer();

        while ((inputLine = in.readLine())!=null){
            responce.append(inputLine);
        }
        in.close();


        Gson gson = new Gson();
        String jsonString = gson.toJson(responce);
        System.out.println(jsonString);

        String pressure = gson.fromJson(jsonString,String.class);

        JsonObject jsonObject = new JsonParser().parse(responce.toString()).getAsJsonObject();

         System.out.println();
        String info =jsonObject.get("main").getAsString();
        JsonElement jsobject = JsonElement.get("main").getAsString();

        System.out.println();




        System.out.println(responce.toString());


    }


    public static class CityData{
        private String name;
        private int temp;
        private int pressure;
        private int humidity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTemp() {
            return temp;
        }

        public void setTemp(int temp) {
            this.temp = temp;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }
    public static class TestPars{
        double temp;
    }

}
