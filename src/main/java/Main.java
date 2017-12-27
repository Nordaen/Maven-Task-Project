import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        String url = "http://api.openweathermap.org/data/2.5/weather?q=";
        String API = "&appid=c874ec2627d75d6c96be9048eb67c519";
        String FORMAT = "&units=metric";


        System.out.println("What city's weather you would like to know?");
        Scanner ins = new Scanner(System.in);
        String answer = ins.nextLine();

        StringBuffer responce = new StringBuffer();
        responce = GetData.RetrieveData(url+answer+FORMAT+API);

        String jsonString = JsonBuilder.Build(responce);
        Writer.Write(jsonString);
    }
}

class CityData{
    private String name;
    private double temp;
    private double pressure;
    private double humidity;
    private double lonCoord;
    private double latCoord;
    public CityData(String name, double temp, double pressure, double humidity, double lonCoord, double latCoord) {
        this.name = name;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.lonCoord = lonCoord;
        this.latCoord = latCoord;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getLonCoord() {
        return lonCoord;
    }

    public void setLonCoord(double lonCoord) {
        this.lonCoord = lonCoord;
    }

    public double getLatCoord() {
        return latCoord;
    }

    public void setLatCoord(double latCoord) {
        this.latCoord = latCoord;
    }


    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }












}
class Writer{
    public static void Write(String str) throws IOException {
        try(FileWriter writer = new FileWriter("D:\\data.txt", true)){
            writer.write(str);
        }

    }
}
class GetData{
    public static StringBuffer RetrieveData(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer responce = new StringBuffer();

        while ((inputLine = in.readLine())!=null){
            responce.append(inputLine);
        }
        in.close();
        return  responce;

    }
}
class JsonBuilder{
    public static String Build(StringBuffer responce){
        Gson gson = new Gson();
        String jsonString = gson.toJson(responce);


        String pressure = gson.fromJson(jsonString,String.class);

        JsonObject jsonObject = new JsonParser().parse(responce.toString()).getAsJsonObject();

        JsonObject jsonMain,jsonCoord;

        jsonMain = jsonObject.get("main").getAsJsonObject();
        jsonCoord = jsonObject.get("coord").getAsJsonObject();


        CityData cData = new CityData(jsonObject.get("name").getAsString(),
                jsonMain.get("temp").getAsDouble(),
                jsonMain.get("pressure").getAsDouble(),
                jsonMain.get("humidity").getAsDouble(),
                jsonCoord.get("lon").getAsDouble(),
                jsonCoord.get("lat").getAsDouble());
        System.out.println("The city is: " + cData.getName() +
                " coordinates is: lon " + cData.getLonCoord()+
                " lan " + cData.getLatCoord());

        jsonString = gson.toJson(cData);
        return jsonString;
    }
}