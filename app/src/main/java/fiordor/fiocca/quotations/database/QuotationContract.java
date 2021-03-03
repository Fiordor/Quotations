package fiordor.fiocca.quotations.database;

import android.provider.BaseColumns;

public class QuotationContract {

    private QuotationContract() {

    }

    public static class Columns implements BaseColumns {

        public static final String TABLE_NAME = "quotation_table";
        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_QUOTE = "quote";
        public static final String COLUMN_NAME_AUTHOR = "author";
    }
}
