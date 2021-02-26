package search

import java.io.File
import kotlin.system.exitProcess


var data = arrayListOf<String>()
var invertedIndex = mutableMapOf<String, MutableList<Int>>()


fun ArrayList<String>.toInvertedIndex(): MutableMap<String, MutableList<Int>> {
    val ii = mutableMapOf<String, MutableList<Int>>()

    for ((i, line) in this.withIndex()) {
        for (word in line.split(" ")) {
            val key = word.toLowerCase()
            if (key in ii)
                ii[key]?.add(i)
            else {
                ii[key] = mutableListOf()
                ii[key]?.add(i)
            }
        }
    }
    return ii
}

fun readData(args: Array<String>) {
    val file = File(args[1])
    data = file.readLines() as ArrayList<String>
    invertedIndex = data.toInvertedIndex()
}

fun getQuery() {
    println("Select a matching strategy: ALL, ANY, NONE")
    val strategy = readLine()!!

    println("\nEnter a name or email to search all suitable people.")
    val query = readLine()!!

    val fp = findPeopleIndexed(query, strategy)
    println("${fp.size} persons found:")
    if (fp.isEmpty())
        println("No matching people found.")
    else {
        for (p in fp)
            println(data[p])
    }
}

fun findPeopleIndexed(query: String, strategy: String): MutableList<Int> {
    val keys = query.toLowerCase().split(" ")

    return when (strategy) {
        "ALL" ->
            data.mapIndexed { i, s -> i to s.toLowerCase() }
                .filter { (i, s) -> s.split(" ").containsAll(keys) }
                .map { it.first } as MutableList<Int>

        "ANY" ->
            invertedIndex
                .filter { it.key in keys }
                .flatMap { it.value }
                .toSet()
                .toMutableList()

        "NONE" ->
            invertedIndex
                .flatMap { it.value }
                .minus(invertedIndex.filter { it.key in keys }.flatMap { it.value })
                .toSet()
                .toMutableList()

        else -> {
            mutableListOf()
        }
    }
}

fun menu() {
    while (true) {
        println()
        println(
            """
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent()
        )
        when (readLine()!!.toInt()) {
            1 -> getQuery()
            2 -> printPeople()
            0 -> {
                println("Bye!")
                exitProcess(0)
            }
            else -> println("\nIncorrect option! Try again.")
        }
    }
}

fun printPeople() {
    println("\n=== List of people ===")
    for (p in data)
        println(p)
}

fun main(args: Array<String>) {
    readData(args)
    menu()
}
