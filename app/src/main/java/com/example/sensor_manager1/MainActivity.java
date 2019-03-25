package com.example.sensor_manager1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements  SensorEventListener{

    private SensorManager mSensorManager;

    private Sensor mlinear_accelometer;
    private Sensor mambient_lightmeter;

    private TextView x;
    private TextView y;
    private TextView z;
    private TextView light;

    private Button  btn1;
    private Button  btn2;

    private Button  btn3;
    private Button  btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mlinear_accelometer= mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mambient_lightmeter= mSensorManager.getDefaultSensor(
                Sensor.TYPE_LIGHT);

        setContentView(R.layout.activity_main2);
        x=(TextView)findViewById(R.id.textView5);
        y=(TextView)findViewById(R.id.textView6);

        z=(TextView)findViewById(R.id.textView7);

        light=(TextView)findViewById(R.id.textView8);
        //setContentView(mGLSurfaceView);

        btn1= findViewById(R.id.button);
        btn2= findViewById(R.id.button2);

        btn3= findViewById(R.id.button3);
        btn4= findViewById(R.id.button4);

        x.setText("0");
        y.setText("0");
        z.setText("0");

        light.setText("0");

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                stop();
            }
        });



        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Light_sensor_start();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Light_sensor_stop();
            }
        });
        //this.start();
    }


    public void start() {
        // enable our sensor when the activity is resumed, ask for
        // 10 ms updates.
        mSensorManager.registerListener(this, mlinear_accelometer, 10000);
    }
    public void stop() {
        // make sure to turn our sensor off when the activity is paused
        mSensorManager.unregisterListener(this);
    }

    public void Light_sensor_start() {
        // enable our sensor when the activity is resumed, ask for
        // 10 ms updates.
        mSensorManager.registerListener(this, mambient_lightmeter, 10000);
    }
    public void Light_sensor_stop() {
        // make sure to turn our sensor off when the activity is paused
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // we received a sensor event. it is a good practice to check
        // that we received the proper event
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // alpha is calculated as t / (t + dT)
            // with t, the low-pass filter's time-constant
            // and dT, the event delivery rate

            final double alpha = 0.8;
            double[] gravity=new double[3];
            double[] linear_acceleration=new double[3];

            gravity[0] = (1 - alpha) * event.values[0];
            gravity[1] =  (1 - alpha) * event.values[1];
            gravity[2] =  (1 - alpha) * event.values[2];

            linear_acceleration[0] = event.values[0] ;
            linear_acceleration[1] = event.values[1] ;
            linear_acceleration[2] = event.values[2] ;

            x.setText(Double.toString(linear_acceleration[0]));
            y.setText(Double.toString(linear_acceleration[1]));
            z.setText(Double.toString(linear_acceleration[2]));
        }

        else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            light.setText(Double.toString(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





}
