package vandy.mooc.services;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import vandy.mooc.aidl.WeatherCall;
import vandy.mooc.aidl.WeatherData;
import vandy.mooc.operations.WeatherOps;

/**
 * Created by pthakkar9 on 5/29/2015.
 *
 */
public class WeatherServiceSync extends LifecycleLoggingService {

    protected final String TAG = getClass().getSimpleName();

    private WeatherOps weatherOps;

    WeatherCall.Stub weatherCallStub = new WeatherCall.Stub() {

        @Override
        public List<WeatherData> getCurrentWeather(String location) throws RemoteException {

            Log.i(TAG, "inside getCurrentWeather. Passed location is " + location);
            return weatherOps.getWeather(location);
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
        return weatherCallStub;
    }
}
