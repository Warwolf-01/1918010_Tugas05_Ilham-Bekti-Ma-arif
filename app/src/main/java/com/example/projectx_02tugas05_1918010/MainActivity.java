package com.example.projectx_02tugas05_1918010;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.TextView);
        //memunculkan dialog timepicker menggunakan library dariandroid
        Button buttonTimePicker = (Button)
                findViewById(R.id.button_buat);
        buttonTimePicker.setOnClickListener(new
                                                    View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            DialogFragment timePicker = new TimePickerFragment();
                                                            timePicker.show(getSupportFragmentManager(),"Time Picker");
                                                        }
                                                    });
        //untuk menggagalkan alarm yang sudah disetel
        Button buttonCancelAlarm =
                findViewById(R.id.button_batal);
        buttonCancelAlarm.setOnClickListener(new
                                                     View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) { cancelAlarm();
                                                         }
                                                     });
    }
    //menangkap inputan jam kalian lalu memulai alarm
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int
            minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        updateTimeText(c);
        startAlarm(c);
    }
    //mengganti text view
    private void updateTimeText(Calendar c){
        String timeText = "Alarm set for: ";
        timeText +=
                DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        mTextView.setText(timeText);
    }
    //memulai alarm
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this,1,intent,0);
        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()
                ,pendingIntent);
    }
    //menggagalkan alarm
    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm Canceled");
    }
}
