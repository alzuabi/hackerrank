import kotlin.collections.*
import kotlin.io.*
import kotlin.text.*

/*
 * Complete the 'poisonousPlants' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts INTEGER_ARRAY p as parameter.
 */
data class Node(var prev: Node?, val value: Int, var next: Node?)


fun poisonousPlants(p: Array<Int>): Int {
    // Write your code here


    var toBeDeleted = mutableListOf<Node>()
    var previousNode: Node? = null
    for (value in p.reversed()) {
        val currentNode = Node(previousNode, value, null)
        previousNode?.next = currentNode
        previousNode = currentNode
        currentNode.prev?.let {prev->
            if (prev.value > currentNode.value) toBeDeleted.add(prev)
        }
    }

    var days = 0

    while (toBeDeleted.isNotEmpty()) {
        val toBeDeletedTemp = mutableListOf<Node>()
        toBeDeleted.forEach { nodeToBeDeleted ->
            nodeToBeDeleted.prev?.next = nodeToBeDeleted.next
            nodeToBeDeleted.next?.prev = nodeToBeDeleted.prev
            if (nodeToBeDeleted.next != null && nodeToBeDeleted.prev != null && nodeToBeDeleted.next?.value!! < nodeToBeDeleted.prev!!.value)
                toBeDeletedTemp.add(nodeToBeDeleted.prev!!)
        }
        toBeDeleted = toBeDeletedTemp
        days++
    }
    return days
}

fun checkIfOtherNodesToDelete(next: Node, prev: Node, toBeDeletedTemp: MutableList<Node>) {
    if (next.value < prev.value)
        toBeDeletedTemp.add(prev)
}

fun main(args: Array<String>) {
    val n = readln().trim().toInt()

    val p = readln().trimEnd().split(" ").map { it.toInt() }.toTypedArray()

    val result = poisonousPlants(p)

    println(result)
}
