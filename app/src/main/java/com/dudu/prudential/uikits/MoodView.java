package com.dudu.prudential.uikits;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.dudu.framework.adapter.AutoAdapter;
import com.dudu.framework.drawables.ShadowDrawable;
import com.dudu.prudential.R;
import com.dudu.prudential.utils.DateUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;

public class MoodView extends ConstraintLayout implements View.OnClickListener {

    public static final int STATUS_UNKNOW = 0;
    public static final int STATUS_VERY_GOOD = 1;
    public static final int STATUS_GOOD = 2;

    public static final String COLOR_VERY_GOOD = "#FF823C";
    public static final String COLOR_GOOD = "#52C873";
    public static final String COLOR_UNKNOW = "#CFCFCF";

    public static final String COLOR_VERY_GOOD_GRADIENT_START = "#FFA14A";
    public static final String COLOR_VERY_GOOD_GRADIENT_END = "#FFCC4A";
    public static final String COLOR_GOOD_GRADIENT_START = "#42F373";
    public static final String COLOR_GOOD_GRADIENT_END = "#A1FD44";

    private int mIndex;
    private int mScore;
    private boolean mSelected;
    private boolean mCurrented;
    private int mPillarHeight;
    private String mWeekDay;

    private TextView mDayTv;
    private ImageView mMoodIconIv;
    private ConstraintLayout mPillarLayout;
    private ViewStub mMoodScoreVs;
    private TextView mMoodScoreTv;
    private int mScoreStatus = STATUS_UNKNOW;
    private int mPillarHeightScaleDuration;

    private OnMoodSelectedChangeListener mOnMoodSelectedChangeListener;

    public MoodView(@NonNull Context context) {
        super(context);
    }

    public MoodView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoodView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDayTv = findViewById(R.id.day_text);
        mMoodIconIv = findViewById(R.id.mood_icon);
        mPillarLayout = findViewById(R.id.pillar);
        mMoodScoreVs = findViewById(R.id.mood_score_vs);

        mDayTv.setOnClickListener(this);

        initDayAnim();

    }

    public void setOnMoodSelectedChangeListener(OnMoodSelectedChangeListener onMoodSelectedChangeListener) {
        this.mOnMoodSelectedChangeListener = onMoodSelectedChangeListener;
    }

    public void setUnSelect() {
        mSelected = false;
        showUnSelect();
    }

    public void updateData(int index, int score, int totalHeight) {
        this.mIndex = index;
        this.mScore = score;
        this.mWeekDay = DateUtil.getWeekDayByIndex(index);
        this.mCurrented = this.mWeekDay.equals(DateUtil.getCurWeekDay());

        if(score >= 90) {
            mMoodIconIv.setImageResource(R.mipmap.very_good_icon);
            mScoreStatus = STATUS_VERY_GOOD;
        } else if(score >= 50) {
            mMoodIconIv.setImageResource(R.mipmap.good_icon);
            mScoreStatus = STATUS_GOOD;
        } else {
            mMoodIconIv.setImageResource(R.mipmap.unknow_icon);
            mScoreStatus = STATUS_UNKNOW;
        }

        if(mCurrented) {
            mDayTv.setBackgroundResource(R.drawable.mood_text_cur_bg);
            mDayTv.setTextColor(getResources().getColor(R.color.white));
        } else {
            mDayTv.setBackgroundResource(R.drawable.mood_text_nor_bg);
            mDayTv.setTextColor(getResources().getColor(R.color.text_dark_color));
        }
        mDayTv.setText(this.mWeekDay);

        calculatePillarHeight(totalHeight);

        showBarBg();
        initPillarAnim();
    }

    private void calculatePillarHeight(int totalHeight) {
        int pillarTotalHeight = totalHeight - getResources().getDimensionPixelSize(R.dimen.pillar_bottom_padding);

        if(mScoreStatus == STATUS_UNKNOW) {
            this.mPillarHeight = pillarTotalHeight * 31 / 100;
        } else {
            this.mPillarHeight = pillarTotalHeight * mScore / 100;
        }

        float pillarHeightScaleVelocity = pillarTotalHeight / 400.0f;
        mPillarHeightScaleDuration = (int) (mPillarHeight / pillarHeightScaleVelocity);
    }

    private void showBarBg() {
        if(mPillarLayout == null) {
            return;
        }

        GradientDrawable bgDrawable = new GradientDrawable();
        bgDrawable.setCornerRadius(AutoAdapter.dp2px(22));
        if(mScoreStatus == STATUS_VERY_GOOD) {
            bgDrawable.setColor(Color.parseColor(COLOR_VERY_GOOD));
        } else if(mScoreStatus == STATUS_GOOD) {
            bgDrawable.setColor(Color.parseColor(COLOR_GOOD));
        } else {
            bgDrawable.setColor(Color.parseColor(COLOR_UNKNOW));
        }

        mPillarLayout.setBackground(bgDrawable);

    }

    private void showSelected() {
        if(!mSelected) {
            return;
        }
        mDayTv.setElevation(AutoAdapter.dp2px(4));
        if(!mCurrented) {
            if(mScoreStatus == STATUS_VERY_GOOD) {
                mDayTv.setTextColor(Color.parseColor(COLOR_VERY_GOOD));
            } else if(mScoreStatus == STATUS_GOOD) {
                mDayTv.setTextColor(Color.parseColor(COLOR_GOOD));
            }
        }

        ValueAnimator pillarAnimator = ValueAnimator.ofFloat(0, AutoAdapter.dp2px(3));

        int oldWidth = mPillarLayout.getWidth();
        int oldHeight = mPillarLayout.getHeight();
        float oldBottomMargin = getResources().getDimensionPixelSize(R.dimen.pillar_bottom_padding);
        pillarAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            ConstraintLayout.LayoutParams params = (LayoutParams) mPillarLayout.getLayoutParams();
            params.width = (int) (oldWidth + value);
            params.height = (int) (oldHeight + value);
            params.bottomMargin = (int) (oldBottomMargin - value);
            mPillarLayout.setPadding(0, 0, 0, (int) (AutoAdapter.dp2px(4) + value));
            mPillarLayout.setLayoutParams(params);
        });
        pillarAnimator.setDuration(100);
        pillarAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showSelectedBarBg();
                mPillarLayout.setElevation(AutoAdapter.dp2px(4));
            }
        });
        pillarAnimator.start();
    }

    private void showSelectedBarBg() {
        if(mScoreStatus == STATUS_UNKNOW) {
            return;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke((int) AutoAdapter.dp2px(3), Color.parseColor("#ffffff"));
        gradientDrawable.setCornerRadius(AutoAdapter.dp2px(25));
        int[] colors = null;
        if(mScoreStatus == STATUS_VERY_GOOD) {
            colors = new int[]{Color.parseColor(COLOR_VERY_GOOD_GRADIENT_START), Color.parseColor(COLOR_VERY_GOOD_GRADIENT_END)};
        } else if(mScoreStatus == STATUS_GOOD) {
            colors = new int[]{Color.parseColor(COLOR_GOOD_GRADIENT_START), Color.parseColor(COLOR_GOOD_GRADIENT_END)};
        }
        if(colors != null && colors.length == 2) {
            gradientDrawable.setColors(colors);
        }

        GradientDrawable bgDrawable = new GradientDrawable();
        bgDrawable.setCornerRadius(AutoAdapter.dp2px(25));
        bgDrawable.setColor(Color.WHITE);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bgDrawable, gradientDrawable});
        mPillarLayout.setBackground(layerDrawable);
    }

    private void showUnSelect() {
        mDayTv.setElevation(AutoAdapter.dp2px(0));
        mDayTv.setTranslationZ(AutoAdapter.dp2px(0));
        if(mCurrented) {
            mDayTv.setTextColor(getResources().getColor(R.color.white));
        } else {
            mDayTv.setTextColor(getResources().getColor(R.color.text_dark_color));
        }
        ConstraintLayout.LayoutParams params = (LayoutParams) mPillarLayout.getLayoutParams();
        params.height = mPillarHeight;
        params.width = (int) AutoAdapter.dp2px(44);
        params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.pillar_bottom_padding);
        mPillarLayout.setPadding(0, 0, 0, (int) AutoAdapter.dp2px(4));
        mPillarLayout.setElevation(0);
        showBarBg();
    }

    private void initDayAnim() {

        mDayTv.setTextColor(Color.TRANSPARENT);

        ValueAnimator textAnimator = ValueAnimator.ofFloat(0, 1.0f);
        textAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            int textColor = this.mDayTv.getCurrentTextColor();
            int alpha = (int) (value * 255);
            int curTextColor = ColorUtils.setAlphaComponent(textColor, alpha);
            mDayTv.setTextColor(curTextColor);
        });
        textAnimator.setDuration(300);

        if(mCurrented) {

            this.mDayTv.setScaleX(0);
            this.mDayTv.setScaleY(0);
            ValueAnimator bgAnimator = ValueAnimator.ofFloat(0, 1.0f);
            bgAnimator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                this.mDayTv.setScaleX(value);
                this.mDayTv.setScaleY(value);
            });
            bgAnimator.setDuration(300);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(bgAnimator, textAnimator);
            animatorSet.start();
        } else {
            textAnimator.start();
        }
    }

    private void initPillarAnim() {
        ValueAnimator iconAnimator = ValueAnimator.ofFloat(0, 1.0f);
        iconAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            this.mMoodIconIv.setScaleX(value);
            this.mMoodIconIv.setScaleY(value);
        });
        iconAnimator.setDuration(300);

        ValueAnimator pillarAnimator = ValueAnimator.ofInt((int) AutoAdapter.dp2px(44), this.mPillarHeight);
        pillarAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams params = mPillarLayout.getLayoutParams();
            params.height = value;
            mPillarLayout.setLayoutParams(params);
        });
        pillarAnimator.setDuration(mPillarHeightScaleDuration);

        ValueAnimator scoreAnimator = ValueAnimator.ofFloat(0, 1f);
        scoreAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mMoodScoreVs.inflate();
                mMoodScoreTv = findViewById(mMoodScoreVs.getInflatedId());
                mMoodScoreTv.setVisibility(VISIBLE);
                mMoodScoreTv.setText(mScoreStatus == STATUS_UNKNOW ? "" : String.valueOf(mScore));
                Typeface typeface = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "font/Nunito-SemiBold.ttf");
                mMoodScoreTv.setTypeface(typeface);
                mMoodScoreTv.setAlpha(0);
            }
        });
        scoreAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
//            if(mMoodScoreTv != null) {
//                mMoodScoreTv.setAlpha(value);
//            }
            mMoodScoreTv.setAlpha(value);
        });
        scoreAnimator.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(iconAnimator, pillarAnimator, scoreAnimator);
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.day_text) {
            if(!mSelected) {
                mSelected = true;
                showSelected();
                if(mOnMoodSelectedChangeListener != null) {
                    mOnMoodSelectedChangeListener.onSelectedChange(mIndex, mSelected);
                }
            }
        }
    }

    public interface OnMoodSelectedChangeListener {
        void onSelectedChange(int index, boolean isSelected);
    }
}
