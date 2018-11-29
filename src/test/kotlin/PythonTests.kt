import com.code.fry.Main
import org.junit.Test

class PythonTests {
    @Test
    fun success() {
        Main.main(arrayOf("-c", javaClass.getResource("input_python_success.json").path))
    }

    @Test
    fun fail() {
        Main.main(arrayOf("-c", javaClass.getResource("input_python_fail.json").path))
    }

}