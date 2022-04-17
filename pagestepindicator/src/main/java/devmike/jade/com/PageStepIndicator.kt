package devmike.jade.com

import android.annotation.SuppressLint
import androidx.viewpager.widget.ViewPager

interface PageStepIndicator  {

    fun setPagerScrollState(state: Int)

    fun setCurrentPosition(position: Int)

}