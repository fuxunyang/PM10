package com.sky.pm.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sky.pm.R;
import com.sky.pm.common.Constants;
import com.sky.pm.ui.BaseActivity;
import com.sky.pm.utils.SPUtils;
import com.sky.utils.JumpAct;

/**
 * author ShenZhenWei
 * time   15/11/24.
 * email  826337999@qq.com
 */
public class SplashActivity extends BaseActivity {
    public Boolean flag;//是否首次运行，true代表首次
    private int TIME = 2000;//handler延迟时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏,在v7中AppCompatActivity下无用
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        ImageView view = new ImageView(this);
        view.setBackgroundResource(R.mipmap.welcome);
        setContentView(view);
//        SPUtils.put(SplashActivity.this, "isfirst", true);
        flag = (Boolean) SPUtils.get(this, Constants.ISFIRST, true);
        //加载动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 判断程序与第几次运行，如果是第一次运行引导页面
                        if (flag) {
//                            JumpAct.jumpActivity(SplashActivity.this, IntroductoryActivity.class);
//                            finish();
//                        } else {
                            JumpAct.jumpActivity(SplashActivity.this, MainActivity.class);
                            finish();
                        }
                    }
                }, TIME);
            }
        });
    }

}
