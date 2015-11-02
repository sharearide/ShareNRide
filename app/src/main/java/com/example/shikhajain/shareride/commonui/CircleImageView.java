// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.shikhajain.shareride.commonui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView
{

    private static final Bitmap.Config BITMAP_CONFIG;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final int DEFAULT_BORDER_COLOR = 0xff000000;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final ScaleType SCALE_TYPE;
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private final Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBorderColor;
    private final Paint mBorderPaint;
    private float mBorderRadius;
    private final RectF mBorderRect;
    private int mBorderWidth;
    private float mDrawableRadius;
    private final RectF mDrawableRect;
    private boolean mReady;
    private boolean mSetupPending;
    private final Matrix mShaderMatrix;

    public CircleImageView(Context context)
    {
        super(context);
        mDrawableRect = new RectF();
        mBorderRect = new RectF();
        mShaderMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderColor = 0xff000000;
        mBorderWidth = 0;
        isInEditMode();
        init();
    }

    public CircleImageView(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, 0);
        isInEditMode();
    }

    public CircleImageView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mDrawableRect = new RectF();
        mBorderRect = new RectF();
        mShaderMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderColor = 0xff000000;
        mBorderWidth = 0;
        isInEditMode();
        //context = context.obtainStyledAttributes(attributeset, R.styleable.CircleImageView, i, 0);
        mBorderWidth = 1;
        mBorderColor = 0;
        //context.recycle();
        init();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable)
    {
        if (drawable == null)
        {
            return null;
        }
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap;
        if (!(drawable instanceof ColorDrawable))
        {
           // break MISSING_BLOCK_LABEL_67;
        }
        bitmap = Bitmap.createBitmap(2, 2, BITMAP_CONFIG);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
        /*try
        {
            //bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
        }
        // Misplaced declaration of an exception variable
        catch (Exception e)
        {
            return null;
        }*/

    }

    private void init()
    {
        super.setScaleType(SCALE_TYPE);
        mReady = true;
        if (mSetupPending)
        {
            setup();
            mSetupPending = false;
        }
    }

    private void setup()
    {
        if (!mReady)
        {
            mSetupPending = true;
        } else
        if (mBitmap != null)
        {
            mBitmapShader = new BitmapShader(mBitmap, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP);
            mBitmapPaint.setAntiAlias(true);
            mBitmapPaint.setShader(mBitmapShader);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);
            mBitmapHeight = mBitmap.getHeight();
            mBitmapWidth = mBitmap.getWidth();
            mBorderRect.set(0.0F, 0.0F, getWidth(), getHeight());
            mBorderRadius = Math.min((mBorderRect.height() - (float) mBorderWidth) / 2.0F, (mBorderRect.width() - (float) mBorderWidth) / 2.0F);
            mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width() - (float)mBorderWidth, mBorderRect.height() - (float)mBorderWidth);
            mDrawableRadius = Math.min(mDrawableRect.height() / 2.0F, mDrawableRect.width() / 2.0F);
            updateShaderMatrix();
            invalidate();
            return;
        }
    }

    private void updateShaderMatrix()
    {
        float f = 0.0F;
        float f1 = 0.0F;
        mShaderMatrix.set(null);
        float f2;
        if ((float)mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * (float)mBitmapHeight)
        {
            f2 = mDrawableRect.height() / (float)mBitmapHeight;
            f = (mDrawableRect.width() - (float)mBitmapWidth * f2) * 0.5F;
        } else
        {
            f2 = mDrawableRect.width() / (float)mBitmapWidth;
            f1 = (mDrawableRect.height() - (float)mBitmapHeight * f2) * 0.5F;
        }
        mShaderMatrix.setScale(f2, f2);
        mShaderMatrix.postTranslate((int)(f + 0.5F) + mBorderWidth, (int)(f1 + 0.5F) + mBorderWidth);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    public int getBorderColor()
    {
        return mBorderColor;
    }

    public int getBorderWidth()
    {
        return mBorderWidth;
    }

    public ScaleType getScaleType()
    {
        return SCALE_TYPE;
    }

    protected void onDraw(Canvas canvas)
    {
        if (getDrawable() != null)
        {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius, mBitmapPaint);
            if (mBorderWidth != 0)
            {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius, mBorderPaint);
                return;
            }
        }
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        super.onSizeChanged(i, j, k, l);
        setup();
    }

    public void setAdjustViewBounds(boolean flag)
    {
        if (flag)
        {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        } else
        {
            return;
        }
    }

    public void setBorderColor(int i)
    {
        if (i == mBorderColor)
        {
            return;
        } else
        {
            mBorderColor = i;
            mBorderPaint.setColor(mBorderColor);
            invalidate();
            return;
        }
    }

    public void setBorderWidth(int i)
    {
        if (i == mBorderWidth)
        {
            return;
        } else
        {
            mBorderWidth = i;
            setup();
            return;
        }
    }

    public void setImageBitmap(Bitmap bitmap)
    {
        super.setImageBitmap(bitmap);
        mBitmap = bitmap;
        setup();
    }

    public void setImageDrawable(Drawable drawable)
    {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    public void setImageResource(int i)
    {
        super.setImageResource(i);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    public void setImageURI(Uri uri)
    {
        super.setImageURI(uri);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    public void setScaleType(ScaleType scaletype)
    {
        if (scaletype != SCALE_TYPE)
        {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", new Object[]{
                    scaletype
            }));
        } else
        {
            return;
        }
    }

    static
    {
        SCALE_TYPE = ScaleType.CENTER_CROP;
        BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    }
}
