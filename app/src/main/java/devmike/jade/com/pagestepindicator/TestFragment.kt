package devmike.jade.com.pagestepindicator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.test_frag_layout.view.*

class TestFragment : Fragment(){


    companion object {
        val EXTRA_VALUE :String = "TestFragment._EXTRA_VALUE"

        fun newInstance(value: Int): TestFragment{

            val frag = TestFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_VALUE, value+1)
            frag.arguments = bundle
            return frag
        }
    }

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View= inflater.inflate(R.layout.test_frag_layout, container, false);
        view.numberTv.text = " ${arguments!!.getInt(EXTRA_VALUE)}"
        return view
    }

}