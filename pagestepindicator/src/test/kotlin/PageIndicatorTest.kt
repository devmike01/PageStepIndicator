import fakes.FakePageStepIndicator
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PageIndicatorTest {

    var pageStepIndicator : FakePageStepIndicator? = null

    @Before
    fun `init`(){
        pageStepIndicator = FakePageStepIndicator()
    }

    @Test
    fun `check value passed into setCurrentPosition is correct `(){
        pageStepIndicator?.setCurrentPosition(4)
        assertEquals(pageStepIndicator?.getCurrentStepPosition(), 4)
    }

    @Test
    fun `Check setPagerScrollState`(){
        pageStepIndicator?.setPagerScrollState(43)
        assertEquals(pageStepIndicator?.state, 43)

    }



    @After
    fun `tear down`(){
        pageStepIndicator = null;
    }

}