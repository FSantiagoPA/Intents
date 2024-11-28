package com.parrarivera.intents;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private EditText edPhoneNumber;
    private ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button openWeb = findViewById(R.id.open_web);
        Button openCountryList = findViewById(R.id.open_CountryList);
        Button openPhoneCall = findViewById(R.id.phone_call);
        Button openGoogleMaps = findViewById(R.id.google_maps);
        Button openPhoto = findViewById(R.id.photo);
        Button openEmail = findViewById(R.id.email);

        openWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.liverpoolfc.com/"));
                startActivity(intent);
            }
        });

        openCountryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.parrarivera.recyclerviewcountries", "com.parrarivera.recyclerviewcountries.MainActivity"));
                startActivity(intent);
            }
        });

        openPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               phoneCall("641655697");
            }
        });

        openGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4194?z=17"));
                startActivity(intent);
            }
        });

        openPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        openEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }

    private void phoneCall(String telephoneNumber){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + telephoneNumber));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                phoneCall(edPhoneNumber.getText().toString());
            }
        }
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("message/rfc822");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"LuizDiazcol@gmail.com"}); // Dirección del destinatario
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Intent prueba"); // Asunto
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hoja de Texto"); // Comentario o texto que resivira el destinatario

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Elige una aplicación de correo"));
        } else {
            Toast.makeText(this, "No existe ninguna aplicación que pueda ejecutar esta acción", Toast.LENGTH_SHORT).show();
        }
    }
}