package vandy.mooc.jsonweather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

/**
 * Parses the Json weather data returned from the Weather Services API
 * and returns a List of JsonWeather objects that contain this data.
 */
public class WeatherJSONParser {
    /**
     * Used for logging purposes.
     */
    private final String TAG =
            this.getClass().getCanonicalName();

    /**
     * Parse the @a inputStream and convert it into a List of JsonWeather
     * objects.
     */
    public List<JsonWeather> parseJsonStream(InputStream inputStream)
            throws IOException {
        // TODO -- you fill in here.
        Log.d(TAG, "Inside parseJasonStream. Passed InputStream is " + inputStream);

        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        List<JsonWeather> list = new ArrayList<>();

        JsonWeather listItem = parseJsonWeather(reader);

        if (listItem == null){
            list = null;
        } else {
            list.add(listItem);
        }
        return list;
    }

    /**
     * Parse a Json stream and convert it into a List of JsonWeather
     * objects.
     */
    public List<JsonWeather> parseJsonWeatherArray(JsonReader reader)
            throws IOException {

        // TODO -- you fill in here.

        List<JsonWeather> list = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            list.add(parseJsonWeather(reader));
        }
        reader.endArray();
        return list;

    }

    /**
     * Parse a Json stream and return a JsonWeather object.
     */
    public JsonWeather parseJsonWeather(JsonReader reader)
            throws IOException {

        // TODO -- you fill in here.

        Log.d(TAG, "Inside parseJasonWeather. Passed JsonReader is " + reader);

        JsonWeather jsonWeather = new JsonWeather();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case JsonWeather.name_JSON:
                    jsonWeather.setName(reader.nextString());
                    break;
                case JsonWeather.cod_JSON:
                    jsonWeather.setCod(reader.nextInt());
                    break;
                case JsonWeather.dt_JSON:
                    jsonWeather.setDt(reader.nextLong());
                    break;
                case JsonWeather.id_JSON:
                    jsonWeather.setId(reader.nextLong());
                    break;
                case JsonWeather.base_JSON:
                    jsonWeather.setBase(reader.nextString());
                    break;
                case JsonWeather.main_JSON:
                    jsonWeather.setMain(parseMain(reader));
                    break;
                case JsonWeather.wind_JSON:
                    jsonWeather.setWind(parseWind(reader));
                    break;
                case JsonWeather.weather_JSON:
                    jsonWeather.setWeather(parseWeathers(reader));
                    break;
                case JsonWeather.sys_JSON:
                    jsonWeather.setSys(parseSys(reader));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        if (jsonWeather.getCod() != 200){
            Log.i(TAG, "Seems like it's going to fail as received code is not 200 (Success)");
            jsonWeather = null;
        }
        return jsonWeather;

    }

    /**
     * Parse a Json stream and return a List of Weather objects.
     */
    public List<Weather> parseWeathers(JsonReader reader) throws IOException {
        // TODO -- you fill in here.

        List<Weather> list = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            list.add(parseWeather(reader));
        }
        reader.endArray();
        return list;
    }

    /**
     * Parse a Json stream and return a Weather object.
     */
    public Weather parseWeather(JsonReader reader) throws IOException {
        // TODO -- you fill in here.

        Weather weather = new Weather();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case Weather.description_JSON:
                    weather.setDescription(reader.nextString());
                    break;
                case Weather.icon_JSON:
                    weather.setIcon(reader.nextString());
                    break;
                case Weather.id_JSON:
                    weather.setId(reader.nextLong());
                    break;
                case Weather.main_JSON:
                    weather.setMain(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return weather;
    }

    /**
     * Parse a Json stream and return a Main Object.
     */
    public Main parseMain(JsonReader reader)
            throws IOException {
        // TODO -- you fill in here.

        Main main = new Main();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case Main.grndLevel_JSON:
                    main.setGrndLevel(reader.nextDouble());
                    break;
                case Main.humidity_JSON:
                    main.setHumidity(reader.nextLong());
                    break;
                case Main.pressure_JSON:
                    main.setPressure(reader.nextDouble());
                    break;
                case Main.seaLevel_JSON:
                    main.setSeaLevel(reader.nextDouble());
                    break;
                case Main.temp_JSON:
                    main.setTemp(reader.nextDouble());
                    break;
                case Main.tempMin_JSON:
                    main.setTempMin(reader.nextDouble());
                    break;
                case Main.tempMax_JSON:
                    main.setTempMax(reader.nextDouble());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return main;
    }

    /**
     * Parse a Json stream and return a Wind Object.
     */
    public Wind parseWind(JsonReader reader) throws IOException {
        // TODO -- you fill in here.

        Wind wind = new Wind();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case Wind.deg_JSON:
                    wind.setDeg(reader.nextDouble());
                    break;
                case Wind.speed_JSON:
                    wind.setSpeed(reader.nextDouble());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return wind;
    }

    /**
     * Parse a Json stream and return a Sys Object.
     */
    public Sys parseSys(JsonReader reader) throws IOException {
        // TODO -- you fill in here.

        Sys sys = new Sys();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case Sys.country_JSON:
                    sys.setCountry(reader.nextString());
                    break;
                case Sys.message_JSON:
                    sys.setMessage(reader.nextDouble());
                    break;
                case Sys.sunrise_JSON:
                    sys.setSunrise(reader.nextInt());
                    break;
                case Sys.sunset_JSON:
                    sys.setSunset(reader.nextInt());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return sys;
    }
}
