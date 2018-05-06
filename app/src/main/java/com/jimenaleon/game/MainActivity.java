package com.jimenaleon.game;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView nave1;
    private int screenWidth, screenHeight;
    private RelativeLayout relativeLay;
    private final int NAVE_WIDTH = 230;
    private final int NAVE_HEIGHT = 220;
    private Handler handler;
    private boolean isShoot = false;
    private int speed = 6000;
    private int enemys = 2;
    private Timer timer = new Timer();
    private boolean isEnemy = false;
    private ImageView currentShoot;
    private boolean gameOver = false;
    private ArrayList<ImageView> enemysList = new ArrayList<>();
    private boolean start_flag = false;
    private TextView textstart, textScore;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nave1 = (ImageView) findViewById(R.id.nave1);
        relativeLay = (RelativeLayout) findViewById(R.id.relativeLay);
        textstart = (TextView) findViewById(R.id.textstart);
        textScore = (TextView) findViewById(R.id.textScore);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        handler = new Handler();

        setBackground();
    }

    public void createImageView(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(NAVE_WIDTH, NAVE_HEIGHT);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.nave2);
        imageView.setX(screenWidth  + 350);
        imageView.setY(randomPosY(imageView));
        relativeLay.addView(imageView);
        enemysList.add(imageView);
        moveImage(imageView);
    }

    public int randomPosY(ImageView imageView){
        return (int) Math.floor(Math.random() * (screenHeight - NAVE_HEIGHT));
    }

    public void moveImage(final ImageView imageView){
        imageView.animate()
                .translationX(0 - NAVE_WIDTH)
                .setDuration(speed)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        isEnemy = false;
                    }
                })
                .start();
    }

    public void setBackground(){
        final ImageView back1 = (ImageView) findViewById(R.id.back1);
        final ImageView back2 = (ImageView) findViewById(R.id.back2);

        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final float progress = (float) animator.getAnimatedValue();
                final float width = back1.getWidth();
                final float translationX = width * progress;
                back1.setTranslationX(translationX);
                back2.setTranslationX(translationX - width);
            }
        });
        animator.start();
    }

    public void showEnemy(){

        if(score > 50 && score < 100){
            speed = 5000;
            enemys = 3;
        }else if(score > 100 && score < 180){
            speed = 4000;
            enemys = 4;
        }else if(score > 180 && score < 280){
            speed = 3000;
            enemys = 5;
        }else if(score > 280){
            speed = 2000;
            enemys = 6;
        }

        if(!isEnemy && enemysList.size() < enemys){
            isEnemy = true;
            createImageView();
        }
    }


    public boolean onTouchEvent(MotionEvent event){

        int posX = (int) event.getX();
        int posY = (int) event.getY();
        if(!start_flag){
            textstart.setVisibility(View.GONE);
            start_flag = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(gameOver == false){
                                showEnemy();
                                checkKill();
                            }
                        }
                    });
                }
            }, 0, 20);
        }else{
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    nave1.setY(event.getY() - (nave1.getHeight() / 2));
                    if(!isShoot){
                        createShootImage();
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                /*
                if(nave1.getY() + nave1.getHeight() > screenHeight){
                    nave1.setY(screenHeight);
                }else if(nave1.getY() < 0){
                    nave1.setY(0);
                }else {
                    */
                    nave1.setY(event.getY() - (nave1.getHeight() / 2));
                    if(!isShoot){
                        createShootImage();
                    }
                    //}
                    break;
            }
        }

        return true;
    }

    public void createShootImage(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50, 50);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.cross);
        imageView.setX(nave1.getWidth());
        imageView.setY(nave1.getY() + (nave1.getHeight() / 2));
        relativeLay.addView(imageView);
        currentShoot = imageView;
        shoot(imageView);
    }

    public void shoot(ImageView imageView){
        isShoot = true;
        imageView.animate()
                .translationX(screenWidth + 50)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        isShoot = false;
                    }
                })
                .start();
    }

    public void checkKill(){

        if(currentShoot != null && enemysList.size() > 0){
            for (int i = 0 ; i < enemysList.size(); i++){
                if(currentShoot.getY() > enemysList.get(i).getY()
                        && currentShoot.getY() < enemysList.get(i).getY() + enemysList.get(i).getHeight()
                        && currentShoot.getX() > enemysList.get(i).getX()
                        && currentShoot.getX() < enemysList.get(i).getX() + enemysList.get(i).getWidth()){

                    score += 10;
                    textScore.setText("Score: " + score);
                    relativeLay.removeView(enemysList.get(i));
                    enemysList.remove(i);
                    //enemysList.get(i).setVisibility(View.INVISIBLE);
                    isEnemy = false;
                }else{
                    int[] locations = new int[2];
                    enemysList.get(i).getLocationInWindow(locations);
                    int x = locations[0];
                    if(x < 0){
                        gameOver = true;
                        Intent intent = new Intent(this, GameOverActivity.class);
                        intent.putExtra("SCORE", score);
                        startActivity(intent);
                    }
                }
            }
        }
    }

}
