import fakes.FakePageStepIndicatorViewPager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PageStepIndicatorViewPagerTest {

    var fakePageStepIndicator : FakePageStepIndicatorViewPager? = null

    @Before
    fun init(){
        fakePageStepIndicator = FakePageStepIndicatorViewPager()
    }


    @Test
    fun `check that getPageStepIndicator() returns PageStepIndicator)`(){

    }

    @After
    fun tearDown(){
        fakePageStepIndicator = null
    }
}