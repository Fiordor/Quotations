package fiordor.fiocca.quotations.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fiordor.fiocca.quotations.custom.Quotation;

@Dao
public interface QuotationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addQuotation(Quotation quotation);

    @Delete
    void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM " + QuotationContract.Columns.TABLE_NAME)
    List<Quotation> getQuotations();

    @Query("SELECT * FROM " + QuotationContract.Columns.TABLE_NAME +
            " WHERE " + QuotationContract.Columns.COLUMN_NAME_QUOTE + " = :quote")
    Quotation findQuotation(String quote);

    @Query("DELETE FROM " + QuotationContract.Columns.TABLE_NAME)
    void deleteQuotations();
}
