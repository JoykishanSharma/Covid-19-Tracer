package com.joyappsdevteam.covid_19tracer.settings_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.joyappsdevteam.covid_19tracer.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView email_developer = findViewById(R.id.email_developer);
        ImageView back_button = findViewById(R.id.back_arrow8);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Email the Developer using Intent
        email_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,new String[] {"joyappsdevteam@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"Query Related to \"Covid-19 Tracer App\"");
                i.putExtra(Intent.EXTRA_TEXT, "Email message here...");
                try {
                    startActivity(Intent.createChooser(i,"Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(AboutUsActivity.this,"There is no email client installed.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}