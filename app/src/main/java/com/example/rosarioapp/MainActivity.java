package com.example.rosarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //---------------ATRIBUTOS-------------

    ImageView rosario;

    Bitmap rosarioBitmap;
    Bitmap copy;

    int colorRezo = Color.YELLOW;

    //Array para almacenar las posiciones de todas las oraciones
    ArrayList<Oraciones> oraciones = new ArrayList<Oraciones>();

    //Posiciones X e Y según dimensión de la imagen rosario.png (920x1024)
    int [] posXOraciones = {414,382,361,341,324,307,292,283,275,270,266,263,263,263,267,272,279,286,296,308,326,
    345,363,378,396,417,437,462,483,505,523,538,555,576,589,603,612,621,626,632,636,638,638,635,629,625,618,610,597,
    580,563,544,524,492,452,452,452,452,452};
    int [] posYOraciones = {679,665,644,625,600,570,533,508,475,443,414,379,346,313,284,255,225,195,166,139,109,83,
    64,49,38,29,25,26,30,39,50,64,82,112,138,162,194,222,250,280,309,341,374,414,444,469,502,528,564,594,620,641,661,
    676,750,778,803,825,852};

    //Pelotita que se colorea, ancho y alto
    int rezoActualAncho = 33;
    int rezoActualAlto = 33;

    int contRezos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rosario = (ImageView) findViewById(R.id.rosario);
        // Obtener el bitmap de la imagen actual en la ImageView
        rosarioBitmap = ((BitmapDrawable) rosario.getDrawable()).getBitmap();
        //copy permite cambiar imagen del mapa de bit
        copy = rosarioBitmap.copy(Bitmap.Config.ARGB_8888,true);

        //Función para agregar oraciones al vector oraciones, según los vectores posXOraciones, posYOraciones
        agregarOracion();

        MediaPlayer mp = MediaPlayer.create(this, R.raw.camapanas2);

        rosario.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent){

                        if (motionEvent.getAction() == motionEvent.ACTION_DOWN && contRezos<59) {
                            nuevaOracion(copy, contRezos);
                            mp.start();
                            contRezos++;
                        }


            return true;
            }
        });


    }


    //Colorea rezo actual
    public void nuevaOracion(Bitmap copy, int cont){

        Oraciones orar = oraciones.get(cont);
        //Colorea pixeles
        for (int i = orar.posX-15; i < orar.posX+33; i++) {
            for (int j = orar.posY-15; j < orar.posY+33; j++) {
                copy.setPixel(i, j, colorRezo);
            }


            // Establecer el bitmap modificado en la ImageView
            rosario.setImageBitmap(copy);
        }
    }

    public void agregarOracion(){
        int i = 0;
        int j = 0;
        while(i<posXOraciones.length&&j<posYOraciones.length){
            Oraciones orar = new Oraciones(posXOraciones[i]*2,posYOraciones[j]*2);
            oraciones.add(orar);
            i++;
            j++;
        }
    }

}