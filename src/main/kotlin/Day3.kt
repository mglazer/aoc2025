import jdk.jshell.execution.Util

object Day3 {

    fun maxJoltage(batteries: String): Int {
        val batts = batteries.trim().map { c -> Integer.parseInt(c + "") }

        val allValues = mutableListOf<Int>()
        for (i in 0 until batts.size - 1) {
            for (j in i + 1 until batts.size) {
                allValues.add("${batts[i]}${batts[j]}".toInt())
            }
        }
        return allValues.max()
    }

    fun maxJoltageNum(batteries: String, count: Int): Long {
        val batts = batteries.trim().map { c -> Integer.parseInt(c + "") }

        val currentBatteries = mutableListOf<Int>()
        currentBatteries.addAll(batts.subList(batts.size - count, batts.size))
        val currentBatteriesIdx = mutableSetOf<Int>()
        currentBatteriesIdx.addAll((batts.size - count) until (batts.size))
        val origBatteriesIdx = mutableSetOf<Int>()
        origBatteriesIdx.addAll(currentBatteriesIdx)

        assert(currentBatteries.size == count)
        assert(currentBatteriesIdx.size == count)


//        batts.windowed(10, 10).forEachIndexed { idx, c -> println("$idx: $c") }

        var nextStart = 0
        for (i in 0 until currentBatteries.size) {
            var maxSoFar: Int? = null
            var maxIdxSoFar: Int? = null
            for (j in nextStart  .. origBatteriesIdx.min()) {
                if (batts[j] >= currentBatteries[i] && !currentBatteriesIdx.contains(j)) {
                    if (maxSoFar == null || maxSoFar < batts[j]) {
                        maxSoFar = batts[j]
                        maxIdxSoFar = j
                    }
                }
            }
            if (maxSoFar != null && maxIdxSoFar != null) {
                currentBatteries[i] = maxSoFar
                assert(currentBatteriesIdx.remove(batts.size - count + i))
                assert(currentBatteriesIdx.add(maxIdxSoFar))
                origBatteriesIdx.remove(batts.size - count + i)
                nextStart = maxIdxSoFar + 1

//                println("${maxSoFar} -> ${maxIdxSoFar} -> ${currentBatteries} -> ${currentBatteriesIdx}")
            } else {
                nextStart = origBatteriesIdx.min()
//                println("${nextStart} -> ${origBatteriesIdx}")
            }

        }

        var str = ""
        for (c in currentBatteries) {
            str += c.toString()
        }

        assert(str.length == count)

//        println("${batteries} -> ${str}")

        return str.toLong()
    }

    fun computeJoltage(input: String): Int {
        return input.lines().filter { line -> line.trim().isNotBlank() }.sumOf(Day3::maxJoltage)
    }

    fun computeJoltagePart2(input: String): Long {
        return input.lines().filter { line -> line.trim().isNotBlank() }.sumOf { maxJoltageNum(it, 12) }
    }

    val testInput = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent()

    val testInput2 = """
        2311122132123143222522221331121212224221322122222223325222332231123122322231132422222223134222323124
    """.trimIndent()
}

fun main() {
    val part1 = Day3.computeJoltage(Utils.load("inputs/day3.txt"))
    println("Part 1: $part1")

    val part2 = Day3.computeJoltagePart2(Utils.load("inputs/day3.txt"))
    println("Part 2: $part2")

}