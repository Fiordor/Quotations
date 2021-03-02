package fiordor.fiocca.quotations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void dashUnifiedCallback(View view) {

        Intent intent = null;
        switch (view.getId()) {
            case R.id.btGet : intent = new Intent(this, QuotationActivity.class); break;
            case R.id.btFavourite : intent = new Intent(this, FavouriteActivity.class); break;
            case R.id.btSettings : intent = new Intent(this, SettingsActivity.class); break;
            case R.id.btAbout : intent = new Intent(this, AboutActivity.class); break;
            default: return;
        }
        startActivity(intent);
    }
}