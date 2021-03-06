package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jonatan on 2016-12-13.
 */
public interface IWardrobeItem {
    String getWardrobeItemString();

    Drawable getDrawable();

    WardrobeItemType getWardrobeItemType();

    View addToView(LinearLayout layout);
}
