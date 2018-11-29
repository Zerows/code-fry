import com.code.fry.Main
import org.junit.Test

class RubyTests {
    @Test
    fun success() {
        Main.main(arrayOf("-c", javaClass.getResource("input_ruby_success.json").path))
    }

    @Test
    fun fail() {
        Main.main(arrayOf("-c", javaClass.getResource("input_ruby_fail.json").path))
    }

}