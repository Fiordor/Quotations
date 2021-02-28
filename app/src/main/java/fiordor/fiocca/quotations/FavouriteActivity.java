package fiordor.fiocca.quotations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fiordor.fiocca.quotations.custom.FavouriteAdapter;
import fiordor.fiocca.quotations.custom.Quotation;

public class FavouriteActivity extends AppCompatActivity implements FavouriteAdapter.OnItemClickListener {

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

        ArrayList<Quotation> quotations = getMockQuotations();
        quotationsAdapter = new FavouriteAdapter(quotations, this::onItemClickListener);
        rv.setAdapter(quotationsAdapter);
    }

    public void openAuthorWiki(String author) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + "Albert Einstein"));

        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (activities.size() > 0) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemClickListener(int position) {

        String author = quotationsAdapter.getQuoteUsingListPosition(position).getQuoteAuthor();

        if (author == null || author.isEmpty()) {

        } else {
            openAuthorWiki(author);
        }
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