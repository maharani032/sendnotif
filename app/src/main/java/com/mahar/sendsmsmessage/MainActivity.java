package com.mahar.sendsmsmessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText inputNPhone,inputMessage;
    private TextView labelNPhone,labelMessage;
    private Button buttonSend;
    String userMessage;
    String userNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNPhone=findViewById(R.id.inputNPhone);
        labelNPhone=findViewById(R.id.labelNPhone);
        inputMessage=findViewById(R.id.inputMessage);
        labelMessage=findViewById(R.id.labelMessage);
        buttonSend=findViewById(R.id.buttonSend);

        inputNPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) labelNPhone.setVisibility(View.VISIBLE);
                else labelNPhone.setVisibility(View.INVISIBLE);

            }
        });
        inputMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) labelMessage.setVisibility(View.VISIBLE);
                else labelMessage.setVisibility(View.INVISIBLE);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userMessage=inputMessage.getText().toString();
                userNumber=inputNPhone.getText().toString();

                sendSMS(userMessage,userNumber);
            }
        });

    }

    public void sendSMS (String userMessage, String userNumber){
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.SEND_SMS},1);
        }else{
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(userNumber,null,userMessage,null,null);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==1 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(userNumber,null,userMessage,null,null);
        }
    }
}