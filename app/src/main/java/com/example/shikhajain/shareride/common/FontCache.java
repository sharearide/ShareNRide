// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.shikhajain.shareride.common;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontCache
{

    public static String Bradley_Hand_Bold = "font/BradleyHandBold.ttf";
    public static String MONITORICA_REGULAR = "font/monitorica/Monitorica-Rg.ttf";
    public static String ROBOTO_LIGHT = "font/roboto/Roboto-Light.ttf";
    public static String ROBOTO_MEDIUM = "font/roboto/Roboto-Medium.ttf";
    public static String ROBOTO_REGULAR = "font/roboto/Roboto-Regular.ttf";
    public static String RYDE = "font/Ryde.ttf";
    private static HashMap fontCache = new HashMap();

    public FontCache()
    {
    }

    public static Typeface getTypeface(Context context, String s)
    {
        Typeface typeface = (Typeface)fontCache.get(s);
        Typeface typeface1 = typeface;
        if (typeface != null)
        {
            //break ;//MISSING_BLOCK_LABEL_37;
        }
        try
        {
            typeface1 = Typeface.createFromAsset(context.getAssets(), s);
        }
        // Misplaced declaration of an exception variable
        catch (Exception e)
        {
            //context.printStackTrace();
            return typeface;
        }
        typeface = typeface1;
        fontCache.put(s, typeface1);
        return typeface1;
    }

}
