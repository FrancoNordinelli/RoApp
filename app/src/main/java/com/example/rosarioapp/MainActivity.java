package com.example.rosarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    ImageView rosario;


    //Pelotita que se colorea
    int rezoActualAncho = 33;
    int rezoActualAlto = 33;

    //PRIMERA ORACION TODO: ADAPTAR A CUALQUIER DISPOSITIVO
    int pelotitaX = (414 * 720) /920;
    int pelotitaY = (679 * 1280) /1024;

    int colorRezo = Color.YELLOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rosario = (ImageView) findViewById(R.id.rosario);

        //rosario.setImageResource(R.drawable.rosario);


        // Obtener el bitmap de la imagen actual en la ImageView
        Bitmap rosarioBitmap = ((BitmapDrawable) rosario.getDrawable()).getBitmap();
        Bitmap copy = rosarioBitmap.copy(Bitmap.Config.ARGB_8888,true);
        nuevaOracion(copy);

    }
    public void nuevaOracion(Bitmap copy){
        for (int i = pelotitaX; i < pelotitaX+33; i++) {
            for (int j = pelotitaY; j < pelotitaY+33; j++) {
                copy.setPixel(i, j, Color.YELLOW);
            }


            // Establecer el bitmap modificado en la ImageView
            rosario.setImageBitmap(copy);
        }
    }
}