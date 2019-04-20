package devmike.jade.com;

import android.animation.*;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ViewUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.devmike.pagestepindicator.R;

import java.util.ArrayList;


public class PageStepIndicator extends View {

    private static final int DEFAULT_STEP_RADIUS = 14;   //DP
    private static final int DEFAULT_STOKE_WIDTH = 6;  //DP
    private static final int DEFAULT_STEP_COUNT = 4;  //DP
    private static final int DEFAULT_BACKGROUND_COLOR = R.color.background_default;
    private static final int DEFAULT_STEP_COLOR = R.color.step_default;
    private static final int DEFAULT_CURRENT_STEP_COLOR = R.color.current_step_default;
    private static final int DEFAULT_INACTIVE_TITLE= R.color.lighter_gray;
    private static final int DEFAULT_TEXT_COLOR = R.color.text_default;
    private static final int DEFAULT_SECONDARY_TEXT_COLOR = R.color.secondary_text_default;
    public static final float DEFAULT_LINE_HEIGHT =6.0f;
    public static final int DEFAULT_STROKE_ALPHA = 100;
    private static final int DEFAULT_TITLE_SIZE =14;

    private String[] titles;

    private int radius;
    private int pageStrokeAlpha;
    private int pageTitleId;
    private boolean isTitleClickable;
    private int pageActiveTitleColor;
    private int pageInActiveTitleColor;
    private float titleTextSize;
    private float defaultTitleSize;
    private float mLineHeight;
    private int strokeWidth;
    private int currentStepPosition;
    private int stepsCount = 1;
    private int backgroundColor;
    private int stepColor;
    private int currentColor;
    private int textColor;
    private int secondaryTextColor;

    private int centerY;
    private int startX;
    private int endX;
    private int stepDistance;
    private float offset;
    private int offsetPixel;
    private int pagerScrollState;

    private Paint paint;
    private Paint pStoke;
    private Paint pText;
    private Paint tText;
    private int titleSize;
    private final Rect textBounds = new Rect();
    private OnClickListener onClickListener;
    private float[] hsvCurrent = new float[3];
    private float[] hsvBG = new float[3];
    private float[] hsvProgress = new float[3];

    private boolean clickable = true;
    private boolean withViewpager;
    private ViewPagerOnChangeListener viewPagerChangeListener;
    private boolean disablePageChange;
    private TabLayout.OnTabSelectedListener onTabSelectedListener;

    public PageStepIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public PageStepIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PageStepIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PageStepIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void init(Context context, AttributeSet attributeSet) {

        initAttributes(context, attributeSet);

        paint = new Paint();
        pStoke = new Paint();
        pText = new Paint();
        tText = new Paint();


        defaultTitleSize = radius *1.2f;

        paint.setColor(stepColor);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(mLineHeight);


        pStoke.setColor(stepColor);
        pStoke.setStrokeWidth(strokeWidth);
        pStoke.setStyle(Paint.Style.STROKE);
        pStoke.setFlags(Paint.ANTI_ALIAS_FLAG);

        /**
         * @titleTextSize must not be greater than 19
         */

        tText.setTextSize(titleTextSize);
        tText.setColor(pageInActiveTitleColor);
        tText.setTextAlign(Paint.Align.CENTER);
        tText.setFlags(Paint.ANTI_ALIAS_FLAG);
        tText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        pText.setColor(textColor);
        pText.setTextSize(radius * 1.2f);
        pText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setFlags(Paint.ANTI_ALIAS_FLAG);
        setMinimumHeight(radius * 7);
        Color.colorToHSV(currentColor, hsvCurrent);
        Color.colorToHSV(backgroundColor, hsvBG);
        Color.colorToHSV(stepColor, hsvProgress);
        //animateView(tText, currentColor, currentColor);
        initAnimation();
        invalidate();
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.PageStepIndicator, 0, 0);
        if (attr == null) {
            return;
        }

        try {
            titleTextSize = (int) attr.getDimension(R.styleable.PageStepIndicator_pgTitleTextSize, dp2px(DEFAULT_TITLE_SIZE));
            //pgStrokeAlpha = attr.getInteger(R.styleable.PageStepIndicator_pgStrokeAlpha, DEFAULT_STROKE_ALPHA);
            radius = (int) attr.getDimension(R.styleable.PageStepIndicator_pgRadius, dp2px(DEFAULT_STEP_RADIUS));
            strokeWidth = (int) attr.getDimension(R.styleable.PageStepIndicator_pgStrokeWidth, dp2px(DEFAULT_STOKE_WIDTH));
            stepsCount = attr.getInt(R.styleable.PageStepIndicator_pgStepCount, DEFAULT_STEP_COUNT);
            mLineHeight = attr.getDimension(R.styleable.PageStepIndicator_pgLineHeight, DEFAULT_LINE_HEIGHT);
            stepColor = attr.getColor(R.styleable.PageStepIndicator_pgStepColor, ContextCompat.getColor(context, DEFAULT_STEP_COLOR));
            currentColor = attr.getColor(R.styleable.PageStepIndicator_pgCurrentStepColor, ContextCompat.getColor(context, DEFAULT_CURRENT_STEP_COLOR));
            backgroundColor = attr.getColor(R.styleable.PageStepIndicator_pgBackgroundColor, ContextCompat.getColor(context, DEFAULT_BACKGROUND_COLOR));
            textColor = attr.getColor(R.styleable.PageStepIndicator_pgTextColor, ContextCompat.getColor(context, DEFAULT_TEXT_COLOR));
            secondaryTextColor = attr.getColor(R.styleable.PageStepIndicator_pgSecondaryTextColor, ContextCompat.getColor(context, DEFAULT_SECONDARY_TEXT_COLOR));
            pageInActiveTitleColor = attr.getColor(R.styleable.PageStepIndicator_pgInActiveTitleColor, ContextCompat.getColor(context, DEFAULT_INACTIVE_TITLE));
            pageActiveTitleColor = attr.getColor(R.styleable.PageStepIndicator_pgActiveTitleColor,  ContextCompat.getColor(context, DEFAULT_TEXT_COLOR));
            pageTitleId = attr.getResourceId(R.styleable.PageStepIndicator_pgTitles, View.NO_ID);
            pageStrokeAlpha = attr.getInt(R.styleable.PageStepIndicator_pgStrokeAlpha, DEFAULT_STROKE_ALPHA);

        } finally {
            attr.recycle();
        }
    }

    public void addOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener){
        this.onTabSelectedListener =onTabSelectedListener;
    }


    @SuppressLint("NewApi")
    protected float dp2px(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
        invalidate();
    }

    public int getCurrentStepPosition() {
        return currentStepPosition;
    }

    public void setCurrentStepPosition(int currentStepPosition) {
        this.currentStepPosition = currentStepPosition;
        invalidate();
    }


    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        if (viewPagerChangeListener == null) {
            viewPagerChangeListener = new ViewPagerOnChangeListener(this);
        }
        withViewpager = true;
        // First we'll add Steps.
        setStepsCount(adapter.getCount());

        // Now we'll add our page change listener to the ViewPager
        viewPager.addOnPageChangeListener(viewPagerChangeListener);

        // Now we'll add a selected listener to set ViewPager's currentStepPosition item
        setOnClickListener(new ViewPagerOnSelectedListener(viewPager));

        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    ((ViewPager) v).addOnPageChangeListener(viewPagerChangeListener);
                    disablePageChange = false;
                }
                return false;
            }
        });

        // Make sure we reflect the currently set ViewPager item
        if (adapter.getCount() > 0) {
            final int curItem = viewPager.getCurrentItem();
            if (getCurrentStepPosition() != curItem) {
                setCurrentStepPosition(curItem);
                invalidate();
            }
        }
    }


    public void setTitles(@ArrayRes int id){
        //Set page titles through java
        this.pageTitleId = id;
    }

    public void setTitles(String[] titles){
        this.titles = titles;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stepsCount <= 1) {
            setVisibility(GONE);
            return;
        }
        super.onDraw(canvas);
        int pointX = startX;
        int pointOffset;

        /** draw Line */
        for (int i = 0; i < stepsCount - 1; i++) {
            if (i < currentStepPosition) {
                paint.setColor(stepColor);
                canvas.drawLine(pointX, centerY, pointX + stepDistance, centerY, paint);
            } else if (i == currentStepPosition) {
                paint.setColor(backgroundColor);
                canvas.drawLine(pointX, centerY, pointX + stepDistance, centerY, paint);
            } else {
                paint.setColor(backgroundColor);
                canvas.drawLine(pointX, centerY, pointX + stepDistance, centerY, paint);
            }
            pointX = pointX + stepDistance;
        }

        /**draw progress Line  */
        if (offsetPixel != 0 && pagerScrollState == 1) {
            pointOffset = startX + (currentStepPosition * stepDistance);
            int drawOffset = pointOffset + offsetPixel;
            if (drawOffset >= startX && drawOffset <= endX) {
                if (offsetPixel < 0) {
                    paint.setColor(backgroundColor);
                } else {
                    paint.setColor(stepColor);
                }
                canvas.drawLine(pointOffset, centerY, drawOffset, centerY, paint);
            }
        }

        /**draw Circle */
        pointX = startX;
        for (int i = 0; i < stepsCount; i++) {
            if (i < currentStepPosition) {
                //draw previous step
                paint.setColor(stepColor);
                canvas.drawCircle(pointX, centerY, radius, paint);

                //draw transition
                if (i == currentStepPosition - 1 && offsetPixel < 0 && pagerScrollState == 1) {
                    pStoke.setAlpha(pageStrokeAlpha);
                    pStoke.setStrokeWidth(strokeWidth - Math.round(strokeWidth * offset));
                    canvas.drawCircle(pointX, centerY, radius, pStoke);
                }

                pText.setColor(secondaryTextColor);

                tText.setColor(pageInActiveTitleColor);
                animateView(tText, pageActiveTitleColor, pageInActiveTitleColor, canvas);

            } else if (i == currentStepPosition) {
                //draw current step
                if (offsetPixel == 0 || pagerScrollState == 0) {
                    //set stroke default
                    paint.setColor(currentColor);
                    pStoke.setStrokeWidth(Math.round(strokeWidth));
                    pStoke.setAlpha(pageStrokeAlpha);
                } else if (offsetPixel < 0) {
                    pStoke.setStrokeWidth(Math.round(strokeWidth * offset));
                    pStoke.setAlpha(Math.round(offset * 11f));
                    paint.setColor(getColorToBG(offset));
                } else {
                    //set stroke transition
                    paint.setColor(getColorToProgress(offset));
                    pStoke.setStrokeWidth(strokeWidth - Math.round(strokeWidth * offset));
                    pStoke.setAlpha(255 - Math.round(offset * pageStrokeAlpha));
                }
                canvas.drawCircle(pointX, centerY, radius, paint);
                canvas.drawCircle(pointX, centerY, radius, pStoke);
                pText.setColor(textColor);

                tText.setColor(pageActiveTitleColor);
                animateView(tText, pageInActiveTitleColor, pageActiveTitleColor, canvas);

            } else {
                //draw next step
                paint.setColor(backgroundColor);
                canvas.drawCircle(pointX, centerY, radius, paint);
                pText.setColor(secondaryTextColor);

                tText.setColor(pageInActiveTitleColor);
                animateView(tText, pageActiveTitleColor, pageInActiveTitleColor, canvas);

                //draw transition
                if (i == currentStepPosition + 1 && offsetPixel > 0 && pagerScrollState == 1) {
                    pStoke.setStrokeWidth(Math.round(strokeWidth * offset));
                    pStoke.setAlpha(Math.round(offset * pageStrokeAlpha));
                    canvas.drawCircle(pointX, centerY, radius, pStoke);
                }
            }
            //Draw title text
            if(pageTitleId != View.NO_ID) {

                titles = getContext().getResources().getStringArray(pageTitleId);

                //Draw titles
                drawTextBottom(canvas, tText, titles[i], pointX, (getHeight()-(titleTextSize)));
            }
            drawTextCentred(canvas, pText, String.valueOf(i + 1), pointX, centerY);


            pointX = pointX + stepDistance;
        }

    }

    private void initAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(7500L);
        animation.setInterpolator(new LinearInterpolator());
        //startAnimation(animation);
    }

    private void animateView(Paint target, @ColorInt int defaultColor, @ColorInt int toColor, Canvas canvas){
        //new ArgbEvaluator(),
        //TODO: Do animation works here
        ObjectAnimator animator = ObjectAnimator.ofObject(target,
                "color", new ArgbEvaluator(),   toColor, defaultColor);
        animator.setDuration(2000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animFrac =animation.getAnimatedFraction();
                tText.setColor(Color.BLACK);
                invalidate();
               // canvas.translate(0, 50);
            }
        });
        animator.start();

    }

    private void drawTextCentred(Canvas canvas, Paint paint, String text, float cx, float cy) {
        paint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint);
    }

    private void drawTextBottom(Canvas canvas, Paint paint, String text, float cx, float cy) {
        paint.getTextBounds(text, 0, text.length(), textBounds);
        Path path = new Path();

        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint);
    }

    private int getColorToBG(float offset) {
        offset = Math.abs(offset);
        float[] hsv = new float[3];
        hsv[0] = hsvBG[0] + (hsvCurrent[0] - hsvBG[0]) * offset;
        hsv[1] = hsvBG[1] + (hsvCurrent[1] - hsvBG[1]) * offset;
        hsv[2] = hsvBG[2] + (hsvCurrent[2] - hsvBG[2]) * offset;
        return Color.HSVToColor(hsv);
    }

    private int getColorToProgress(float offset) {
        offset = Math.abs(offset);
        float[] hsv = new float[3];
        hsv[0] = hsvCurrent[0] + (hsvProgress[0] - hsvCurrent[0]) * offset;
        hsv[1] = hsvCurrent[1] + (hsvProgress[1] - hsvCurrent[1]) * offset;
        hsv[2] = hsvCurrent[2] + (hsvProgress[2] - hsvCurrent[2]) * offset;
        return Color.HSVToColor(hsv);
    }

    private void setOffset(float offset, int position) {
        this.offset = offset;
        offsetPixel = Math.round(stepDistance * offset);
        if (currentStepPosition > position) {
            offsetPixel = offsetPixel - stepDistance;
        } else {
            currentStepPosition = position;
        }

        invalidate();
    }

    private void setPagerScrollState(int pagerScrollState) {
        this.pagerScrollState = pagerScrollState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!clickable)
            return super.onTouchEvent(event);
        int pointX = startX;
        int xTouch;
        int yTouch;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);
                for (int i = 0; i < stepsCount; i++) {
                    if (Math.abs(xTouch - pointX) < radius + 5 && Math.abs(yTouch - centerY) < radius + 5) {
                        if (!withViewpager) {
                            setCurrentStepPosition(i);
                        }

                        if (onClickListener != null) {
                            onClickListener.onClick(i);
                        }
                    }
                    pointX = pointX + stepDistance;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, radius * 3);
        centerY = getHeight() /2;
        startX = radius * 2;
        endX = getWidth() - (radius * 2);
        stepDistance = (endX - startX) / (stepsCount - 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY = getHeight() / 2;
        startX = radius * 2;
        endX = getWidth() - (radius * 2);
        stepDistance = (endX - startX) / (stepsCount - 1);
        invalidate();
    }

    public class ViewPagerOnChangeListener implements ViewPager.OnPageChangeListener {
        private final PageStepIndicator stepIndicator;

        public ViewPagerOnChangeListener(PageStepIndicator stepIndicator) {
            this.stepIndicator = stepIndicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (!disablePageChange) {
                stepIndicator.setOffset(positionOffset, position);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (!disablePageChange) {
                stepIndicator.setCurrentStepPosition(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            stepIndicator.setPagerScrollState(state);
        }

    }

    public class ViewPagerOnSelectedListener implements OnClickListener {
        private final ViewPager mViewPager;

        public ViewPagerOnSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onClick(int position) {
            disablePageChange = true;
            setCurrentStepPosition(position);
            mViewPager.setCurrentItem(position);
        }
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.mLineHeight = this.mLineHeight;
        ss.radius = this.radius;
        ss.strokeWidth = this.strokeWidth;
        ss.currentStepPosition = this.currentStepPosition;
        ss.stepsCount = this.stepsCount;
        ss.backgroundColor = this.backgroundColor;
        ss.stepColor = this.stepColor;
        ss.currentColor = this.currentColor;
        ss.textColor = this.textColor;
        ss.secondaryTextColor = this.secondaryTextColor;
        ss.titleTextSize = this.titleSize;
        ss.pageActiveTitleColor = this.pageActiveTitleColor;
        ss.pageInActiveTitleColor = this.pageInActiveTitleColor;
        ss.pageTitleId = this.pageTitleId;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mLineHeight =ss.mLineHeight;
        this.radius = ss.radius;
        this.strokeWidth = ss.strokeWidth;
        this.currentStepPosition = ss.currentStepPosition;
        this.stepsCount = ss.stepsCount;
        this.backgroundColor = ss.backgroundColor;
        this.stepColor = ss.stepColor;
        this.currentColor = ss.currentColor;
        this.textColor = ss.textColor;
        this.secondaryTextColor = ss.secondaryTextColor;
        this.titleTextSize =ss.titleTextSize;
        this.pageActiveTitleColor = ss.pageActiveTitleColor;
        this.pageInActiveTitleColor = ss.pageInActiveTitleColor;
        this.pageTitleId = ss.pageTitleId;
    }

    static class SavedState extends BaseSavedState {
        int radius;
        float mLineHeight;
        int strokeWidth;
        int currentStepPosition;
        int stepsCount;
        int backgroundColor;
        int stepColor;
        int currentColor;
        int textColor;
        int secondaryTextColor;
        int titleTextSize;
        int pageStrokeAlpha;
        int pageTitleId;

        private boolean isTitleClickable;
        private int pageActiveTitleColor;
        private int pageInActiveTitleColor;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mLineHeight = in.readFloat();
            radius = in.readInt();
            strokeWidth = in.readInt();
            currentStepPosition = in.readInt();
            stepsCount = in.readInt();
            backgroundColor = in.readInt();
            stepColor = in.readInt();
            currentColor = in.readInt();
            textColor = in.readInt();
            secondaryTextColor = in.readInt();
            titleTextSize = in.readInt();
            //isTitleClickable = in.readBoo
            pageActiveTitleColor = in.readInt();
            pageInActiveTitleColor = in.readInt();
            pageStrokeAlpha = in.readInt();
            pageTitleId = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(mLineHeight);
            dest.writeInt(radius);
            dest.writeInt(strokeWidth);
            dest.writeInt(currentStepPosition);
            dest.writeInt(stepsCount);
            dest.writeInt(backgroundColor);
            dest.writeInt(stepColor);
            dest.writeInt(currentColor);
            dest.writeInt(textColor);
            dest.writeInt(secondaryTextColor);
            dest.writeInt(titleTextSize);
            dest.writeInt(pageActiveTitleColor);
            dest.writeInt(pageInActiveTitleColor);
            dest.writeInt(pageStrokeAlpha);
            dest.writeInt(pageTitleId);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
