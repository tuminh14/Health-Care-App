package thien.ntn.Health_are_app.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import thien.ntn.Health_are_app.config.Configs;
import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.singleton.VolleySingleton;


public class VerifyPhoneNumberWorker extends Worker {

    private static final String TAG = VerifyPhoneNumberWorker.class.getSimpleName();
    private JSONObject requestParams;
    public VerifyPhoneNumberWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) throws JSONException {
        super(context, workerParams);
        String input = getInputData().getString("request");
        this.requestParams = new JSONObject(input);
    }

    @NonNull
    @Override
    public synchronized  Result doWork() {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        String url;
        try {
            url = String.format("%s?phoneNumber=%s&verifyCode=%s",
                    Configs.API_URL.VERIFY_PHONE_NUMBER,
                    requestParams.getString("phoneNumber"),
                    requestParams.getString("verifyCode"));
        } catch (JSONException e) {
            e.printStackTrace();
            Data result = new Data.Builder().putString("result", Constants.ERROR_MSG.UNKNOWN_ERROR).build();
            // exception handling
            return Result.failure(result);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,future,future);
        VolleySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);

        try {

            JSONObject response = future.get(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS); // this will block
            Data result = new Data.Builder().putString("result", response.toString()).build();
            return Result.success(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Data result = new Data.Builder().putString("result", Constants.ERROR_MSG.UNKNOWN_ERROR).build();
            // exception handling
            return Result.failure(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Data result = new Data.Builder().putString("result", Constants.ERROR_MSG.NO_NETWORK_CONNECTION).build();
            // exception handling
            return Result.failure(result);
        } catch (TimeoutException e) {
            e.printStackTrace();
            Data result = new Data.Builder().putString("result", Constants.ERROR_MSG.TIMEOUT).build();
            // exception handling
            return Result.failure(result);
        }
    }

}