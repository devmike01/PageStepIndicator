package fakes

import devmike.jade.com.PageStepIndicator

class FakePageStepIndicator : PageStepIndicator {

    private var currentStepPosition = 0

    var state = 0

    override fun setPagerScrollState(state: Int) {
        this.state = state
    }

    override fun setCurrentPosition(position: Int) {
        this.currentStepPosition = position
    }

    fun getCurrentStepPosition(): Int{
        return currentStepPosition
    }

}