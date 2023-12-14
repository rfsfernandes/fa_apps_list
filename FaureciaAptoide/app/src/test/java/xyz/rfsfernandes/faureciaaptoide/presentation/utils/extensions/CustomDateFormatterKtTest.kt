package xyz.rfsfernandes.faureciaaptoide.presentation.utils.extensions

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import xyz.rfsfernandes.faureciaaptoide.testutils.MainDispatcherRule

class CustomDateFormatterKtTest {
    @get:Rule
    val dispatcher = MainDispatcherRule()

    @Test
    fun `when string has date that doesn't match with regex return previous string`() {
        val value = "1235-123-51"
        val expected = value
        val actual = value.formatDate()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when string has date that match with regex return reformatted string with dd-MM-yyyy`() {
        val value = "2023-12-13 11:02:00"
        val expected = "13-12-2023"
        val actual = value.formatDate()
        Assert.assertEquals(expected, actual)
    }

}