package com.example.navigationmapbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import java.util.List;
import android.widget.Toast;

// classes needed to initialize map
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class IndoorNavigation extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    boolean flag = true;
    private MapView mapView;
    private MapboxMap mapboxMap;
    Button btn_info;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_indoor);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btn_info = (Button) findViewById(R.id.infoPanel);

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PopActivity.class);
                startActivity(i);
            }
        });


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
                    onMapReady(mapboxMap);
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
                    oneSyle2(mapboxMap);
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
                    oneSyle2(mapboxMap);
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
                    oneSyle(mapboxMap);
                    imageButton3.setImageResource(R.drawable.vniz3etaj);
                    imageButton2.setImageResource(R.drawable.vniz2etaj);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton4.setImageResource(R.drawable.etaj_button4);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(getString(R.string.kvant1), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    }
                });
    }

    public void oneSyle(@NonNull final MapboxMap mapboxMap){
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant4), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
    }

    public void oneSyle2 (@NonNull final MapboxMap mapboxMap){
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant2), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
    }

    public void oneSyle3 (@NonNull final MapboxMap mapboxMap){
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant3), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    public void onMyButtonClick(View view)
    {
        Intent intent = new Intent(IndoorNavigation.this, MainActivity.class);
        startActivity(intent);
    }
}
