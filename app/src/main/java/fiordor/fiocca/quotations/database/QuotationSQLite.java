package fiordor.fiocca.quotations.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fiordor.fiocca.quotations.custom.Quotation;

public class QuotationSQLite extends SQLiteOpenHelper {

    private static QuotationSQLite ourInstance;

    public synchronized static QuotationSQLite getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new QuotationSQLite(context);
        }
        return ourInstance;
    }

    private QuotationSQLite(Context context) {
        super(context, "quotation_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT);",
                QuotationContract.Columns.TABLE_NAME,
                QuotationContract.Columns.COLUMN_NAME_ID,
                QuotationContract.Columns.COLUMN_NAME_QUOTE,
                QuotationContract.Columns.COLUMN_NAME_AUTHOR);

        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addQuotation(Quotation quotation) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(QuotationContract.Columns.COLUMN_NAME_QUOTE, quotation.getQuoteText());
        values.put(QuotationContract.Columns.COLUMN_NAME_AUTHOR, quotation.getQuoteAuthor());

        final long id = database.insert(QuotationContract.Columns.TABLE_NAME, null, values);
        database.close();

        return id;
    }
    public int deleteQuotation(String quote) {

        SQLiteDatabase database = getWritableDatabase();

        int row = database.delete(
                QuotationContract.Columns.TABLE_NAME,
                QuotationContract.Columns.COLUMN_NAME_QUOTE + "=?",
                new String[] {quote});

        database.close();
        return row;
    }

    public void deleteQuotations() {

        SQLiteDatabase database = getWritableDatabase();
        database.delete(QuotationContract.Columns.TABLE_NAME, null, null);
        database.close();
    }

    public List<Quotation> getQuotations() {

        List<Quotation> out = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(
                QuotationContract.Columns.TABLE_NAME,
                new String[] {
                        QuotationContract.Columns.COLUMN_NAME_QUOTE,
                        QuotationContract.Columns.COLUMN_NAME_AUTHOR },
                null, null, null, null, null );
        while (cursor.moveToNext()) {
            out.add(new Quotation(cursor.getString(0), cursor.getString(1)));
        }

        cursor.close();
        database.close();

        return out;
    }

    public boolean existsQuotation(String quote) {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(
                QuotationContract.Columns.TABLE_NAME,
                null,
                QuotationContract.Columns.COLUMN_NAME_QUOTE + "=?",
                new String[] {quote},
                null, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        database.close();

        return exists;
    }
}

















