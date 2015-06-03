package vandy.mooc.activities;

//import vandy.mooc.operations.AcronymOps;
//import vandy.mooc.operations.AcronymOpsImpl;

import vandy.mooc.R;
import vandy.mooc.aidl.WeatherCall;
import vandy.mooc.aidl.WeatherData;
import vandy.mooc.aidl.WeatherRequest;
import vandy.mooc.aidl.WeatherResults;

import vandy.mooc.services.WeatherServiceAsync;
import vandy.mooc.services.WeatherServiceSync;
import vandy.mooc.utils.RetainedFragmentManager;
import vandy.mooc.utils.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * The main Activity that prompts the user for Acronyms to expand via
 * various implementations of AcronymServiceSync and
 * AcronymServiceAsync and view via the results.  Extends
 * LifecycleLoggingActivity so its lifecycle hook methods are logged
 * automatically.
 */
public class MainActivity extends LifecycleLoggingActivity {
    /**
     * Used to retain the ImageOps state between runtime configuration
     * changes.
     */
    protected final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(),
                    TAG);

    /**
     * Provides acronym-related operations.
     */
//    private AcronymOps mAcronymOps;
    private WeatherCall mWeatherCall;
    private WeatherRequest mWeatherRequest;

    private EditText editText;
    private TextView outputTextView;
    private Button mSync;
    private Button mAsync;

    private Context mContext;

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., runtime
     * configuration changes.
     *
     * @param Bundle object that contains saved state information.
     */
    @SuppressWarnings("JavadocReference")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        // Create the AcronymOps object one time.
//        mAcronymOps = new AcronymOpsImpl(this);

        // Handle any configuration change.
        handleConfigurationChanges();

        mContext = getApplicationContext();

        editText = (EditText) findViewById(R.id.editTextLocation);
        outputTextView = (TextView) findViewById(R.id.textViewOutput);
        mSync = (Button) findViewById(R.id.buttonSync);
        mAsync = (Button) findViewById(R.id.buttonAsync);

        Intent intentSync = new Intent(mContext, WeatherServiceSync.class);
        bindService(intentSync, mServiceConnectionSync, BIND_AUTO_CREATE);

        Intent intentAsync = new Intent(mContext, WeatherServiceAsync.class);
        bindService(intentAsync, mServiceConnectionAsync, BIND_AUTO_CREATE);
    }

    /**
     * Hook method called by Android when this Activity is
     * destroyed.
     */
    @Override
    protected void onDestroy() {
        // Unbind from the Service.
//        mAcronymOps.unbindService();

        unbindService(mServiceConnectionSync);
        unbindService(mServiceConnectionAsync);

        // Always call super class for necessary operations when an
        // Activity is destroyed.
        super.onDestroy();
    }

    /**
     * Handle hardware reconfigurations, such as rotating the display.
     */
    protected void handleConfigurationChanges() {
        // If this method returns true then this is the first time the
        // Activity has been created.
        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG,
                    "First time onCreate() call");

            // Create the AcronymOps object one time.  The "true"
            // parameter instructs AcronymOps to use the
            // DownloadImagesBoundService.
//            mAcronymOps = new AcronymOpsImpl(this);

            // Store the AcronymOps into the RetainedFragmentManager.
//            mRetainedFragmentManager.put("ACRONYM_OPS_STATE",
//                                         mAcronymOps);

            // Initiate the service binding protocol (which may be a
            // no-op, depending on which type of DownloadImages*Service is
            // used).
//            mAcronymOps.bindService();
        } else {
            // The RetainedFragmentManager was previously initialized,
            // which means that a runtime configuration change
            // occured.

            Log.d(TAG,
                    "Second or subsequent onCreate() call");

            // Obtain the AcronymOps object from the
            // RetainedFragmentManager.
//            mAcronymOps =
//                mRetainedFragmentManager.get("ACRONYM_OPS_STATE");

            // This check shouldn't be necessary under normal
            // circumtances, but it's better to lose state than to
            // crash!
//            if (mAcronymOps == null) {
            // Create the AcronymOps object one time.  The "true"
            // parameter instructs AcronymOps to use the
            // DownloadImagesBoundService.
//                mAcronymOps = new AcronymOpsImpl(this);

            // Store the AcronymOps into the RetainedFragmentManager.
//                mRetainedFragmentManager.put("ACRONYM_OPS_STATE",
//                                             mAcronymOps);

            // Initiate the service binding protocol (which may be
            // a no-op, depending on which type of
            // DownloadImages*Service is used).
//                mAcronymOps.bindService();
//            } else
            // Inform it that the runtime configuration change has
            // completed.
//                mAcronymOps.onConfigurationChange(this);
        }
    }

    /*
     * Initiate the synchronous acronym lookup when the user presses
     * the "Look Up Sync" button.
     */
//    public void expandAcronymSync(View v) {
//        mAcronymOps.expandAcronymSync(v);
//    }

    /*
     * Initiate the asynchronous acronym lookup when the user presses
     * the "Look Up Async" button.
     */
//    public void expandAcronymAsync(View v) {
//        mAcronymOps.expandAcronymAsync(v);
//    }

    // ServiceConnection object for Sync Service
    private ServiceConnection mServiceConnectionSync = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mWeatherCall = WeatherCall.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mWeatherCall = null;
        }
    };

    // ServiceConnection object for Async Service
    private ServiceConnection mServiceConnectionAsync = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mWeatherRequest = WeatherRequest.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mWeatherRequest = null;
        }
    };

    // Get called when Sync button is pressed
    public void getWeatherSync(View view) {

        final String location = getLocation();

        new AsyncTask<String, Void, List<WeatherData>>() {

            @Override
            protected void onPreExecute() {
                setupDisplayWeather();
            }

            @Override
            protected List<WeatherData> doInBackground(String... params) {
                try {
                    return mWeatherCall.getCurrentWeather(location);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<WeatherData> info) {
                displayWeather(info);
            }
        }.execute();
    }

    // Get called when Async button is pressed
    public void getWeatherAsync(View view) {

        final String location = getLocation();

        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                setupDisplayWeather();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    mWeatherRequest.getCurrentWeather(location, mWeatherResult);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            }

//            @Override
//            protected void onPostExecute(String info) {
//                displayWeather(info);
//            }

        }.execute();

    }

//    // Helper method to display data to output view
//    private void displayWeather(String data) {
//
//        if (data == null) {
//            data = "Couldn't find weather data for entered location. Please try again.";
//        }
//
//        outputTextView.setVisibility(View.VISIBLE);
//        outputTextView.setText(data
//                + String.format("\n\nCurrent Time: %tc", new Date()));
//    }

    // Helper method to display data to output view from getter methods on WeatherData POJO
    private void displayWeather(List<WeatherData> data) {

        String output = null;

        if (data == null) {
            output = "Couldn't find weather data for entered location. Please try again.";
        } else {
            for (WeatherData weatherData : data) {
                output = "\nLocation Name: \t" + weatherData.getName()
                + "\nTemperature(F): \t" + weatherData.getTemp()
                + "\nHumidity (%): \t" + weatherData.getHumidity()
                + "\nWind Speed (miles/hour): \t" + weatherData.getSpeed()
                + "\nWind Direction (degrees): \t" + weatherData.getDeg();
            }
        }

        outputTextView.setVisibility(View.VISIBLE);
        outputTextView.setText(output
                + String.format("\n\nCurrent Time: %tc", new Date()));

        mSync.setClickable(true);
        mSync.setText("Sync");
        mAsync.setClickable(true);
        mAsync.setText("Async");
        editText.setClickable(true);

    }

    private void setupDisplayWeather() {
        outputTextView.setVisibility(View.INVISIBLE);
        mSync.setClickable(false);
        mSync.setText("Fetching Data");
        mAsync.setClickable(false);
        mAsync.setText("Please Wait");
        editText.setClickable(false);
    }

    // Helper method to get location from editText
    private String getLocation() {
        String location = editText.getText().toString();

        if (location.isEmpty()) {

            // Default location
            location = "Columbus,USA";
            Utils.showToast(mContext, "No location entered. " +
                    "Showing data for default location - Columbus, OH, USA");
        }

        return location;
    }

    private WeatherResults.Stub mWeatherResult = new WeatherResults.Stub() {
        @Override
        public void sendResults(List<WeatherData> results) throws RemoteException {
//            final String data = results.get(0).toString();
            final List<WeatherData> data = results;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayWeather(data);
                }
            });
        }
    };
}
