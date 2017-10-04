package com.anonymous.guesslogo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //private final String TAG = this.getClass().getSimpleName();
    EditText companyName;
    Button submitButton, helpButton;
    TextView levelText, scoreText;
    String img;
    HashMap<String, Integer> images;
    ArrayList<String> imgName;
    QuadTreeImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final QuadTreeImageView imageView;
        getSupportActionBar().setTitle("Guess Logo");
        images = new HashMap<>();

        images.put("adidas", R.drawable.adidas);
        images.put("android", R.drawable.android);
        images.put("apple", R.drawable.apple);
        images.put("bitcoin", R.drawable.bitcoin);
        images.put("blogger", R.drawable.blogger);
        images.put("bmw", R.drawable.bmw);
        images.put("dell", R.drawable.dell);
        images.put("fedex", R.drawable.fedex);
        images.put("firefox", R.drawable.firefox);
        images.put("ford", R.drawable.ford);
        images.put("github", R.drawable.github);
        images.put("google", R.drawable.google);
        images.put("googleallo", R.drawable.googleallo);
        images.put("ibm", R.drawable.ibm);
        images.put("intel", R.drawable.intel);
        images.put("mcdonald", R.drawable.mcd);
        images.put("microsoft", R.drawable.microsoft);
        images.put("mitsubishi", R.drawable.mitsubishi);
        images.put("nike", R.drawable.nike);
        images.put("puma", R.drawable.puma);
        images.put("spotify", R.drawable.spotify);
        images.put("uber", R.drawable.uber);
        images.put("wordpress", R.drawable.wordpress);


        imgName = new ArrayList<>();
        imgName.add("adidas");
        imgName.add("android");
        imgName.add("apple");
        imgName.add("bitcoin");
        imgName.add("blogger");
        imgName.add("bmw");
        imgName.add("dell");
        imgName.add("fedex");
        imgName.add("firefox");
        imgName.add("ford");
        imgName.add("github");
        imgName.add("google");
        imgName.add("googleallo");
        imgName.add("ibm");
        imgName.add("intel");
        imgName.add("mcdonald");
        imgName.add("microsoft");
        imgName.add("mitsubishi");
        imgName.add("nike");
        imgName.add("puma");
        imgName.add("spotify");
        imgName.add("uber");
        imgName.add("wordpress");

        companyName = (EditText) findViewById(R.id.companyNameText);
        submitButton = (Button) findViewById(R.id.submitButton);
        levelText = (TextView) findViewById(R.id.levelText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        helpButton = (Button) findViewById(R.id.helpButton);
        displayImg();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guessedName = companyName.getText().toString().toLowerCase();
                if (guessedName.equals(img)) {
                    Toast.makeText(MainActivity.this, "Correct Answer!!!", Toast.LENGTH_SHORT).show();
                    int level = Integer.parseInt(levelText.getText().toString());
                    level += 1;
                    String l = Integer.toString(level);
                    levelText.setText(l);
                    int score = Integer.parseInt(scoreText.getText().toString());
                    score += 10;
                    String s = Integer.toString(score);
                    scoreText.setText(s);
                    companyName.setText("");
                    images.remove(img);
                    imgName.remove(img);
                    displayImg();
                } else {
                    Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    companyName.setText("");
                }
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                options.inMutable = true;
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), images.get(img), options);
                imageView.setImageBitmap(bmp);
                int score = Integer.parseInt(scoreText.getText().toString());
                score -= 20;
                String s = Integer.toString(score);
                scoreText.setText(s);

                Toast.makeText(MainActivity.this, "Correct Answer is : " + img, Toast.LENGTH_SHORT).show();
                images.remove(img);
                imgName.remove(img);
                displayImg();
            }
        });
    }

    public void displayImg() {
        //img = imgName.get(new Random().nextInt(images.size()));
        if (imgName.size() > 0) {
            img = imgName.get(new Random().nextInt(images.size()));
            Bitmap mutableBitmap;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inMutable = true;
            final Bitmap bm = BitmapFactory.decodeResource(getResources(), images.get(img), options);
            mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);

            QuadTreeSplitter quadTreeSplitter = new QuadTreeSplitter(mutableBitmap, new OnQuadTreeSplitComplete() {
                @Override
                public void onSplitComplete(List<QuadTreeRect> quadTreeRects) {
                    //Log.d(TAG, "onSplitComplete: " + quadTreeRects.size());
                }
            });
            quadTreeSplitter.start();
            quadTreeSplitter.setMinQuadAreaSize(1050);
            quadTreeSplitter.setMinColorDistance(5);

            imageView = (QuadTreeImageView) findViewById(R.id.qtImgView);
            quadTreeSplitter.setOnQuadDrawListener(imageView);
            imageView.setDrawGreed(false);
            imageView.setImageBitmap(mutableBitmap);
            while (quadTreeSplitter.isAlive() == true) ;
        } else {
            Toast.makeText(MainActivity.this, "Congratulations!!! You completed the game", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

}
