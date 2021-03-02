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

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation;
    private TextView tvAuthor;

    private Menu menu;

    private int numQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvQuotation);
        tvAuthor = findViewById(R.id.tvAuthor);

        numQuotes = 0;

        if (savedInstanceState == null) {
            String text = tvQuotation.getText().toString();
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            tvQuotation.setText(String.format(text, pref.getString("username", "")));
        } else {
            tvQuotation.setText(savedInstanceState.getString("quote"));
            tvAuthor.setText(savedInstanceState.getString("author"));
        }

    }

    private void addToFavourite(int n) {
        menu.findItem(R.id.miAddToFavourite).setVisible(true);
        String quote = String.format(getString(R.string.quotation_samlpe_quotation), n);
        String author = String.format(getString(R.string.quotation_sample_author), n);
        tvQuotation.setText(quote);
        tvAuthor.setText(author);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.quotation_menu, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miRefreshFavourite : addToFavourite(++numQuotes); return true;
            case R.id.miAddToFavourite : item.setVisible(false); return true;
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