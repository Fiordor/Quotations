package fiordor.fiocca.quotations.threads;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import fiordor.fiocca.quotations.QuotationActivity;
import fiordor.fiocca.quotations.R;
import fiordor.fiocca.quotations.custom.Quotation;

public class RequestThread extends Thread {

    private WeakReference<QuotationActivity> weakReference;

    public RequestThread(QuotationActivity activity) {
        weakReference = new WeakReference<>(activity);
    }

    private void finishLoad(Quotation quotation) {

        weakReference.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weakReference.get().setLoadingOff(quotation);
            }
        });
    }

    private Uri.Builder getUriBuilder() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendPath("");

        return builder;
    }

    private void startLoad() {
        weakReference.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weakReference.get().setLoadingOn();
            }
        });
    }

    @Override
    public void run() {

        if (weakReference.get() == null) return;

        startLoad();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(weakReference.get());
        String method = pref.getString("http","GET");
        String language = pref.getString("language", "en");

        Log.d("personaltag", method);

        String body = "method=getQuote&format=json&lang=" + language;

        final  Quotation quotation;
        Quotation loadQuotation = null;

        Uri.Builder builder = getUriBuilder();
        if (method.equals("GET")) {
            builder.appendQueryParameter("method", "getQuote");
            builder.appendQueryParameter("format", "json");
            builder.appendQueryParameter("lang", language);
        }

        try {
            URL url = new URL(builder.build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoInput(true);

            if (method.equals("POST")) {

                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);;
                writer.flush();
                writer.close();
            }

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            loadQuotation = new Gson().fromJson(reader, Quotation.class);
            reader.close();

            connection.disconnect();
        } catch (Exception ex) {
            String error = weakReference.get().getString(R.string.quotation_error) + ": " + ex.getMessage();
            loadQuotation = new Quotation(error, "");
        } finally {
            quotation = loadQuotation;
        }
        finishLoad(quotation);
    }
}
