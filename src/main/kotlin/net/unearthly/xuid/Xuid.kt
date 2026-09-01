package net.unearthly.xuid

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table

@JvmInline
value class Xuid(val value: Long) {
    override fun toString(): String = value.toString()
}

fun Table.xuid(name: String): Column<Xuid> = long(name).transform(
    wrap = { value -> Xuid(value) },
    unwrap = { xuid -> xuid.value }
)