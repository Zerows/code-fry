import com.code.fry.Main
import org.junit.Test

class JavaTests {
    @Test
    fun success() {
        Main.main(arrayOf("-c", javaClass.getResource("input_java_success.json").path))
    }

    @Test
    fun fail() {
        Main.main(arrayOf("-c", javaClass.getResource("input_java_fail.json").path))
    }

}