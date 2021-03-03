package fiordor.fiocca.quotations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import fiordor.fiocca.quotations.custom.FavouriteAdapter;
import fiordor.fiocca.quotations.custom.Quotation;
import fiordor.fiocca.quotations.database.QuotationSQLite;

public class FavouriteActivity extends AppCompatActivity implements FavouriteAdapter.OnItemClickListener, FavouriteAdapter.OnItemLongClickListener {

    private FavouriteAdapter quotationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        RecyclerView rv = findViewById(R.id.rvFavourite);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(manager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        rv.addItemDecoration(decoration);

        List<Quotation> quotations = QuotationSQLite.getInstance(this).getQuotations();
        quotationsAdapter = new FavouriteAdapter(quotations, this::onItemClickListener, this::onItemLongClickListener);
        rv.setAdapter(quotationsAdapter);
    }

    public void openAuthorWiki(String author) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + author));

        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (activities.size() > 0) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.favourite_menu, menu);
        return true;
    }

    @Override
    public void onItemClickListener(int position) {

        String author = quotationsAdapter.getQuoteUsingListPosition(position).getQuoteAuthor();

        if (author == null || author.isEmpty()) {
            Toast.makeText(this, R.string.favourite_cant_get_info, Toast.LENGTH_LONG).show();
        } else {
            openAuthorWiki(URLEncoder.encode(author));
        }
    }

    @Override
    public void onItemLongClickListener(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setTitle(R.string.favourite_take_a_breath);
        builder.setMessage(R.string.favourite_are_you_sure);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Quotation q = quotationsAdapter.getQuoteUsingListPosition(position);

                QuotationSQLite.getInstance(FavouriteActivity.this).deleteQuotation(q.getQuoteText());

                quotationsAdapter.removeQuoteUsingListPosition(position);
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() != R.id.miCleanAllFavourite) {
            return super.onOptionsItemSelected(item);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setTitle(R.string.favourite_take_a_breath);
        builder.setMessage(R.string.favourite_menu_are_you_sure);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                QuotationSQLite.getInstance(FavouriteActivity.this).deleteQuotations();

                quotationsAdapter.clearAllList();
                item.setVisible(false);
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create().show();
        return true;
    }

    public ArrayList<Quotation> getMockQuotations() {
        ArrayList<Quotation> q = new ArrayList<>();
        q.add(new Quotation("No hay que ir para atrás ni para derse impulso", "Lao Tsé"));
        q.add(new Quotation("No hay caminos para la paz; la paz es el camino", "Mahatma Gandhi"));
        q.add(new Quotation("Haz el amor y no la guerra", "John Lennon"));
        q.add(new Quotation("Para trabajar basta estar convencido de una cosa: que trabajar es menos aburrido que divertirse", "Charles Baudelaire"));
        q.add(new Quotation("Lo peor que hacen los malos es obligarnos a dudar de los buenos", "Jacinto Benavente"));
        q.add(new Quotation("Aprende a vivir y sabrás morir bien", "Confucio"));
        q.add(new Quotation("Cada día sabemos más y entendemos menos", "Albert Einstein"));
        q.add(new Quotation("La medida del amor es amar sin medida", "San Agustín"));
        q.add(new Quotation("El dinero no puede comprar la vida", "Bob Marley"));
        q.add(new Quotation("Si es bueno vivir, todavía es mejor soñar, y lo mejor de todo, despertar", "Antonio Machado"));
        q.add(new Quotation("Ningún hombre es lo bastante bueno para gobernar a otros sin su consentimiento", "Abraham Lincoln"));
        q.add(new Quotation("La más estricta justicia no creo que sea siempre la mejor política", "Abraham Lincoln"));
        q.add(new Quotation("Pienso, luego existo", "René Descartes"));
        q.add(new Quotation("Sólo puede ser feliz siempre el que sepa ser feliz con todo", "Confucio"));

        return q;
    }
}