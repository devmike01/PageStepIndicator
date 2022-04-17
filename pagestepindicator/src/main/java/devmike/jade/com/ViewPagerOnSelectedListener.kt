package devmike.jade.com

import androidx.viewpager.widget.ViewPager
import devmike.jade.com.PageStepIndicatorImpl


class ViewPagerOnSelectedListener(private var mCurrentStepPosition: Int,
                                  private var disablePageChange: Boolean,
                                  private val onInvalidate: () -> Unit,
                                  private val mViewPager: ViewPager) : PageStepIndicatorImpl.OnClickListener {

    override fun onClick(position: Int) {
        disablePageChange = true
        setCurrentStepPosition(position)
        mViewPager.currentItem = position
    }

    private fun setCurrentStepPosition(currentStepPosition: Int) {
        mCurrentStepPosition = currentStepPosition
        onInvalidate.invoke()
        //invalidate()
    }
}