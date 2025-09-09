package game.game

import game.core.Character
import game.core.Warrior
import game.core.Enemy
import kotlin.random.Random

class NerdRunGame {
    private val random = Random.Default
    private var isGameRunning = true
    private var currentTile = 0
    private val player = Warrior()

    fun start() {
        println("Welcome to NerdRun!")
        println("Your adventure begins...")

        while (isGameRunning && player.isAlive) {
            println("\n--- Tile $currentTile ---")
            player.showStatus()
            
            println("\nChoose an action 1/2/3:")

            
            when (readLine()?.toIntOrNull()) {
                1 -> takeTurn()
                2 -> showAbilities()
                3 -> {
                    println("Thanks for playing!")
                    isGameRunning = false
                }
                else -> println("Invalid choice. Please select 1-3.")
            }
        }
        
        if (!player.isAlive) {
            println("\nGame Over! You were defeated on tile $currentTile")
        }
    }
    
    private fun takeTurn() {
        val roll = random.nextInt(1, 21)
        println("\nYou rolled a $roll")

        when (roll) {
            1 -> {
                // Critical failure
                val damage = random.nextInt(1, 4)
                player.takeDamage(damage)
                println("Critical failure! You stumble and take $damage damage!")
                if (player.isAlive) handleCombat()
            }
            in 2..4 -> {
                // Enemy encounter
                println("An enemy appears!")
                handleCombat()
            }
            in 5..15 -> {
                // Normal move
                currentTile++
                println("You advance to tile $currentTile")
            }
            in 16..19 -> {
                // Move with chance to heal
                currentTile++
                if (random.nextFloat() < 0.25f) {
                    player.warriorRage()
                    println("You find a moment of peace and recover some health!")
                } else {
                    println("You advance to tile $currentTile")
                }
            }
            20 -> {
                // Critical success
                currentTile += 2
                println("Critical success! You find a shortcut and advance 2 tiles to $currentTile")
            }
        }
    }
    
    private fun handleCombat() {
        val enemy = Enemy(currentTile)
        println("\nA wild ${enemy.name} appears!")
        
        while (enemy.health > 0 && player.isAlive) {
            println("\n${enemy}")
            player.showStatus()
            
            println("\nCombat options:")
            println("1. Attack (Basic Attack)")
            println("2. Rage Heal")
            
            when (readLine()?.toIntOrNull()) {
                1 -> {
                    player.attack(enemy)
                    if (enemy.health > 0) {
                        // Enemy counter-attacks if still alive
                        val damage = enemy.attack()
                        player.takeDamage(damage)
                    }
                }
                2 -> {
                    player.warriorRage()
                    // Enemy still attacks after healing
                    val damage = enemy.attack()
                    player.takeDamage(damage)
                }
                else -> println("Invalid choice. Try again.")
            }
        }
        
        if (player.isAlive) {
            println("\nYou defeated the ${enemy.name}!")
        }
    }
    
    private fun showAbilities() {
        println("\nWarrior Abilities:")
        println("1. Basic Attack - Deal damage equal to your attack power")
        println("2. Rage Heal (20 Rage) - Heal for 30% of max HP")
        println("3. Critical Strike (Passive) - 30% chance when at least 30 Rage")
        println("   to deal double damage")
        println("\nPress Enter to continue...")
        readLine()
    }
}

fun main() {
    val game = NerdRunGame()
    game.start()
}
