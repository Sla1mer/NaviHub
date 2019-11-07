package com.example.navigationmapbox;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IndoorNavigation extends AppCompatActivity {

    boolean flag = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        final ImageView imageButton1 = (ImageView) findViewById(R.id.Floor1);
        final ImageView imageButton2 = (ImageView) findViewById(R.id.Floor2);
        final ImageView imageButton3 = (ImageView) findViewById(R.id.Floor3);
        final ImageView imageButton4 = (ImageView) findViewById(R.id.Floor4);

        imageButton1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag){
                    imageButton2.setImageResource(R.drawable.etaj2);
                    imageButton1.setImageResource(R.drawable.etaj_button1);
                    imageButton3.setImageResource(R.drawable.etaj3);
                    imageButton4.setImageResource(R.drawable.etaj4);

                }
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag){
                    imageButton2.setImageResource(R.drawable.etaj_button2);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton3.setImageResource(R.drawable.etaj3);
                    imageButton4.setImageResource(R.drawable.etaj4);

                }
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag){
                    imageButton3.setImageResource(R.drawable.etaj_button3);
                    imageButton2.setImageResource(R.drawable.vniz2etaj);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton4.setImageResource(R.drawable.etaj4);
                }
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag){
                    imageButton3.setImageResource(R.drawable.vniz3etaj);
                    imageButton2.setImageResource(R.drawable.vniz2etaj);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton4.setImageResource(R.drawable.etaj_button4);

                }
            }
        });
    }
}
