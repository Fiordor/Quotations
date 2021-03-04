package fiordor.fiocca.quotations.threads;

import java.lang.ref.WeakReference;
import java.util.List;

import fiordor.fiocca.quotations.FavouriteActivity;
import fiordor.fiocca.quotations.custom.Quotation;
import fiordor.fiocca.quotations.database.QuotationSQLite;
import fiordor.fiocca.quotations.database.QuotationsDatabase;

public class ReadDBThread extends Thread {

    private WeakReference<FavouriteActivity> weakActivity;
    private String access;

    public ReadDBThread(FavouriteActivity activity, String access) {
        weakActivity = new WeakReference<>(activity);
        this.access = access;
    }

    @Override
    public void run() {

        List<Quotation> quotations;

        if (weakActivity.get() == null) return;

        if (access.equals("sqlite")) {
            quotations = QuotationSQLite.getInstance(weakActivity.get()).getQuotations();
        } else {
            quotations = QuotationsDatabase.getInstance(weakActivity.get()).quotationDao().getQuotations();
        }

        weakActivity.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weakActivity.get().iniAdapter(quotations);
            }
        });
    }
}
