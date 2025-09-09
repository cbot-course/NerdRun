package game.core

class Enemy(
    private val tileNumber: Int,
    name: String = "Goblin",
    health: Int = 2 * tileNumber,
    attack: Int = maxOf(1, (tileNumber * 0.25).toInt()),
    defense: Int = maxOf(1, (tileNumber * 0.10).toInt())

) {
    val name: String = when (tileNumber % 5) {
        0 -> "Goblin"
        1 -> "Orc"
        2 -> "Skeleton"
        3 -> "Slime"
        else -> "Dragon"
    }

    var health: Int = 2 * tileNumber
    val baseDamage: Int = maxOf(1, (tileNumber * 0.25).toInt())
    var defense: Int = maxOf(1, (tileNumber * 0.40).toInt())

    fun attack(): Int = baseDamage

    fun takeDamage(damage: Int): Boolean {
        health -= damage
        return health <= 0
    }

    override fun toString(): String = "$name (HP: $health, DMG: $baseDamage)"
}
