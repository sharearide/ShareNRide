// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.shikhajain.shareride.commonui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

import com.example.shikhajain.shareride.common.FontCache;


public class RydeAutoCompleteTextView extends AppCompatAutoCompleteTextView
{

    public RydeAutoCompleteTextView(Context context)
    {
        super(context);
        isInEditMode();
    }

    public RydeAutoCompleteTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        isInEditMode();
    }

    public RydeAutoCompleteTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        isInEditMode();
    }

    public void setCustomTypeFace(Typeface typeface)
    {
        super.setTypeface(typeface);
    }

    public void setTypeface(Typeface typeface)
    {
        super.setTypeface(FontCache.getTypeface(getContext(), "font/roboto/Roboto-Light.ttf"));
    }

    public void setTypeface(Typeface typeface, int i)
    {
        if (!isInEditMode())
        {
            super.setTypeface(FontCache.getTypeface(getContext(), "font/roboto/Roboto-Light.ttf"));
        }
    }
}
