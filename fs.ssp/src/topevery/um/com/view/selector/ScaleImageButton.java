package topevery.um.com.view.selector;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ShuDongShu on 2014/9/2.
 */
public class ScaleImageButton extends ImageView
{
    private int clickEffect = 128;

    public ScaleImageButton(Context context)
    {
        super(context);
        setClickable(true);
    }

    public ScaleImageButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
//        TypedArray cusAttrs = context.obtainStyledAttributes(attrs, Res.styleable.ScaleImageButton);
//        clickEffect = cusAttrs.getInt(Res.styleable.ScaleImageButton_clickEffect, 255);
//        cusAttrs.recycle();
//        TypedArray androidAttrs = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.clickable});
//        setClickable(androidAttrs.getBoolean(0, true));
//        androidAttrs.recycle();
    }

    @Override
    public void refreshDrawableState()
    {
        super.refreshDrawableState();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(isButtonPressed())
        {
            canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), clickEffect, Canvas.ALL_SAVE_FLAG);
        }else
        {
            canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255, Canvas.ALL_SAVE_FLAG);
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    private boolean isButtonPressed()
    {
        int[] states = getDrawableState();
        for(int state : states)
        {
            if (state == android.R.attr.state_pressed)
            {
                return isEnabled();
            }
        }
        return false;
    }
}
