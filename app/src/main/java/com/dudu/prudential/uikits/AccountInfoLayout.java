package com.dudu.prudential.uikits;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.dudu.framework.utils.AppUtil;
import com.dudu.prudential.R;
import com.dudu.prudential.models.UserInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AccountInfoLayout extends ConstraintLayout {

    private ImageView mHeadIconIv;
    private TextView mNameTv;
    private TextView mScoreTv;

    private UserInfo mUserInfo;

    private RequestOptions mOptions;

    private boolean mIsPlayAnima = false;

    public AccountInfoLayout(@NonNull Context context) {
        super(context);
    }

    public AccountInfoLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AccountInfoLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AccountInfoLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeadIconIv = findViewById(R.id.header_icon);
        mNameTv = findViewById(R.id.name_tv);
        mScoreTv = findViewById(R.id.score);

        Typeface typeface = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "font/Nunito-ExtraBold.ttf");
        mScoreTv.setTypeface(typeface);
    }

    public void setUserInfo(UserInfo userInfo) {
        if(userInfo == null) {
            return;
        }
        this.mUserInfo = userInfo;
        mNameTv.setText(userInfo.getName());

//        int score = mUserInfo.getScore();
//        String scoreStr = score < 0 ? "--" : String.valueOf(score);
        mScoreTv.setText("--");

        if(mOptions == null) {
            mOptions = new RequestOptions()
                    .circleCrop();
        }
        Glide.with(getContext())
                .setDefaultRequestOptions(mOptions)
                .load(userInfo.getHeadIconUrl())
                .placeholder(R.mipmap.def_head_icon)
                .into(mHeadIconIv);

        if(!mIsPlayAnima) {
            initAnim();
            mIsPlayAnima = true;
        }
    }

    private void initAnim() {


        ValueAnimator scoreAnimator = ValueAnimator.ofInt(0, mUserInfo.getScore());
        scoreAnimator.addUpdateListener(animation -> {
            int score = (int) animation.getAnimatedValue();
            String scoreStr = score < 0 ? "--" : String.valueOf(score);
            mScoreTv.setText(scoreStr);
        });
        scoreAnimator.setDuration(500);

        scoreAnimator.start();

//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(scoreAnimator);
//        animatorSet.start();
    }
}
