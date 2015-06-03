package vandy.mooc.services;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

import vandy.mooc.aidl.WeatherData;
import vandy.mooc.aidl.WeatherRequest;
import vandy.mooc.aidl.WeatherResults;
import vandy.mooc.operations.WeatherOps;

/**
 * Created by pthakkar9 on 5/31/2015.
 *
 */
public class WeatherServiceAsync extends LifecycleLoggingService {

    private WeatherOps weatherOps;

    WeatherRequest.Stub mWeatherRequest = new WeatherRequest.Stub() {
        @Override
        public void getCurrentWeather(String location, WeatherResults results) throws RemoteException {
            List<WeatherData> weatherData = weatherOps.getWeather(location);
            results.sendResults(weatherData);
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        weatherOps = new WeatherOps();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return mWeatherRequest;
    }
}
