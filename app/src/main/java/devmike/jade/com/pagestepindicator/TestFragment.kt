package devmike.jade.com.pagestepindicator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

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

    @SuppressLint("SetTextI18n")
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    = (inflater.inflate(R.layout.test_frag_layout, container, false)).let{view ->
        view.findViewById<TextView>(R.id.number_tv).run {
            this.text = " ${requireArguments().getInt(devmike.jade.com.pagestepindicator.TestFragment.EXTRA_VALUE)}"
        }
        view
    }

}