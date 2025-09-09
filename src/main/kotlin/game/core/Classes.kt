package game.core

open class Character(
     open val name: String,
     health: Int,
     attack: Int,
     defense: Int
) {
    // Default stats of a generic character

    open var health: Int = 10
    open val attack: Int = 1
    open val defense: Int = 1

    open val isAlive: Boolean
        get() = health > 0

    open fun showStatus() {
        println("=== ${name}'s Status ===")
        println("Health: $health")
        println("Attack: $attack")
        println("Defense: $defense")
    }

    open fun takeDamage(damage: Int): Boolean {
        health -= damage
        return health <= 0
    }

    open fun attack(target: Enemy): Int {
        val damage = attack - (0.25 * target.defense).toInt()
        if (damage > 0) {
            println("You deal $damage damage to ${target.name}!")
            target.health -= damage
        } else {
            println("Your attack missed ${target.name}!")
        }
        return damage
    }
}

class Warrior : Character(
    name = "Warrior",
    health = 10,
    attack = 2,
    defense = 1
) {
    val power: Int = 2


    fun warriorCrit(): Int {
        println("Critical strike! You dealt ${attack * power} damage!")
        return attack * power
    }

    fun warriorRage(): Int {
        println("Your rage fuels you! You heal for ${attack * power} health!")
        health += attack * power
        return health
    }

    override fun showStatus() {
        super.showStatus()
        println("Power: $power")
    }

}