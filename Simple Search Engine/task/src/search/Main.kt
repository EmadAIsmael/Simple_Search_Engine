package search

fun main() {
    val line = readLine()!!.split(" ")
    val word = readLine()!!

    for ((i, w) in line.withIndex()) {
        if (w == word) {
            println(i + 1)
            return
        }
    }

    println("Not Found")
}
