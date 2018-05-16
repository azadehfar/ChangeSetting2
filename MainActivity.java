package com.adel.changesetting;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    boolean GpsStatus;
    Context context;
    Button Location_button;
    TextView Location_status;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    ToggleButton Wifi_switch;
    WifiManager wifiManager;
    TextView Wifi_status;
    Switch BluetoothButton;
    TextView BluetoothText;
    BluetoothAdapter bluetoothadapter;
    Intent BluetoothIntent;
    int REQ_BLUETOOTH = 100;

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "Resume to Application", Toast.LENGTH_SHORT).show();
        CheckBlutoothStatus();
        CheckWIFIStatus();
        CheckGpsStatus();
    }

  /*  @Override
    public void onStart(){
        super.onStart();
        Toast.makeText(MainActivity.this, "Start Application", Toast.LENGTH_SHORT).show();

    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bindvariables();

        ///////////////////////////////Blutooth/////////////////////////////////////

        CheckBlutoothStatus();

        BluetoothButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ChangeBlutoothStatus(true);
                else
                    ChangeBlutoothStatus(false);

            }
        });


        ///////////////////////////////WIFI/////////////////////////////////////

        CheckWIFIStatus();
        Wifi_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked)
                    ChangeWIFIStatus(true);
                else
                    ChangeWIFIStatus(false);
            }
        });


        ///////////////////////////////GPS/////////////////////////////////////

        CheckGpsStatus();
        Location_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChangeGpsStatus(true);
            }
        });

    }


    public void ChangeBlutoothStatus(boolean status) {

        if (status) {
            Intent bluetoothadapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothadapter, REQ_BLUETOOTH);
            BluetoothText.setText(R.string.bluetoothon);
        } else {
            bluetoothadapter.disable();
            BluetoothText.setText(R.string.bluetoothoff);
        }
    }

    public void CheckBlutoothStatus() {

        if (bluetoothadapter.isEnabled()) {
            BluetoothText.setText(R.string.bluetoothon);
            BluetoothButton.setChecked(true);
        } else {
            BluetoothText.setText(R.string.bluetoothoff);
            BluetoothButton.setChecked(false);
        }
    }


    public void CheckWIFIStatus() {


        if (wifiManager.isWifiEnabled()) {
            Wifi_status.setText(R.string.wifion);
            Wifi_switch.setChecked(true);
        } else {
            Wifi_status.setText(R.string.wifioff);
            Wifi_switch.setChecked(false);
        }

    }


    public void ChangeWIFIStatus(boolean status) {

        if (status) {
            Wifi_status.setText(R.string.wifion);
            Wifi_switch.setChecked(true);
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
        } else {
            Wifi_status.setText(R.string.wifioff);
            Wifi_switch.setChecked(false);
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
        }
        //CheckWIFIStatus();
    }


    public void ChangeGpsStatus(boolean status) {

        if (status) {
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!enabled) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                CheckGpsStatus();
            }

        }
    }

    public void CheckGpsStatus() {
        Location_button = (Button) findViewById(R.id.Location_button);
        Location_status = (TextView) findViewById(R.id.Location_status);

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled == true && network_enabled) {
            Location_status.setText(R.string.gpson);
            //Location_button.setEnabled(false);
            //Toast.makeText(MainActivity.this, "Location service is enable!", Toast.LENGTH_SHORT).show();

        } else {
            Location_status.setText(R.string.gpsoff);
            //Location_button.setEnabled(true);
            //Toast.makeText(MainActivity.this, "Location service is disable!", Toast.LENGTH_SHORT).show();
        }
    }


    public void Bindvariables() {
        Wifi_switch = (ToggleButton) findViewById(R.id.Wifi_switch);
        Wifi_status = (TextView) findViewById(R.id.Wifi_status);
        Location_button = (Button) findViewById(R.id.Location_button);
        BluetoothButton = (Switch) findViewById(R.id.Bluetooth_switch);
        BluetoothText = (TextView) findViewById(R.id.Bluetooth_status);
        bluetoothadapter = BluetoothAdapter.getDefaultAdapter();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
    }


}
