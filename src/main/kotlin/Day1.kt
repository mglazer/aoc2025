object Day1 {

    data class Move(val direction: Int, val distance: Int) {
        fun movement(): Int {
            return distance * direction
        }

        fun absMovement(): Int {
            return distance
        }
    }

    fun parseMoves(input: String): List<Move> {
        return input.lines().map{ it -> it.trim() }.map {
            val direction = if (it[0] == 'L') -1 else 1
            Move(direction, it.substring(1).toInt() )
        }
    }

    fun solvePart1(input: String): Int {
        val moves = parseMoves(input)

        val start = 50
        var zeroCounter = 0
        val result = moves.fold(start) { acc, move ->
            val result = (acc + move.movement()) % 100
            if (result == 0) zeroCounter++

            result
        }

        return zeroCounter
    }

    fun solvePart2(input: String): Int {
        val moves = parseMoves(input)

        var start = 50
        var zeroCounter = 0

        moves.forEach { move ->
            zeroCounter += move.absMovement().floorDiv(100)
            val prevStart = start
            start = (((start + move.movement()) % 100) + 100) % 100
            if (start == 0) zeroCounter++
            else if (move.direction <0 && prevStart < start && prevStart != 0) zeroCounter++
            else if (move.direction >0 && prevStart > start && prevStart != 0) zeroCounter++

        }

        return zeroCounter
    }


    val testInput = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
    """.trimIndent()

}

fun main() {
    val part1 = Day1.solvePart1(Utils.load("inputs/day1.txt"))
    println("Part 1: $part1")

    val part2 = Day1.solvePart2(Utils.load("inputs/day1.txt"))
//    val part2 = Day1.solvePart2(Day1.testInput)
    println("Part 2: $part2")
}

