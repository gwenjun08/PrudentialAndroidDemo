package com.dudu.prudential.fragments;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dudu.framework.adapter.AutoAdapter;
import com.dudu.framework.drawables.ShadowDrawable;
import com.dudu.prudential.R;
import com.dudu.prudential.models.UserInfo;
import com.dudu.prudential.uikits.AccountInfoLayout;
import com.dudu.prudential.uikits.MoodGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private View mBgView;
    private AccountInfoLayout mAccountInfoLayout;
    private MoodGroup mMoodGroup;

    private UserInfo mUserInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initBg();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initAnim();
    }

    private void initBg() {
        ShadowDrawable shadowDrawable = new ShadowDrawable.Builder()
                .setBgColor(Color.WHITE)
                .setShadowRadius((int)AutoAdapter.dp2px(16f))
                .setShadowColor(Color.parseColor("#66000000"))
                .setShapeRadius((int) AutoAdapter.dp2px(25))
                .setOffsetY((int) AutoAdapter.dp2px(-6f))
                .setOffsetX(0)
                .builder();
        Drawable gradientDrawable = getResources().getDrawable(R.drawable.content_bg);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shadowDrawable, gradientDrawable});
        mBgView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBgView.setBackground(layerDrawable);
    }

    private void initView(@NonNull View view) {
        mBgView = view.findViewById(R.id.bg);
        mAccountInfoLayout = view.findViewById(R.id.account_info);
        mMoodGroup = view.findViewById(R.id.mood_group);
    }

    private void initData() {
        mUserInfo = new UserInfo("甘文俊", 88, "https://img1.baidu.com/it/u=4259690133,1140063031&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        mAccountInfoLayout.setUserInfo(mUserInfo);

        mMoodGroup.updataData(new int[]{86, 80, -1, 90, 92, 97, 81});
    }

    private void initAnim() {
        ValueAnimator opacityAnimator = ValueAnimator.ofFloat(0, 1.0f);
        opacityAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            mAccountInfoLayout.setAlpha(value);
        });
        opacityAnimator.setDuration(1000);
        opacityAnimator.start();
    }


}
