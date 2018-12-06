import com.google.gson.Gson
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import java.sql.PreparedStatement

/**
 * Created by quangio.
 */

fun <T : Any> Table.jsonb(name: String, klass: Class<T>): Column<T> = registerColumn(name, Json(klass))


private class Json<out T : Any>(private val klass: Class<T>) : ColumnType() {
    val jsonMapper = Gson()
    override fun sqlType() = "jsonb"

    override fun setParameter(stmt: PreparedStatement, index: Int, value: Any?) {
        val obj = PGobject()
        obj.type = "jsonb"
        obj.value = value as String
        stmt.setObject(index, obj)
    }

    override fun valueFromDB(value: Any): Any {
        value as PGobject
        return try {
            jsonMapper.fromJson(value.value, klass)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Can't parse JSON: $value")
        }
    }


    override fun notNullValueToDB(value: Any): Any = jsonMapper.toJson(value)
    override fun nonNullValueToString(value: Any): String = "'${jsonMapper.toJson(value)}'"
}