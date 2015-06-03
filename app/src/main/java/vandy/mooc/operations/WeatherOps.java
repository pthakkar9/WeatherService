package vandy.mooc.operations;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vandy.mooc.aidl.WeatherData;
import vandy.mooc.jsonweather.JsonWeather;
import vandy.mooc.jsonweather.WeatherJSONParser;
import vandy.mooc.utils.Utils;

/**
 * Created by pthakkar9 on 5/29/2015.
 */
public class WeatherOps {

    protected final String TAG = getClass().getSimpleName();
    protected final String KEY_TIME = "QUERIED_WEATHER_TIME";
    protected final String KEY_LOCATION = "QUERIED_WEATHER_TIME";
    protected final long TEN_SECONDS = 10 * 1000;

    private final String weatherServiceUrl;

    private Map<String, Long> queriedWeatherTime = new HashMap<String, Long>();
    private Map<String, String> queriedWeatherLocation = new HashMap<String, String>();
    private List<WeatherData> storedWeatherData;

    public WeatherOps() {
        weatherServiceUrl = "http://api.openweathermap.org/data/2.5/weather"; //?q=city,country
        queriedWeatherTime.put(KEY_TIME, null);
        queriedWeatherLocation.put(KEY_LOCATION, null);
    }

    public List<WeatherData> getWeather(String location) {

        Log.i(TAG, "Inside getWeather. Passed location is - " + location);

        HttpURLConnection connection = null;

        try {
            URL url = new URL(weatherServiceUrl + "?q=" + location + "&units=imperial");
            connection = (HttpURLConnection) url.openConnection();
            Log.i(TAG, "HttpURLConnection is " + connection);

            if (queriedWeatherTime.get(KEY_TIME) == null) {
                // This is the first time any weather data is requested

                // Store current time
                queriedWeatherTime.put(KEY_TIME, System.currentTimeMillis());

                // Store current location
                queriedWeatherLocation.put(KEY_LOCATION, location);

                // Request weather data
                List<WeatherData> receivedWeather = makeWeatherData(connection.getInputStream());

                // Store received data for 'caching'
                storedWeatherData = receivedWeather;

            } else if (!queriedWeatherLocation.get(KEY_LOCATION).equalsIgnoreCase(location)) {
                // Weather data was requested before but not for this location

                // Store current time
                queriedWeatherTime.put(KEY_TIME, System.currentTimeMillis());

                // Store current location
                queriedWeatherLocation.put(KEY_LOCATION, location);

                // Request weather data
                List<WeatherData> receivedWeather = makeWeatherData(connection.getInputStream());

                // Store received data for 'caching'
                storedWeatherData = receivedWeather;
            } else {
                // Weather data was requested earlier for this location

                // Check it was within last 10 seconds
                if (System.currentTimeMillis() - queriedWeatherTime.get(KEY_TIME) > TEN_SECONDS) {
                    // It was more than 10 seconds ago
                    // Store current time
                    queriedWeatherTime.put(KEY_TIME, System.currentTimeMillis());

                    // Store current location
                    queriedWeatherLocation.put(KEY_LOCATION, location);

                    // Request weather data
                    List<WeatherData> receivedWeather = makeWeatherData(connection.getInputStream());

                    // Store received data for 'caching'
                    storedWeatherData = receivedWeather;
                } else {
                    // It was within last 10 seconds for last request

                    // Do nothing. Return statement will take care of it
                    Log.i(TAG, "Returning results from caching :-)");
                }

            }

            return storedWeatherData;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    private List<WeatherData> makeWeatherData(InputStream jsonStream) {

        Log.i(TAG, "Inside makeWeatherData. Passed InputStream is - " + jsonStream);

        List<WeatherData> list = new ArrayList<>();

        try {
            WeatherJSONParser parser = new WeatherJSONParser();
            List<JsonWeather> jsonWeathers = parser.parseJsonStream(jsonStream);

            if (jsonWeathers == null) {
                list = null;
            } else {
                for (JsonWeather jsonWeather : jsonWeathers) {
                    WeatherData weatherData = new WeatherData(
                            jsonWeather.getName(),
                            jsonWeather.getWind().getSpeed(),
                            jsonWeather.getWind().getDeg(),
                            jsonWeather.getMain().getTemp(),
                            jsonWeather.getMain().getHumidity(),
                            jsonWeather.getSys().getSunrise(),
                            jsonWeather.getSys().getSunset());

                    list.add(weatherData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
