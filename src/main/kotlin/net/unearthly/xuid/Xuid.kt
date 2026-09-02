package net.unearthly.xuid

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.jdbc.selectAll
import java.util.UUID

@JvmInline
value class Xuid(val value: Long) {

    val isValid: Boolean
        get() = value > 0L

    fun toHexString(): String = String.format("%016x", value)
    fun toBedrockUuid(): UUID = UUID(0L, value)

    override fun toString(): String = value.toString()

    companion object {
        fun parseOrNull(text: String?): Xuid? {
            return text?.toLongOrNull()?.let { Xuid(it) }
        }
        
        fun fromHexOrNull(hex: String?): Xuid? {
            return hex?.toLongOrNull(16)?.let { Xuid(it) }
        }

        fun fromBedrockUuidOrNull(uuid: UUID): Xuid? {
            return if (uuid.mostSignificantBits == 0L && uuid.leastSignificantBits != 0L) {
                Xuid(uuid.leastSignificantBits)
            } else null
        }
    }
}

fun Table.xuid(name: String): Column<Xuid> = long(name).transform(
    wrap = { value -> Xuid(value) },
    unwrap = { xuid -> xuid.value }
)

fun <T : Table> T.selectByXuid(column: Column<Xuid?>, string: String): Query {
    val parsedXuid = Xuid.parseOrNull(string) ?: return selectAll().where { Op.FALSE }
    return selectAll().where { column eq parsedXuid }
}