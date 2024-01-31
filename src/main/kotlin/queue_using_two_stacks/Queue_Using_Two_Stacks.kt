package queue_using_two_stacks

import java.util.*


fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val q: Int = scanner.nextInt()
    val stack = Stack<Int>()
    (1..q).forEach {
        val command = scanner.nextInt()
        var number = 0
        if (command == 1)
            number = scanner.nextInt()
        when (command) {
            1 -> stack.push(number)
            2 -> stack.removeAt(0)
            else -> println(stack.elementAt(0))
        }
    }
}
