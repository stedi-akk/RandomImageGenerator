package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stedi.randomimagegenerator.ImageParams;

import java.util.Locale;

public class TextOverlayGenerator implements Generator {
    private final Generator generator;

    private TextPolicy textPolicy;
    private Paint backgroundPaint;
    private Paint textPaint;

    private boolean drawBackground = true;
    private boolean autoresizeText = true;

    public interface TextPolicy {
        String getText(ImageParams imageParams);
    }

    public TextOverlayGenerator(Generator generator) {
        this.generator = generator;
    }

    public void setTextPolicy(TextPolicy textPolicy) {
        this.textPolicy = textPolicy;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public void setBackgroundPaint(Paint backgroundPaint) {
        this.backgroundPaint = backgroundPaint;
    }

    public void drawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    public void setAutoresizeText(boolean autoresizeText) {
        this.autoresizeText = autoresizeText;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = generator.generate(imageParams);
        if (bitmap == null)
            return null;

        if (textPolicy == null) {
            textPolicy = new TextPolicy() {
                @Override
                public String getText(ImageParams imageParams) {
                    return String.format(Locale.getDefault(), "%d_%dx%d",
                            imageParams.getId(), imageParams.getWidth(), imageParams.getHeight());
                }
            };
        }

        String text = textPolicy.getText(imageParams);

        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
        }

        Rect textBounds = new Rect();

        if (autoresizeText) {
            textPaint.setTextSize(bitmap.getWidth());
            do {
                textPaint.setTextSize(textPaint.getTextSize() / 2);
                textPaint.getTextBounds(text, 0, text.length(), textBounds);
            } while (textBounds.width() >= bitmap.getWidth() - textBounds.height() * 2);
        } else {
            textPaint.getTextBounds(text, 0, text.length(), textBounds);
        }

        float centerX = bitmap.getWidth() / 2f;
        float centerY = bitmap.getHeight() / 2f;
        textBounds.offsetTo((int) (centerX - textBounds.width() / 2f), (int) (centerY - textBounds.height() / 2f));
        float padding = textBounds.height() / 2f;
        textBounds.inset((int) -padding, (int) -padding);

        Canvas canvas = new Canvas(bitmap);

        if (drawBackground && backgroundPaint == null) {
            backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.BLACK);
        }

        if (drawBackground)
            canvas.drawRect(textBounds, backgroundPaint);

        canvas.drawText(text, textBounds.left + padding, textBounds.top + textBounds.height() - padding, textPaint);

        return bitmap;
    }
}
