package fiordor.fiocca.quotations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation;
    private TextView tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvQuotation);
        tvAuthor = findViewById(R.id.tvAuthor);

        String text = tvQuotation.getText().toString();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        tvQuotation.setText(String.format(text, pref.getString("username", "")));
    }

    private void addToFavourite() {
        tvQuotation.setText(R.string.quotation_samlpe_quotation);
        tvAuthor.setText(R.string.quotation_sample_author);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.quotation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miRefreshFavourite : addToFavourite(); return true;
        }

        return super.onOptionsItemSelected(item);
    }
}