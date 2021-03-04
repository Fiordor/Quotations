package fiordor.fiocca.quotations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import fiordor.fiocca.quotations.custom.Quotation;
import fiordor.fiocca.quotations.database.QuotationSQLite;
import fiordor.fiocca.quotations.database.QuotationsDatabase;
import fiordor.fiocca.quotations.threads.RequestThread;

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation;
    private TextView tvAuthor;
    private ProgressBar pb;

    private String accessDB;

    private Menu menu;
    private MenuItem miAdd;
    private MenuItem miRefresh;

    private Quotation quotationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvQuotation);
        tvAuthor = findViewById(R.id.tvAuthor);
        pb = findViewById(R.id.progressBar);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        accessDB = pref.getString("db", "");

        if (savedInstanceState == null) {
            String text = tvQuotation.getText().toString();
            tvQuotation.setText(String.format(text, pref.getString("username", "")));
        } else {
            tvQuotation.setText(savedInstanceState.getString("quote"));
            tvAuthor.setText(savedInstanceState.getString("author"));
        }
    }

    public void setLoadingOff(Quotation quotation) {

        miRefresh.setVisible(true);
        pb.setVisibility(View.INVISIBLE);

        refreshFavourite(quotation);
    }

    public void setLoadingOn() {

        miAdd.setVisible(false);
        miRefresh.setVisible(false);
        pb.setVisibility(View.VISIBLE);
    }

    private void addFavourite() {

        miAdd.setVisible(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (accessDB.equals("sqlite")) {
                    QuotationSQLite.getInstance(QuotationActivity.this).addQuotation(quotationView);
                } else {
                    QuotationsDatabase.getInstance(QuotationActivity.this).quotationDao().addQuotation(quotationView);
                }
            }
        }).start();
    }
    private void refreshFavourite(Quotation q) {

        tvQuotation.setText(q.getQuoteText());
        tvAuthor.setText(q.getQuoteAuthor());
        quotationView = q;

        new Thread(new Runnable() {
            @Override
            public void run() {

                final boolean exists;

                if (accessDB.equals("sqlite")) {
                    exists = QuotationSQLite.getInstance(QuotationActivity.this).existsQuotation(q.getQuoteText());
                } else {
                    exists = QuotationsDatabase.getInstance(QuotationActivity.this).quotationDao().findQuotation(q.getQuoteText()) != null;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        miAdd.setVisible(!exists);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.quotation_menu, menu);
        this.menu = menu;
        miAdd = menu.findItem(R.id.miAddToFavourite);
        miRefresh = menu.findItem(R.id.miRefreshFavourite);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miAddToFavourite : addFavourite(); return true;
            case R.id.miRefreshFavourite : new RequestThread(this).start(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("quote", tvQuotation.getText().toString());
        outState.putString("author", tvAuthor.getText().toString());
    }
}