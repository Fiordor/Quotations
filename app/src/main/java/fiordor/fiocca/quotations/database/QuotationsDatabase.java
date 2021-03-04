package fiordor.fiocca.quotations.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fiordor.fiocca.quotations.custom.Quotation;

@Database(version = 1, entities = Quotation.class)
public abstract class QuotationsDatabase extends RoomDatabase {

    private static QuotationsDatabase ourInstance;

    public synchronized static QuotationsDatabase getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = Room.databaseBuilder(
                    context, QuotationsDatabase.class, "quotation_database")
                    .build();
        }
        return ourInstance;
    }

    public static void destroyInstance() {
        if (ourInstance != null && ourInstance.isOpen()) {
            ourInstance.close();
            ourInstance = null;
        }
    }

    public abstract QuotationDao quotationDao();

}
