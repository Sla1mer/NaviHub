package com.example.navigationmapbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.*;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.navigationmapbox.R;

public class BTService extends Activity {

    //based on java.util.UUID
    private static UUID MY_UUID = UUID.fromString("446118f0-8b1e-11e2-9e96-0800200c9a66");

    // The local server socket
    private BluetoothServerSocket mmServerSocket;

    // based on android.bluetooth.BluetoothAdapter
    private BluetoothAdapter mAdapter;
    private BluetoothDevice remoteDevice;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text = (TextView) findViewById(R.id.textView_Text);

        BluetoothSocket socket = null;
        mAdapter = BluetoothAdapter.getDefaultAdapter();

        // Listen to the server socket if we're not connected
        // while (true) {

        try {
            // Create a new listening server socket
            Log.d((String) this.getTitle(), ".....Initializing RFCOMM SERVER....");

            // MY_UUID is the UUID you want to use for communication
            mmServerSocket = mAdapter.listenUsingRfcommWithServiceRecord("MyService",    MY_UUID);
            //mmServerSocket =  mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, MY_UUID); // you can also try  using In Secure connection...

            // This is a blocking call and will only return on a
            // successful connection or an exception
            socket = mmServerSocket.accept();

        } catch (Exception e) {

        }

        byte[] buffer = new byte[256];  // buffer store for the stream
        int bytes; // bytes returned from read()
        try {
            Log.d((String) this.getTitle(), "Closing Server Socket.....");
            mmServerSocket.close();

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams

            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();

            DataInputStream mmInStream = new DataInputStream(tmpIn);
            DataOutputStream mmOutStream = new DataOutputStream(tmpOut);
            // here you can use the Input Stream to take the string from the client  whoever is connecting
            //similarly use the output stream to send the data to the client

            // Read from the InputStream
            bytes = mmInStream.read(buffer);
            String readMessage = new String(buffer, 0, bytes);
            // Send the obtained bytes to the UI Activity


            text.setText(readMessage);
        } catch (Exception e) {
            //catch your exception here
        }
        // }
    }
}