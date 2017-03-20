package com.example.ganesh.timework.views;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * Created by Ganesh Prasad on 05-07-2016. */

public class CustomTabLayout extends TabLayout {

    private static final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";

    public CustomTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }

    private void initTabMinWidth() {

        int tabMinWidth = 40;

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
