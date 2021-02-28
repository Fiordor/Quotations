package fiordor.fiocca.quotations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        TextView tvQuotation = findViewById(R.id.tvQuotation);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        ImageButton ib = findViewById(R.id.ibRefresh);

        String text = tvQuotation.getText().toString();
        tvQuotation.setText(String.format(text, getString(R.string.quotation_random_user)));

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvQuotation.setText(R.string.quotation_samlpe_quotation);
                tvAuthor.setText(R.string.quotation_sample_author);
            }
        });
    }
}