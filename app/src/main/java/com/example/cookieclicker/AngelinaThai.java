package com.example.cookieclicker;

import static java.lang.Thread.sleep;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AngelinaThai extends AppCompatActivity {

    ImageView calcifer;
    Button clicks, passiveMoney;
    TextView cookieCount, plusCookies;
    RelativeLayout relativeLayout;
    AtomicInteger count = new AtomicInteger();
    int num = 1;
    int cost = 10;
    int passiveCost = 10;
    int passiveAddition = 1;
    ArrayList<View> views = new ArrayList<View>();
    AnimationDrawable animationDrawable;
    String purchaseName = "";
    ArrayList<String> purchaseList = new ArrayList<String>();
    ListView listView;
    MediaPlayer media;
    ImageView purchasePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcifer = findViewById(R.id.calcifer);
        clicks = findViewById(R.id.clicks);
        passiveMoney = findViewById(R.id.automaticMoney);
        cookieCount = findViewById(R.id.cookieCount);
        relativeLayout = findViewById(R.id.relativeLayout);
        listView = findViewById(R.id.listView);

        media = MediaPlayer.create(this, R.raw.song);

        count.set(0);

        CustomAdapter adapter = new CustomAdapter(this, R.layout.adapter_layout, purchaseList);
        listView.setAdapter(adapter);

        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        media.start();
        media.setLooping(true);

        final ScaleAnimation animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);

        //passive income part
        passiveIncome();

        calcifer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlus();
                TranslateAnimation animate = new TranslateAnimation(0, 0, plusCookies.getHeight(), plusCookies.getHeight() - 50);
                animate.setDuration(150);
                calcifer.startAnimation(animation);
                count.set(count.get() + num);
                plusCookies.setText("+" + num);
                cookieCount.setText(count + " Fireballs");

                plusCookies.startAnimation(animate);

                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(views.get(0));
                        views.remove(0);
                    }
                }, 400);

            }

        }); //closes imageView

        clicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count.get() - cost < 0) {
                    Toast.makeText(AngelinaThai.this, "YOU NEED MORE FIREBALLS", Toast.LENGTH_SHORT).show();
                } else {
                    num++;
                    count.set(count.get() - cost);
                    purchaseName = "Clicks: $" + cost;
                    plusCookies.setText("+" + num);
                    cookieCount.setText(count + " Fireballs");
                    cost *= 4;
                    clicks.setText("Clicks: $" + cost);
                    purchaseList.add(purchaseName);

                    purchaseAnimation();

                }
            }
        });

        passiveMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count.get() - passiveCost < 0) {
                    Toast.makeText(AngelinaThai.this, "YOU NEED MORE FIREBALLS", Toast.LENGTH_SHORT).show();
                } else {
                    count.set(count.get() - passiveCost);
                    purchaseName = "Passive Income: $" + passiveCost;
                    passiveCost *= 4;
                    passiveAddition++;
                    passiveMoney.setText("Passive Income: $" + passiveCost);
                    purchaseList.add(purchaseName);

                    purchaseAnimation();
                }
            }
        });

    }//closes onCreate

    public void createPlus() {
        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Instantiate View to be added to the Layout from Java Code (not XML)
        plusCookies = new TextView(this);
        plusCookies.setId(View.generateViewId());
        plusCookies.setTextColor(Color.RED);
        plusCookies.setTextSize(20);

        int randomLeft = (int) (Math.random() * 570) + 220;
        int randomTop = (int) (Math.random() * 170) + 650;
        textViewParams.setMargins(randomLeft, randomTop, 0, 0);
        relativeLayout.addView(plusCookies, textViewParams);
        views.add(plusCookies);
    }

    public void passiveIncome() {
        Handler autoClick = new Handler();
        autoClick.postDelayed(new Runnable() {
            @Override
            public void run() {
                count.set(count.get() + passiveAddition);
                autoClick.postDelayed(this, 1000); // Auto-click every second
                cookieCount.setText(count + " Fireballs");
            }
        }, 1000);
    }

    public void purchaseAnimation() {
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        purchasePic = new ImageView(this);
        purchasePic.setId(View.generateViewId());

        if (purchaseName.substring(0,1).equals("C")) {
            purchasePic.setImageResource(R.drawable.flame);
            purchasePic.setScaleY(0.50F);
            purchasePic.setScaleX(0.50F);
            imageViewParams.setMargins(20, 125, 0, 0);
        }
        else if (purchaseName.substring(0,1).equals("P")) {
            purchasePic.setImageResource(R.drawable.money);
            purchasePic.setScaleY(0.25F);
            purchasePic.setScaleX(0.25F);
            imageViewParams.setMargins(20, -800, 0, 0);

        }

        relativeLayout.addView(purchasePic, imageViewParams);

        final RotateAnimation animation = new RotateAnimation(0f, 360.0f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(400);

        purchasePic.startAnimation(animation);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.removeView(purchasePic);
            }
        }, 400);

    }

    public class CustomAdapter extends ArrayAdapter<String> {
        List list;
        Context context;
        int xmlResource;

        public CustomAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            xmlResource = resource;
            list = objects;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterLayout = layoutInflater.inflate(xmlResource, null);

            TextView purchase = adapterLayout.findViewById(R.id.purchase);
            ImageView imagePurchase = adapterLayout.findViewById(R.id.imagePurchase);

            String name = list.get(position).toString();
            purchase.setText(name);

            if (name.substring(0,1).equals("P")) {
                imagePurchase.setImageResource(R.drawable.money);
                imagePurchase.setScaleX(0.37F);
                imagePurchase.setScaleY(0.37F);
            }
            else if (name.substring(0,1).equals("C"))
                imagePurchase.setImageResource(R.drawable.flame);

            notifyDataSetChanged();

            return adapterLayout;
        }
    } //closes CustomAdapter

}