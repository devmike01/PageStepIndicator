package fakes

import devmike.jade.com.PageStepIndicator

class FakePageStepIndicator : PageStepIndicator {

    private var currentStepPosition = 0

    override fun setOffset(position: Int, positionOffset: Float) {
        this.currentStepPosition = position
    }

    override fun setPagerScrollState(state: Int) {
        currentStepPosition = state
    }

    override fun setCurrentPosition(position: Int) {
        this.currentStepPosition = position
    }
}