object Day2 {

    fun String.isValid(): Boolean {
        return !this.startsWith("0") && !this.zipWithNext().any { it.first == it.second }
    }

    fun String.validOrNull(): Int? {
        return if (this.isValid()) {
            null
        } else {
            this.toInt()
        }
    }

    data class NumberRange(val start: String, val end: String) {
        fun invalidInRange(): List<Long> {
            val range = start.toLongOrNull()!!..end.toLongOrNull()!!
            return range.filter(this::isInvalid)
        }

        fun isInvalid(number: Long): Boolean {
            val strValue = number.toString()
            if (strValue.length % 2 != 0) {
                return false
            }

            val firstPart = strValue.substring(0, strValue.length / 2)
            val lastPart = strValue.substring(strValue.length / 2)

            return firstPart == lastPart
        }

        fun invalidInRange2(): List<Long> {
            val range = start.toLongOrNull()!!..end.toLongOrNull()!!
            return range.filter(this::isInvalid2)
        }

        fun isInvalid2(number: Long): Boolean {
            val strValue = number.toString()

            for (window in 1..strValue.length / 2) {
                val windowed = strValue.windowed(window, window, true)
                if (windowed.zipWithNext().all { it.first == it.second }) {
                    return true
                }
            }

            return false
        }

    }

    fun part1(input: String): Long {
        return input.split(",").map(String::trim).map{
            val parts = it.split("-")
            NumberRange(parts[0], parts[1])
        }.flatMap { it.invalidInRange() }
            .sum()
    }

    fun part2(input: String): Long {
        return input.split(",").map(String::trim).map{
            val parts = it.split("-")
            NumberRange(parts[0], parts[1])
        }.flatMap { it.invalidInRange2() }.sum()
    }

    val testInput = """
    11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
    1698522-1698528,446443-446449,38593856-38593862,565653-565659,
    824824821-824824827,2121212118-2121212124
    """
}

fun main() {
    val part1 = Day2.part1(Utils.load("inputs/day2.txt"))
    println("Part 1: $part1")

    val part2 = Day2.part2(Utils.load("inputs/day2.txt"))
    println("Part 2: $part2")
}