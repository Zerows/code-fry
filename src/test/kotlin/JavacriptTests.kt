import com.code.fry.Main
import org.junit.Test

class JavacriptTests {
    @Test
    fun success() {
        Main.main(arrayOf("-c", javaClass.getResource("input_javascript_success.json").path))
    }

    @Test
    fun fail() {
        Main.main(arrayOf("-c", javaClass.getResource("input_javascript_fail.json").path))
    }

}