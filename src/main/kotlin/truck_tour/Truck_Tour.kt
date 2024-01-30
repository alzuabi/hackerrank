package truck_tour

import kotlin.collections.*
import kotlin.io.*
import kotlin.ranges.*
import kotlin.text.*

/*
 * Complete the 'truckTour' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts 2D_INTEGER_ARRAY petrolpumps as parameter.
 */


fun truckTour(petrolpumps: Array<Array<Int>>): Int {

    (0..petrolpumps.size).forEach {
        if(checkP(it, petrolpumps)) return it
    }
    return -1
}

fun checkP(index: Int, petrolpumps: Array<Array<Int>>): Boolean {
    var currentIndex = index
    var currentPetrol = petrolpumps[index][0]
    var currentDistance = petrolpumps[index][1]
    var nextIndex:Int

    while (currentPetrol >= currentDistance) {
        nextIndex = (currentIndex + 1) % petrolpumps.size
        if (nextIndex == index) return true
        else {
            currentPetrol -= currentDistance
            currentIndex = nextIndex
            currentPetrol += petrolpumps[currentIndex][0]
            currentDistance = petrolpumps[currentIndex][1]
        }
    }
    return false
}

fun main(args: Array<String>) {
    val n = readln().trim().toInt()

    val petrolpumps = Array<Array<Int>>(n, { Array<Int>(2, { 0 }) })

    for (i in 0 until n) {
        petrolpumps[i] = readln().trimEnd().split(" ").map { it.toInt() }.toTypedArray()
    }

    val result = truckTour(petrolpumps)

    println(result)
}
