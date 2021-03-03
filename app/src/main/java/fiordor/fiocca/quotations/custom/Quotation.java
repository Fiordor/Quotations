package fiordor.fiocca.quotations.custom;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fiordor.fiocca.quotations.database.QuotationContract;

@Entity(tableName = QuotationContract.Columns.TABLE_NAME)
public class Quotation {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = QuotationContract.Columns.COLUMN_NAME_QUOTE)
    @NonNull
    private String quoteText;
    @ColumnInfo(name = QuotationContract.Columns.COLUMN_NAME_AUTHOR)
    private String quoteAuthor;

    public Quotation() { }

    public Quotation(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }
}
