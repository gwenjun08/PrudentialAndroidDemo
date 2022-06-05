package com.dudu.prudential.uikits;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dudu.framework.adapter.AutoAdapter;
import com.dudu.prudential.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MoodGroup extends ConstraintLayout implements MoodView.OnMoodSelectedChangeListener {

    private int[] mData;

    private LinearLayout mContentLayout;
    private int mCurSelectedIndex = -1;

    public MoodGroup(Context context) {
        super(context);
    }

    public MoodGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoodGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MoodGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentLayout = findViewById(R.id.mood_content);
    }

    public void updataData(int[] data) {
        this.mData = data;

        loadContent(0);
    }

    private void loadContent(int index) {
        if(mData != null && index < mData.length) {
            this.postDelayed(() -> {
                int scored = this.mData[index];
                MoodView moodView = (MoodView) LayoutInflater.from(getContext()).inflate(R.layout.mood_layout, mContentLayout, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) AutoAdapter.dp2px(55.7f), ViewGroup.LayoutParams.MATCH_PARENT);
//                if(index > 0) {
//                    params.setMarginStart((int) AutoAdapter.dp2px(6.67f));
//                }

                moodView.updateData(index, scored, getMeasuredHeight());
                moodView.setLayoutParams(params);
                moodView.setOnMoodSelectedChangeListener(this);
                mContentLayout.addView(moodView);

                loadContent(index + 1);
            }, 200);
        }
    }

    @Override
    public void onSelectedChange(int index, boolean isSelected) {
        if(!isSelected || mContentLayout == null) {
            return;
        }
        if(mCurSelectedIndex < 0) {
            mCurSelectedIndex = index;
            return;
        }
        View child = mContentLayout.getChildAt(mCurSelectedIndex);
        if(child instanceof MoodView) {
            ((MoodView) child).setUnSelect();
        }
        mCurSelectedIndex = index;
    }
}
