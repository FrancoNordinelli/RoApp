

/*Clase que crear y mantener/actualizar la base de datos con las frases*/
package frases.database;

import static frases.database.FrasesBiblicasContract.FrasesBiblicasEntry.COLUMN_FRASE;
import static frases.database.FrasesBiblicasContract.FrasesBiblicasEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FrasesBiblicaDBHelper extends SQLiteOpenHelper {

    //private static final: atributos de clase encapsulados privados, finales y estáticos.
    // Toda instancia creada de esta clase, contendrá los valores de estas variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "frases_biblicas.db";

    //Constructor. Context
    public FrasesBiblicaDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Definir el esquema de la base de datos y crear la tabla
        final String SQL_CREATE_FRASES_TABLE = "CREATE TABLE " +
                FrasesBiblicasContract.FrasesBiblicasEntry.TABLE_NAME + " (" +
                FrasesBiblicasContract.FrasesBiblicasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FRASE + " TEXT NOT NULL " +
                "); ";

        db.execSQL(SQL_CREATE_FRASES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS " + FrasesBiblicasContract.FrasesBiblicasEntry.TABLE_NAME);
        onCreate(db);
    }

    public void agregarFrase(String frase) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FRASE, frase);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void agregarFrasesPredeterminadas(Context context) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            // Abre el archivo CSV desde la carpeta "assets"
            inputStream = context.getAssets().open("frases.csv");

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                // Agrega cada línea del archivo CSV a la base de datos
                FrasesBiblicaDBHelper db = new FrasesBiblicaDBHelper(context);
                db.agregarFrase(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierra los recursos
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String obtenerFraseAleatoria() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_FRASE}, null, null, null, null, "RANDOM()", "1");

        String frase = "";

        if (cursor.moveToFirst()) {
            frase = cursor.getString(0);
            Log.d("FRASE", "Frase obtenida de la base de datos: " + frase);
        } else {
            Log.d("FRASE", "No se encontraron frases en la base de datos");
        }

        Log.d("FRASE", "Frase desde obtener--->" +  frase);


        cursor.close();
        db.close();

        return frase;
    }
}
