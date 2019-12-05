package com.example.navigationmapbox;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


import static android.R.layout.simple_list_item_1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// classes needed to initialize map
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.utils.PolylineUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.ColorUtils;

import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;




/**
 * Add a GeoJSON line to a map.
 */

public class IndoorNavigation<TAG> extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private static final int REQ_ENABLE_BLUETOOTH = 1001;


    private MapView mapView;
    private List<Point> routeCoordinates;
    boolean flag = true;
    private MapboxMap mapboxMap;
    Button btn_info;
    TextView myLabel;
    EditText myTextbox;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

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

    void findBT()
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

    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        beginListenForData();

        myLabel.setText("Bluetooth Opened");
    }

    void beginListenForData()
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
                                        public void run()
                                        {
                                            myLabel.setText(data);
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

    void sendData() throws IOException
    {
        String msg = myTextbox.getText().toString();
        msg += "\n";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Data Sent");
    }

    void closeBT() throws IOException
    {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }

    public void onMapReady4(@NonNull final MapboxMap mapboxMap) {
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

    public void onMapReady2(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant2), new Style.OnStyleLoaded() {
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

    public void onMapReady3(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant3), new Style.OnStyleLoaded() {
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

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.kvant1), new Style.OnStyleLoaded() {
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

    private void initRouteCoordinates() {
// Create a list to store our line coordinates.
        routeCoordinates = new ArrayList<>();
        routeCoordinates.add(Point.fromLngLat(20.491051148290637, 54.73608038516162));
        routeCoordinates.add(Point.fromLngLat(20.491018129381587, 54.73608109627289));
        routeCoordinates.add(Point.fromLngLat(20.49104876741069, 54.73602434063259));
    }

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
        Intent intent = new Intent(IndoorNavigation.this, Bluetooth.class);
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
