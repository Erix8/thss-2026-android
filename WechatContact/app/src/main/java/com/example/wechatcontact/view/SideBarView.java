package com.example.wechatcontact.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Alphabet sidebar used to quickly jump to contacts
 */
public class SideBarView extends View {

    private final String[] letters = {
            "A","B","C","D","E","F","G",
            "H","I","J","K","L","M",
            "N","O","P","Q","R","S",
            "T","U","V","W","X","Y","Z"
    };

    private final Paint paint = new Paint();

    public interface OnLetterTouchListener{
        void onLetterTouched(String letter);
    }

    private OnLetterTouchListener listener;

    public void setOnLetterTouchListener(OnLetterTouchListener listener){
        this.listener = listener;
    }

    // Called when creating the view programmatically
    public SideBarView(Context context){
        super(context);
        init();
    }

    // Called when inflating the view from XML
    public SideBarView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    // Called when inflating the view from XML with a style
    public SideBarView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    // Shared initialization
    private void init(){
        paint.setColor(0xFF666666);
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        int width = getWidth();

        if (height <= 0) return;

        // Measure font metrics
        Paint.FontMetrics fm = paint.getFontMetrics();
        float glyphHeight = fm.descent - fm.ascent; // approximate glyph height

        int n = letters.length;
        // Desired maximum spacing between letter centers (relative to glyph height)
        // increased to allow more dispersion
        float maxSpacing = glyphHeight * 2.0f;

        // We want letters to be centered and occupy up to 80% of view height
        float desiredSpacing = (height * 0.8f) / n;

        // Ensure spacing is not smaller than glyph height (to avoid overlap), and not bigger than maxSpacing
        float spacing = Math.max(glyphHeight, Math.min(maxSpacing, desiredSpacing));

        float centerY = height / 2f;
        float midIndex = (n - 1) / 2f;

        // vertical offset from baseline to glyph center
        float baselineToCenter = (fm.ascent + fm.descent) / 2f;

        for (int i = 0; i < n; i++) {
            float desiredCenterY = centerY + (i - midIndex) * spacing;
            float baseline = desiredCenterY - baselineToCenter;
            canvas.drawText(letters[i], width / 2f, baseline, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float y = event.getY();

        int index = -1;
        int n = letters.length;
        float h = getHeight();

        if (h > 0 && n > 0) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            float glyphHeight = fm.descent - fm.ascent;
            // allow more dispersion
            float maxSpacing = glyphHeight * 2.0f;
            float desiredSpacing = (h * 0.8f) / n;
            float spacing = Math.max(glyphHeight, Math.min(maxSpacing, desiredSpacing));

            float centerY = h / 2f;
            float midIndex = (n - 1) / 2f;
            float topCenter = centerY - midIndex * spacing; // center Y of first letter

            // Round to nearest index based on spacing
            int guessed = Math.round((y - topCenter) / spacing);
            if (guessed >= 0 && guessed < n) {
                index = guessed;
            }
        }

        if (index >= 0 && index < letters.length) {
            if (listener != null) {
                listener.onLetterTouched(letters[index]);
            }
        }

        // For accessibility, call performClick() on ACTION_UP
        if (event.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }

        return true;
    }

    @Override
    public boolean performClick() {
        // Call super to handle accessibility events
        return super.performClick();
    }
}