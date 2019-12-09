package com.example.navigationmapbox;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.BitmapFactory;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


import androidx.annotation.NonNull;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

// classes needed to initialize map
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;


/**
 * Add a GeoJSON line to a map.
 */

public class IndoorNavigation<TAG> extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener{

    private static final int REQ_ENABLE_BLUETOOTH = 1001;


    private MapView mapView;
    private List<Point> routeCoordinates;
    boolean flag = true;
    private MapboxMap mapboxMap;
    private static final String ICON_ID = "ICON_ID";
    Button btn_info;
    TextView myLabel;
    EditText myTextbox;
    Button vrNac;
    Button serverNac;
    Button vrKon;
    Button prepodavatelskayKon;
    Button prepodavatelskayKon2;
    TextView otpr;
    String  otpr2;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    public ImageView imageButton1;
    public ImageView imageButton2;
    public ImageView imageButton3;
    public ImageView imageButton4;
    String textServer = "server";
    String textVR = "VR";
    String nol;
    int counter;
    volatile boolean stopWorker;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    String text1 = number.getText().toString();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_indoor);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button openButton = (Button)findViewById(R.id.open);
        Button closeButton = (Button)findViewById(R.id.close);
        myLabel = (TextView)findViewById(R.id.label);
        otpr = (TextView)findViewById(R.id.otpr);
        vrNac = (Button) findViewById(R.id.vrNac);
        serverNac = (Button) findViewById(R.id.server);

        vrKon = (Button) findViewById(R.id.vrKon);
        prepodavatelskayKon = (Button) findViewById(R.id.prepodavatelskayaKon);

        prepodavatelskayKon2 = (Button) findViewById(R.id.prepodavatelskayaKon);

        ImageView imageButton1 = (ImageView) findViewById(R.id.Floor1);
        ImageView imageButton2 = (ImageView) findViewById(R.id.Floor2);
        ImageView imageButton3 = (ImageView) findViewById(R.id.Floor3);
        ImageView imageButton4 = (ImageView) findViewById(R.id.Floor4);

        serverNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpr.setText(textServer);
            }
        });

        vrNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpr.setText(textVR);
            }
        });

        vrKon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpr2 = otpr.getText().toString();
                if (otpr2 == textServer && flag){
                    onMapReady6(mapboxMap);
                    otpr.setText(nol);
                }else if(otpr2 == textVR){
                    Toast toast = Toast.makeText(getApplicationContext(), "Так нельзя простраивать маршрут!", Toast.LENGTH_SHORT);toast.show();
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберете начальную точку!", Toast.LENGTH_SHORT);toast.show();
                }
            }
        });

        prepodavatelskayKon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpr2 = otpr.getText().toString();
                if (otpr2 == textVR && flag){
                    onMapReady5(mapboxMap);
                    otpr.setText(nol);
                }else if(otpr2 == textServer && flag) {
                    onMapReady7(mapboxMap);
                    otpr.setText(nol);
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберете начальную точку!", Toast.LENGTH_SHORT);toast.show();
                }
            }
        });

//        serverNac.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                otpr2 = otpr.getText().toString();
//                if (otpr2 == textServer && flag){
//                    onMapReady7(mapboxMap);
//                    otpr.setText(nol);
//                }else {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Выберете начальную точку!", Toast.LENGTH_SHORT);toast.show();
//                }
//            }
//        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    onMapReady(mapboxMap);
                    imageButton2.setImageResource(R.drawable.etaj2);
                    imageButton1.setImageResource(R.drawable.etaj_button1);
                    imageButton3.setImageResource(R.drawable.etaj3);
                    imageButton4.setImageResource(R.drawable.etaj4);

                }
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    onMapReady2(mapboxMap);
                    imageButton2.setImageResource(R.drawable.etaj_button2);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton3.setImageResource(R.drawable.etaj3);
                    imageButton4.setImageResource(R.drawable.etaj4);

                }
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    onMapReady3(mapboxMap);
                    imageButton3.setImageResource(R.drawable.etaj_button3);
                    imageButton2.setImageResource(R.drawable.vniz2etaj);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton4.setImageResource(R.drawable.etaj4);
                }
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    onMapReady4(mapboxMap);
                    imageButton3.setImageResource(R.drawable.vniz3etaj);
                    imageButton2.setImageResource(R.drawable.vniz2etaj);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton4.setImageResource(R.drawable.etaj_button4);

                }
            }
        });

        //Open Button
        openButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    findBT();
                    openBT();
                }
                catch (IOException ex) { }
            }
        });

        //Close button
        closeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    closeBT();
                }
                catch (IOException ex) { }
            }
        });
    }

    public void findBT()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            myLabel.setText("No bluetooth adapter available");
        }

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("HC-06"))
                {
                    mmDevice = device;
                    break;
                }
            }
        }
        myLabel.setText("Bluetooth Device Found");
    }

    public void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        beginListenForData();

        myLabel.setText("Bluetooth Opened");
    }

    public void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run() {
                                            tochka11();
                                            myLabel.setText(data);

                                            data2 = data;

                                            switch (data) {
                                                case ("1692242104"):
                                                    tochka1();
                                                    break;
                                                case ("1691511704"):
                                                    tochka2();
                                                    break;
                                                case ("10286209209"):
                                                    tochka3();
                                                    break;
                                                case ("57412204"):
                                                    tochka4();
                                                    break;
                                                case ("5431214101"):
                                                    tochka5();
                                                    onMapReady3(mapboxMap);
                                                    break;
                                                case ("2171562004"):
                                                    tochka6();
                                                    break;
                                                case ("2011261904"):
                                                    tochka7();
                                                    break;
                                                case ("572161604"):
                                                    tochka8();
                                                    onMapReady(mapboxMap);
                                                    break;
                                                case ("412131604"):
                                                    tochka9();
                                                    break;
                                                case ("86166248208"):
                                                    tochka10();
                                                    break;
                                                case ("2331772304"):
                                                    tochka11();
                                                    break;
                                                case ("252431704"):
                                                    tochka13();
                                                    break;
                                                case ("1691292104"):
                                                    tochka14();
                                                    break;
                                                case ("134207183245"):
                                                    tochka15();
                                                    break;
                                                case ("86213211209"):
                                                    tochka16();
                                                    break;
                                                case ("572362404"):
                                                    tochka17();
                                                    break;
                                                case ("246131217209"):
                                                    tochka18();
                                                    onMapReady2(mapboxMap);
                                                    break;
                                                case ("2171971904"):
                                                    tochka19();
                                                    onMapReady4(mapboxMap);
                                                    break;
                                                    default:
                                                        mapboxMap.clear();
                                            }

                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }

                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }


    void closeBT() throws IOException
    {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }

    public String data2;

    public void tochka1(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736160, 20.491523));
        mapboxMap.addMarker(options);

    }
    public void tochka2(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736135, 20.491510));
        mapboxMap.addMarker(options);
    }

    public void tochka3(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736092, 20.491493));
        mapboxMap.addMarker(options);
    }

    public void tochka4(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736071, 20.491480));
        mapboxMap.addMarker(options);
    }

    public void tochka5(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736036, 20.491463));
        mapboxMap.addMarker(options);
    }

    public void tochka6(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736020, 20.491456));
        mapboxMap.addMarker(options);
    }

    public void tochka7(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.735990, 20.491443));
        mapboxMap.addMarker(options);
    }

    public void tochka8() {
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.735967, 20.491426));
        mapboxMap.addMarker(options);

            routeCoordinates = new ArrayList<>();
            routeCoordinates.add(Point.fromLngLat(20.491480, 54.736071));
            routeCoordinates.add(Point.fromLngLat( 20.491392, 54.736093));
            routeCoordinates.add(Point.fromLngLat(20.490940, 54.736158));
    }

    public void tochka9(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.735936, 20.491413));
        mapboxMap.addMarker(options);
    }

    public void tochka10(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.735908, 20.491403));
        mapboxMap.addMarker(options);
    }

    public void tochka11(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.735919, 20.491303));
        mapboxMap.addMarker(options);
    }

    public void tochka12(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.735965, 20.491314));
        mapboxMap.addMarker(options);
    }

    public void tochka13(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736093, 20.491392));
        mapboxMap.addMarker(options);

            routeCoordinates = new ArrayList<>();
            routeCoordinates.add(Point.fromLngLat( 20.491392, 54.736093));
            routeCoordinates.add(Point.fromLngLat(20.490940, 54.736158));
    }

    public void tochka14(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736104, 20.491309));
        mapboxMap.addMarker(options);
    }

    public void tochka15(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736116, 20.491220));
        mapboxMap.addMarker(options);
    }

    public void tochka16(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736130, 20.491137));
        mapboxMap.addMarker(options);
    }

    public void tochka17(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736138, 20.491065));
        mapboxMap.addMarker(options);
    }

    public void tochka18(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736148, 20.490999));
        mapboxMap.addMarker(options);
    }

    public void tochka19(){
        mapboxMap.clear();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(54.736158, 20.490940));
        mapboxMap.addMarker(options);
    }

    public void onMapReady4(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant4), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
    }

    public void onMapReady2(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant2), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
    }

    public void onMapReady3(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant3), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
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

    public void onMapReady5(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(getString(R.string.kvant4), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                initRouteCoordinates();

                // Create the LineString from the list of coordinates and then make a GeoJSON
                // FeatureCollection so we can add the line to our map as a layer.
                style.addSource(new GeoJsonSource("line-source",
                        FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(
                                LineString.fromLngLats(routeCoordinates)
                        )})));

                // The layer properties for our line. This is where we make the line dotted, set the
                // color, etc.
                style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                        PropertyFactory.lineDasharray(new Float[]{0.01f, 2f}),
                        PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                        PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                        PropertyFactory.lineWidth(5f),
                        PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
                ));
            }
        });
    }

    public void onMapReady6(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(getString(R.string.kvant4), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                initRouteCoordinates2();

                // Create the LineString from the list of coordinates and then make a GeoJSON
                // FeatureCollection so we can add the line to our map as a layer.
                style.addSource(new GeoJsonSource("line-source",
                        FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(
                                LineString.fromLngLats(routeCoordinates)
                        )})));

                // The layer properties for our line. This is where we make the line dotted, set the
                // color, etc.
                style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                        PropertyFactory.lineDasharray(new Float[]{0.01f, 2f}),
                        PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                        PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                        PropertyFactory.lineWidth(5f),
                        PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
                ));
            }
        });
    }

    public void onMapReady7(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(getString(R.string.kvant4), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                initRouteCoordinates3();

                // Create the LineString from the list of coordinates and then make a GeoJSON
                // FeatureCollection so we can add the line to our map as a layer.
                style.addSource(new GeoJsonSource("line-source",
                        FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(
                                LineString.fromLngLats(routeCoordinates)
                        )})));

                // The layer properties for our line. This is where we make the line dotted, set the
                // color, etc.
                style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                        PropertyFactory.lineDasharray(new Float[]{0.01f, 2f}),
                        PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                        PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                        PropertyFactory.lineWidth(5f),
                        PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
                ));
            }
        });
    }

    public void initRouteCoordinates(){
        routeCoordinates = new ArrayList<>();
        routeCoordinates.add(Point.fromLngLat( 20.491065, 54.736138));
        routeCoordinates.add(Point.fromLngLat(  20.491480, 54.736071));
        routeCoordinates.add(Point.fromLngLat( 20.491443, 54.735990));
        routeCoordinates.add(Point.fromLngLat(20.491403, 54.735908));
        routeCoordinates.add(Point.fromLngLat( 20.491303, 54.735919));
    }

    public void initRouteCoordinates2(){
        routeCoordinates = new ArrayList<>();
        routeCoordinates.add(Point.fromLngLat(  20.491510, 54.736135));
        routeCoordinates.add(Point.fromLngLat( 20.491493, 54.736092));
        routeCoordinates.add(Point.fromLngLat(  20.491480, 54.736071));
        routeCoordinates.add(Point.fromLngLat( 20.491065, 54.736138));
    }

    public void initRouteCoordinates3(){
        routeCoordinates = new ArrayList<>();
        routeCoordinates.add(Point.fromLngLat( 20.491510, 54.736135));
        routeCoordinates.add(Point.fromLngLat( 20.491493, 54.736092));
        routeCoordinates.add(Point.fromLngLat( 20.491403, 54.735908));
        routeCoordinates.add(Point.fromLngLat( 20.491303, 54.735919));

    }


//    private void initRouteCoordinates2() {
//// Create a list to store our line coordinates.
//        routeCoordinates = new ArrayList<>();
//        routeCoordinates.add(Point.fromLngLat( 20.491392, 54.736093));
//        routeCoordinates.add(Point.fromLngLat(20.490940, 54.736158));
//    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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

    public void onMyButtonClick(View view) {
        Intent intent = new Intent(IndoorNavigation.this, MainActivity.class);
        startActivity(intent);
    }

    public void onMyButtonClick2(View view) {
        onStart();
    }

    public void onMyButtonClickPanel(View view) {
        Intent panel = new Intent(IndoorNavigation.this, PopActivity.class);
        startActivity(panel);
    }
}
    
//        //based on java.util.UUID
//        private static UUID MY_UUID = UUID.fromString("446118f0-8b1e-11e2-9e96-0800200c9a66");
//
//        // The local server socket
//        private BluetoothServerSocket mmServerSocket;
//
//        // based on android.bluetooth.BluetoothAdapter
//        private BluetoothAdapter mAdapter;
//        private BluetoothDevice remoteDevice;
//        TextView text;
//
//        protected void onCreate2(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//            //text = (TextView) findViewById(R.id.textView_Text);
//
//            BluetoothSocket socket = null;
//            mAdapter = BluetoothAdapter.getDefaultAdapter();
//
//            // Listen to the server socket if we're not connected
//            // while (true) {
//
//            try {
//                // Create a new listening server socket
//                Log.d((String) this.getTitle(), ".....Initializing RFCOMM SERVER....");
//
//                // MY_UUID is the UUID you want to use for communication
//                mmServerSocket = mAdapter.listenUsingRfcommWithServiceRecord("MyService",    MY_UUID);
//                //mmServerSocket =  mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, MY_UUID); // you can also try  using In Secure connection...
//
//                // This is a blocking call and will only return on a
//                // successful connection or an exception
//                socket = mmServerSocket.accept();
//
//            } catch (Exception e) {
//
//            }
//
//            byte[] buffer = new byte[256];  // buffer store for the stream
//            int bytes; // bytes returned from read()
//            try {
//                Log.d((String) this.getTitle(), "Closing Server Socket.....");
//                mmServerSocket.close();
//
//                InputStream tmpIn = null;
//                OutputStream tmpOut = null;
//
//                // Get the BluetoothSocket input and output streams
//
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//
//                DataInputStream mmInStream = new DataInputStream(tmpIn);
//                DataOutputStream mmOutStream = new DataOutputStream(tmpOut);
//                // here you can use the Input Stream to take the string from the client  whoever is connecting
//                //similarly use the output stream to send the data to the client
//
//                // Read from the InputStream
//                bytes = mmInStream.read(buffer);
//                String readMessage = new String(buffer, 0, bytes);
//                // Send the obtained bytes to the UI Activity
//
//
//                text.setText(readMessage);
//            } catch (Exception e) {
//                //catch your exception here
//            }
//            // }
//        }
//    }






/*
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

        final ImageView imageButton1 = (ImageView) findViewById(R.id.Floor1);
        final ImageView imageButton2 = (ImageView) findViewById(R.id.Floor2);
        final ImageView imageButton3 = (ImageView) findViewById(R.id.Floor3);
        final ImageView imageButton4 = (ImageView) findViewById(R.id.Floor4);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    onMapReady(mapboxMap);
                    imageButton2.setImageResource(R.drawable.etaj2);
                    imageButton1.setImageResource(R.drawable.etaj_button1);
                    imageButton3.setImageResource(R.drawable.etaj3);
                    imageButton4.setImageResource(R.drawable.etaj4);

                }
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    oneSyle2(mapboxMap);
                    imageButton2.setImageResource(R.drawable.etaj_button2);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton3.setImageResource(R.drawable.etaj3);
                    imageButton4.setImageResource(R.drawable.etaj4);

                }
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
                    oneSyle2(mapboxMap);
                    imageButton3.setImageResource(R.drawable.etaj_button3);
                    imageButton2.setImageResource(R.drawable.vniz2etaj);
                    imageButton1.setImageResource(R.drawable.vniz1etaj);
                    imageButton4.setImageResource(R.drawable.etaj4);
                }
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // меняем изображение на кнопке
                if (flag) {
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

 */
