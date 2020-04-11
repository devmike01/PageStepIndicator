package devmike.jade.com.pagestepindicator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import devmike.jade.com.PageStepIndicator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var pageStepper : PageStepIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        pageStepper = findViewById(R.id.page_stepper)

        val adapter = MyPagerAdapter(supportFragmentManager)
        vp.adapter = adapter
        pageStepper.setEnableStepClick(false)

        pageStepper.setupWithViewPager(vp)
    }

    class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

        override fun getItem(p0: Int): Fragment {
            return TestFragment.newInstance(p0)
        }

        override fun getCount(): Int {

            return 4
        }

    }

}
