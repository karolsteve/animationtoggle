/*
 * Copyright 2018 Steve Tchatchouang
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.steve.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.steve.view.animationtoggle.R;


/**
 * Created by Steve Tchatchouang on 12/01/2018
 */

public class AnimatingToggle extends FrameLayout {

    private final Animation inAnimation;
    private final Animation outAnimation;
    private       View      current;

    public AnimatingToggle(Context context) {
        this(context, null);
    }

    public AnimatingToggle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatingToggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.outAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_toggle_out);
        this.inAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_toggle_in);
        this.outAnimation.setInterpolator(new FastOutSlowInInterpolator());
        this.inAnimation.setInterpolator(new FastOutSlowInInterpolator());
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() == 1) {
            current = child;
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.GONE);
        }
        child.setClickable(false);
    }

    public void display(@Nullable View view) {
        if (view == current) return;
        if (current != null) animateOut(current, outAnimation, View.GONE);
        if (view != null) animateIn(view, inAnimation);

        current = view;
    }

    private void animateIn(View view, Animation animation) {
        if (view.getVisibility() == View.VISIBLE) return;
        view.clearAnimation();
        animation.reset();
        animation.setStartTime(0);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

    private void animateOut(final View view, Animation animation, final int visibility) {
        if (view.getVisibility() != visibility) {
            view.clearAnimation();
            animation.reset();
            animation.setStartTime(0);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(visibility);
                }
            });
            view.startAnimation(animation);
        }
    }

    public void displayQuick(@Nullable View view) {
        if (view == current) return;
        if (current != null) current.setVisibility(View.GONE);
        if (view != null) view.setVisibility(View.VISIBLE);
        current = view;
    }
}