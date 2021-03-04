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
import android.widget.TextView;

import fiordor.fiocca.quotations.custom.Quotation;
import fiordor.fiocca.quotations.database.QuotationSQLite;
import fiordor.fiocca.quotations.database.QuotationsDatabase;

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation;
    private TextView tvAuthor;

    private String accessDB;

    private MenuItem miAdd;

    private int numQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvQuotation);
        tvAuthor = findViewById(R.id.tvAuthor);

        numQuotes = 0;

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

    private void addFavourite() {

        Quotation q = new Quotation(
                tvQuotation.getText().toString(),
                tvAuthor.getText().toString());

        if (accessDB.equals("sqlite")) {
            QuotationSQLite.getInstance(this).addQuotation(q);
        } else {
            QuotationsDatabase.getInstance(this).quotationDao().addQuotation(q);
        }
        miAdd.setVisible(false);
    }

    private void exeOnThread(boolean add) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (add) {
                    addFavourite();
                } else {
                    refreshFavourite(++numQuotes);
                }
            }
        }).start();
    }

    private void refreshFavourite(int n) {

        Quotation q = new Quotation(
                String.format(getString(R.string.quotation_samlpe_quotation), n),
                String.format(getString(R.string.quotation_sample_author), n) );

        boolean exists = false;

        if (accessDB.equals("sqlite")) {
            exists = QuotationSQLite.getInstance(this).existsQuotation(q.getQuoteText());
        } else {
            exists = QuotationsDatabase.getInstance(this).quotationDao().findQuotation(q.getQuoteText()) != null;
        }

        if (!exists) {
            miAdd.setVisible(true);
        }

        tvQuotation.setText(q.getQuoteText());
        tvAuthor.setText(q.getQuoteAuthor());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.quotation_menu, menu);
        miAdd = menu.findItem(R.id.miAddToFavourite);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miAddToFavourite : exeOnThread(true); return true;
            case R.id.miRefreshFavourite : exeOnThread(false); return true;
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