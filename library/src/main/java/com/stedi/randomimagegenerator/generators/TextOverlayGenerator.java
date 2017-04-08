package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stedi.randomimagegenerator.ImageParams;

import java.util.Locale;

/**
 * Generator wrapper, that draws text in front of the target generator.
 */
public class TextOverlayGenerator implements Generator {
    private final Params params = new Params();

    /**
     * Text policy. Used to get text, that will be drawn in front of image.
     */
    public interface TextPolicy {
        /**
         * @param imageParams Image parameters used to generate an image.
         * @return Text that will be drawn in front of image.
         */
        String getText(ImageParams imageParams);
    }

    private static class DefaultTextPolicy implements TextPolicy {
        private final String format = "%d_%dx%d";

        @Override
        public String getText(ImageParams imageParams) {
            return String.format(Locale.getDefault(), format,
                    imageParams.getId(), imageParams.getWidth(), imageParams.getHeight());
        }

        @Override
        public String toString() {
            return "DefaultTextPolicy{" +
                    "format='" + format + '\'' +
                    '}';
        }
    }

    private TextOverlayGenerator() {
    }

    private static class Params {
        private Generator generator;
        private TextPolicy textPolicy;
        private Paint backgroundPaint;
        private Paint textPaint;
        private boolean drawBackground = true;
        private boolean autoresizeText = true;

        private void apply(Params params) {
            generator = params.generator;
            textPolicy = params.textPolicy;
            backgroundPaint = params.backgroundPaint;
            textPaint = params.textPaint;
            drawBackground = params.drawBackground;
            autoresizeText = params.autoresizeText;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "generator=" + generator +
                    ", textPolicy=" + textPolicy +
                    ", drawBackground=" + drawBackground +
                    ", autoresizeText=" + autoresizeText +
                    '}';
        }
    }

    /**
     * Builder that is used to create an instance of {@link TextOverlayGenerator}.
     */
    public static class Builder {
        private final Params p;

        public Builder() {
            p = new Params();
        }

        /**
         * Set target generator.
         * <p>Must not be null.</p>
         */
        public Builder setGenerator(Generator generator) {
            if (generator == null)
                throw new IllegalArgumentException("generator cannot be null");
            p.generator = generator;
            return this;
        }

        /**
         * Set text policy.
         * <p>If not specified, then the default implementation will be used.</p>
         */
        public Builder setTextPolicy(TextPolicy textPolicy) {
            p.textPolicy = textPolicy;
            return this;
        }

        /**
         * To override existing {@link Paint} object for drawing text.
         */
        public Builder setTextPaint(Paint textPaint) {
            p.textPaint = textPaint;
            return this;
        }

        /**
         * To override existing {@link Paint} object for drawing text background.
         */
        public Builder setBackgroundPaint(Paint backgroundPaint) {
            p.backgroundPaint = backgroundPaint;
            return this;
        }

        /**
         * To draw text background, or not. Default is {@code true}.
         */
        public Builder setDrawBackground(boolean drawBackground) {
            p.drawBackground = drawBackground;
            return this;
        }

        /**
         * To resize text based on the image size, or not. Default is {@code true}.
         */
        public Builder setAutoresizeText(boolean autoresizeText) {
            p.autoresizeText = autoresizeText;
            return this;
        }

        public TextOverlayGenerator build() {
            if (p.generator == null)
                throw new IllegalStateException("generator not specified");
            if (p.textPolicy == null)
                p.textPolicy = new DefaultTextPolicy();
            if (p.textPaint == null) {
                p.textPaint = new Paint();
                p.textPaint.setColor(Color.WHITE);
            }
            if (p.drawBackground && p.backgroundPaint == null) {
                p.backgroundPaint = new Paint();
                p.backgroundPaint.setColor(Color.BLACK);
            }
            TextOverlayGenerator generator = new TextOverlayGenerator();
            generator.params.apply(p);
            return generator;
        }
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = params.generator.generate(imageParams);
        if (bitmap == null)
            return null;

        String text = params.textPolicy.getText(imageParams);
        if (text == null)
            throw new NullPointerException("text from TextPolicy is null");

        Rect textBounds = new Rect();

        if (params.autoresizeText) {
            params.textPaint.setTextSize(bitmap.getWidth());
            do {
                params.textPaint.setTextSize(params.textPaint.getTextSize() / 2);
                params.textPaint.getTextBounds(text, 0, text.length(), textBounds);
            } while (textBounds.width() >= bitmap.getWidth() - textBounds.height() * 2);
        } else {
            params.textPaint.getTextBounds(text, 0, text.length(), textBounds);
        }

        float centerX = bitmap.getWidth() / 2f;
        float centerY = bitmap.getHeight() / 2f;
        textBounds.offsetTo((int) (centerX - textBounds.width() / 2f), (int) (centerY - textBounds.height() / 2f));
        float padding = textBounds.height() / 2f;
        textBounds.inset((int) -padding, (int) -padding);

        Canvas canvas = new Canvas(bitmap);

        if (params.drawBackground)
            canvas.drawRect(textBounds, params.backgroundPaint);

        canvas.drawText(text, textBounds.left + padding, textBounds.top + textBounds.height() - padding, params.textPaint);

        return bitmap;
    }

    @Override
    public String toString() {
        return "TextOverlayGenerator{" +
                "params=" + params +
                '}';
    }
}
