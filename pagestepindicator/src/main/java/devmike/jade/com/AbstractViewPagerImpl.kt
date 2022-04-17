package devmike.jade.com

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import devmike.jade.com.listeners.OnClickStepListener


abstract class AbstractViewPagerImpl : View, PageStepIndicatorViewPager<ViewPager> {

    var mStepsCount =0;

    var disablePageChange : Boolean = false

    private var onClickListener: PageStepIndicatorImpl.OnClickListener? = null

    private var onClickStepListener : OnClickStepListener? = null

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){}

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0){}

     //abstract fun  getPagerChangeListener(): ViewPagerOnChangeListenerImpl


    private val pageChangedListener = object : ViewPager.OnPageChangeListener{
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (!disablePageChange) {
                getPageStepIndicator().setOffset(position, positionOffset)
            }
        }

        override fun onPageSelected(position: Int) {
            if (!disablePageChange) {
                getPageStepIndicator().setCurrentPosition(position)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            getPageStepIndicator().setPagerScrollState(state)
        }

    }

     @SuppressLint("ClickableViewAccessibility")
     override fun setupWithViewPager(viewPager: ViewPager) {
         val adapter = viewPager.adapter
             ?: throw IllegalArgumentException("ViewPager does not have a PagerAdapter set")

       //  withViewpager = true
         // First we'll add Steps.
         setStepsCount(adapter.count)

         // Now we'll add our page change listene to the ViewPager
         viewPager.addOnPageChangeListener(pageChangedListener)

         // Now we'll add a selected listener to set ViewPager's currentStepPosition item
         setOnClickListener(object : PageStepIndicatorImpl.OnClickListener{
             override fun onClick(position: Int) {
                 //disablePageChange = true;
                 setCurrentStepPosition(position);
                 viewPager.currentItem = position;
             }

         });

         // Now we'll add a selected listener to set ViewPager's currentStepPosition item
        // setOnClickListener( ViewPagerOnSelectedListener(viewPager));

         viewPager.setOnTouchListener { v: View, event: MotionEvent ->
             if (event.actionMasked == MotionEvent.ACTION_MOVE) {
                 (v as ViewPager).addOnPageChangeListener(pageChangedListener)
               //  disablePageChange = false
             }
             false
         }

         // Make sure we reflect the currently set ViewPager item
         if (adapter.count > 0) {
             val curItem = viewPager.currentItem
             if (getCurrentStepPosition() != curItem) {
                 setCurrentStepPosition(curItem)
                 invalidate()
             }
         }
     }

     override fun setStepsCount(stepsCount: Int) {
         mStepsCount = stepsCount
         invalidate()
     }

    override fun getCount(): Int = mStepsCount

    fun setCompatClickStepListener(position: Int){
        this.onClickListener?.onClick(position)
        this.onClickStepListener?.onClickStep(position)
    }

    override fun setOnClickStepListener(onClickStepListener: OnClickStepListener?) {
        this.onClickStepListener = onClickStepListener
    }

    @Deprecated("[setOnClickListener] is @Deprecated.",
        ReplaceWith("setOnClickStepListener(OnClickStepListener onClickStepListener)")
    )
    override fun setOnClickListener(onClickListener: PageStepIndicatorImpl.OnClickListener) {
        this.onClickListener = onClickListener
    }



}