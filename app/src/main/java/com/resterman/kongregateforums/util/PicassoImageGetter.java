package com.resterman.kongregateforums.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.resterman.kongregateforums.R;
import com.squareup.picasso.Picasso;

public class PicassoImageGetter implements Html.ImageGetter {

    private TextView textView;
    private Resources resources;
    private Context context;

    public PicassoImageGetter(TextView textView, Context context) {
        this.textView = textView;
        this.resources = context.getResources();
        this.context = context;
    }

    @Override
    public Drawable getDrawable(final String source) {
        final PicassoBitmapDrawable result = new PicassoBitmapDrawable();

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected void onPreExecute() {
                Drawable d = resources.getDrawable(R.drawable.post_image_placeholder);
                result.setDrawable(d);
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    return Picasso.with(context).load(source).get();
                } catch (Exception ex) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                try {
                    BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);

                    int[] l = new int[2];
                    textView.getLocationOnScreen(l);
                    int width = textView.getWidth();
                    int height = (int) ((double) width / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());

                    drawable.setBounds(0, 0, width, height);
                    result.setDrawable(drawable);
                    result.setBounds(0, 0, width, height);

                    textView.setText(textView.getText());
                } catch (Exception ex) {
                    // TODO: handle exception
                }
            }

        }.execute();

        return result;
    }

    private static class PicassoBitmapDrawable extends BitmapDrawable {

        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null)
                drawable.draw(canvas);
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

    }

}
