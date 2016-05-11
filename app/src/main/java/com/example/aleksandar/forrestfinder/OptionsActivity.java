package com.example.aleksandar.forrestfinder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class OptionsActivity extends AppCompatActivity {

    Button addNewLevel;
    Button sound;
    Button language;
    ImageView slicica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //slicica = (ImageView) findViewById(R.id.slikaJEBENA);

        addNewLevel = (Button) findViewById(R.id.addNewLevel);
        addNewLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 100);
            }
        });

        sound = (Button) findViewById(R.id.sound);
        language = (Button) findViewById(R.id.language);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("slicica ", " request code " + requestCode);
        Log.d("slicica", " resultCode " + resultCode);

        Uri uri;

        if (data != null) {
            uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                //slicica.setImageBitmap(bitmap);
                //save to disk
                String name = saveToInternalStorage(bitmap);

                Intent intent = new Intent(getApplicationContext(), AddNewLevelActivity.class);
                intent.putExtra("imeSlike", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                //////////////
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"user_gen.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert fos != null;
            fos.close();
        }
        return directory.getAbsolutePath();
    }
}
