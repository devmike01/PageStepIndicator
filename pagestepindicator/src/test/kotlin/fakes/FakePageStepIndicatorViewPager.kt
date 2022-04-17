package fakes

import devmike.jade.com.PageStepIndicator
import devmike.jade.com.PageStepIndicatorImpl
import devmike.jade.com.PageStepIndicatorViewPager
import devmike.jade.com.listeners.OnClickStepListener

class FakePageStepIndicatorViewPager : PageStepIndicatorViewPager<MockViewPager> {

    private var currentStepPosition = 0

    private var stepCount =0

    override fun setupWithViewPager(viewPager: MockViewPager) {}

    override fun getPageStepIndicator(): PageStepIndicator {
        return FakePageStepIndicator()
    }

    override fun disablePageChange(): Boolean {
        return false
    }

    override fun getCurrentStepPosition(): Int {
        return currentStepPosition
    }

    override fun setCurrentStepPosition(currentItem: Int) {
        this.currentStepPosition = currentItem
    }

    override fun setStepsCount(stepsCount: Int) {
        this.stepCount = stepsCount
    }

    override fun getCount(): Int {
        return stepCount
    }

    override fun setOnClickStepListener(onClickStepListener: OnClickStepListener?) {
        onClickStepListener?.onClickStep(2)
    }

    override fun setOnClickListener(onClickListener: PageStepIndicatorImpl.OnClickListener) {
        onClickListener.onClick(3)
    }
}