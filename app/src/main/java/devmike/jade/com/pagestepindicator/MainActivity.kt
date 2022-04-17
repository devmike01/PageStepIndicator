package devmike.jade.com.pagestepindicator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import devmike.jade.com.PageStepIndicatorImpl
import devmike.jade.com.listeners.OnClickStepListener

class MainActivity : AppCompatActivity() {

    lateinit var pageStepperImpl : PageStepIndicatorImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        pageStepperImpl = findViewById(R.id.page_stepper)

        val adapter = MyPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        val viewPager = findViewById<ViewPager>(R.id.vp).apply {
         this.adapter = adapter
        }

        pageStepperImpl.setOnClickStepListener(object : OnClickStepListener{
            override fun onClickStep(position: Int) {
                Log.d("onClickStep", "onClickStep => $position")
            }

        })


        viewPager.addOnAdapterChangeListener { viewPager, oldAdapter,
                                               newAdapter ->
            Log.d("addOnAdapterChange", "Not yet implemented")
        }
        pageStepperImpl.setupWithViewPager(viewPager)
    }

    class MyPagerAdapter(fm: FragmentManager, behaviour: Int) : FragmentStatePagerAdapter(fm, behaviour){

        override fun getItem(p0: Int): Fragment {
            return TestFragment.newInstance(p0)
        }

        override fun getCount(): Int {

            return 4
        }

    }

}
