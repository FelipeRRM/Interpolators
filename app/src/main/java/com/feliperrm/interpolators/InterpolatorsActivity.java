package com.feliperrm.interpolators;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class InterpolatorsActivity extends AppCompatActivity {

    private static final long ANIM_DURATION = 3000;
    private static final long ANIM_DELAY = 750;
    FrameLayout linear, accelerate, decelerate, accelerateDecelerate, overshoot, bounce;
    LinearLayout parent;
    int screenWidth;
    float ballWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolators);
        findViews();
        setUpWidth();
    }

    private void findViews(){
        linear = (FrameLayout) findViewById(R.id.ball_linear);
        accelerate = (FrameLayout) findViewById(R.id.ball_accelerate);
        decelerate = (FrameLayout) findViewById(R.id.ball_decelerate);
        accelerateDecelerate = (FrameLayout) findViewById(R.id.ball_accelerate_decelerate);
        overshoot = (FrameLayout) findViewById(R.id.ball_overshoot);
        bounce = (FrameLayout) findViewById(R.id.ball_bounce);
        parent = (LinearLayout) findViewById(R.id.linearParent);
    }

    private void setUpWidth(){

        ballWidth = getResources().getDimensionPixelSize(R.dimen.ball_size);

        parent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                parent.getViewTreeObserver().removeOnPreDrawListener(this);
                screenWidth = parent.getWidth();
                animateViews();
                return true;
            }
        });
    }

    private void animateViews(){
        animateViewForward(linear, null);
        animateViewForward(accelerate, new AccelerateInterpolator());
        animateViewForward(decelerate, new DecelerateInterpolator());
        animateViewForward(accelerateDecelerate,new AccelerateDecelerateInterpolator());
        animateViewForward(overshoot, new OvershootInterpolator());
        animateViewForward(bounce, new BounceInterpolator());
    }

    private void animateViewForward(final View v, final TimeInterpolator interpolator){
        v.animate()
                .translationX(screenWidth - getResources().getDimensionPixelSize(R.dimen.ball_margin)*2 - ballWidth)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY)
                .setInterpolator(interpolator)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animateViewBackward(v,interpolator);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
    }

    private void animateViewBackward(final View v, final TimeInterpolator interpolator){
        v.animate()
                .translationX(0)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY)
                .setInterpolator(interpolator)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animateViewForward(v,interpolator);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
    }

}
