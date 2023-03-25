package frases.database;

import android.provider.BaseColumns;

public final class FrasesBiblicasContract {

    public static final String _ID = BaseColumns._ID;

    private FrasesBiblicasContract() {}

    public static class FrasesBiblicasEntry implements BaseColumns {
        public static final String TABLE_NAME = "frases_biblicas";
        public static final String COLUMN_FRASE = "frase";
    }


}
