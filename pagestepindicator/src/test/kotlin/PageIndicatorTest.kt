import fakes.FakePageStepIndicator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PageIndicatorTest {

    lateinit var pageStepIndicator : FakePageStepIndicator

    @Before
    fun `init`(){
        pageStepIndicator = FakePageStepIndicator()
    }

    @Test
    fun `check setOffset `(){

    }

}