package devmike.jade.com

import android.view.View
import devmike.jade.com.listeners.OnClickStepListener

interface  PageStepIndicatorViewPager<VP> {

    fun setupWithViewPager(viewPager: VP)

    fun getPageStepIndicator(): PageStepIndicator

    fun disablePageChange(): Boolean

    fun getCurrentStepPosition(): Int

    fun setCurrentStepPosition(currentItem: Int)

    fun setStepsCount(stepsCount: Int)

    fun getCount(): Int

    fun setOnClickStepListener(onClickStepListener: OnClickStepListener?)

    fun setOnClickListener(onClickListener: PageStepIndicatorImpl.OnClickListener)

}