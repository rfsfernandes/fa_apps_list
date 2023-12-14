package xyz.rfsfernandes.faureciaaptoide.presentation.utils.extensions

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import xyz.rfsfernandes.faureciaaptoide.testutils.MainDispatcherRule

@RunWith(JUnit4::class)
class BigNumberFormatterKtTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    @Test
    fun `when value is a million then formatter returns 1M`() {
        val million = 1000000
        val expected = "1M"
        val actual = million.toSmallNumber()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when value is a 1500 then formatter returns 1,5k`() {
        val million = 1500
        val expected = "1.5k"
        val actual = million.toSmallNumber()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when value is a 1530 then formatter returns 1,5k`() {
        val million = 1500
        val expected = "1.5k"
        val actual = million.toSmallNumber()
        Assert.assertEquals(expected, actual)
    }
}