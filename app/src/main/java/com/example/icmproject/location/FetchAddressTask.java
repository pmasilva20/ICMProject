package com.example.icmproject.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import com.example.icmproject.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class FetchAddressTask extends AsyncTask<Location, Void, List<Address>> {

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Address> result);
    }
    private OnTaskCompleted mListener;

    private final String TAG = FetchAddressTask.class.getSimpleName();
    private Context mContext;

    public FetchAddressTask(Context applicationContext, OnTaskCompleted listener) {
        mContext = applicationContext;
        mListener = listener;
    }

    @Override
    protected void onPostExecute(List<Address> s) {
        mListener.onTaskCompleted(s);
        super.onPostExecute(s);
    }

    @Override
    protected List<Address> doInBackground(Location... locations) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        Location location = locations[0];
        List<Address> addresses = null;
        String resultMessage = "";

        try {

            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address
                    1);

            if (addresses == null || addresses.size() == 0) {
                if (resultMessage.isEmpty()) {
                    resultMessage = mContext
                            .getString(R.string.no_address_found);
                }
            }

            }
        catch (IOException ioException) {
            resultMessage = mContext
                    .getString(R.string.service_not_available);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            resultMessage = mContext
                    .getString(R.string.invalid_lat_long_used);
        }
        return addresses;
    }
}
