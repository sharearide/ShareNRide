// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.shikhajain.shareride.commonui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


import com.example.shikhajain.shareride.common.FontCache;

import java.util.HashMap;

public class RydeTextView extends AppCompatTextView
{

    private HashMap fontHashMap;
    private String fontType;
    private Context mContext;

    public RydeTextView(Context context)
    {
        super(context);
       // fontHashMap = new HashMap();
        //mContext = context;
        //fontType = mContext.getString(0x7f0600c7);
        //createFontStyleHashMap();
        //setCustomTypeFace(FontCache.getTypeface(mContext, (String)fontHashMap.get(fontType)));
        isInEditMode();
    }

    public RydeTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        /*fontHashMap = new HashMap();
        mContext = context;
        fontType = mContext.getString(0x7f0600c7);
        createFontStyleHashMap();
        context = context.obtainStyledAttributes(attributeset, R.styleable.RydeTextView);
        if (context.getString(0) != null)
        {
            fontType = context.getString(0);
        }
        setCustomTypeFace(FontCache.getTypeface(mContext, (String) fontHashMap.get(fontType)));*/
        isInEditMode();
       // context.recycle();
    }

    public RydeTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
       /* fontHashMap = new HashMap();
        mContext = context;
        fontType = mContext.getString(0x7f0600c7);
        createFontStyleHashMap();
        context = context.obtainStyledAttributes(attributeset, R.styleable.RydeStatusView);
        if (context.getString(0) != null)
        {
            fontType = context.getString(0);
        }
        setCustomTypeFace(FontCache.getTypeface(mContext, (String) fontHashMap.get(fontType)));*/
        isInEditMode();
        //context.recycle();
    }

    private void createFontStyleHashMap()
    {
       /* fontHashMap.put(mContext.getString(0x7f0600c7), FontCache.ROBOTO_LIGHT);
        fontHashMap.put(mContext.getString(0x7f0600c8), FontCache.ROBOTO_MEDIUM);
        fontHashMap.put(mContext.getString(0x7f0600c9), FontCache.ROBOTO_REGULAR);
        fontHashMap.put(mContext.getString(0x7f0600ca), FontCache.RYDE);
        fontHashMap.put(mContext.getString(0x7f0600c6), FontCache.MONITORICA_REGULAR);*/
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