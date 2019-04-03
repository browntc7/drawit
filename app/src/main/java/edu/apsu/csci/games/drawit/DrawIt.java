package edu.apsu.csci.games.drawit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DrawIt extends View {
        private int currentWidth;
        private int currentHeight;
        private Paint paint;
        private Path path;

        public DrawIt(Context context) {
            super(context);
            setup(null);
        }

        public DrawIt(Context context, AttributeSet attrs) {
            super(context, attrs);
            setup(attrs);
        }

        public DrawIt(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setup(attrs);
        }

        private void setup(AttributeSet attrs) {
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(10);
            path = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paint);

        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

            int action = event.getAction();

            if(action == MotionEvent.ACTION_DOWN) {
                path.moveTo(event.getX(), event.getY());
            } else if (action == MotionEvent.ACTION_MOVE){
                path.lineTo(event.getX(), event.getY());
                invalidate();
            }
            return true;

    }

    /**
        * From ShapeView.java created by Dr. John Nicholson, APSU on 3/9/16
        * Allows screen to rotate without affecting canvas.
        */

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            currentWidth = w;
            currentHeight = h;
        }

        /**
         * Source and explanation
         * http://stackoverflow.com/questions/12266899/onmeasure-custom-view-explanation
         */
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int desiredWidth = 100;
            int desiredHeight = 100;

            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            int width;
            int height;

            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            } else if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(desiredWidth, widthSize);
            } else {
                width = desiredWidth;
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            } else if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desiredHeight, heightSize);
            } else {
                height = desiredHeight;
            }

            setMeasuredDimension(width, height);
        }



}
