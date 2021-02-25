package search

import java.io.File
import kotlin.system.exitProcess


var data = arrayListOf<String>()
var invertedIndex = mutableMapOf<String, MutableList<Int>>()


fun getData() {
    println("Enter the number of people:")
    val numPeople = readLine()!!.toInt()

    println("Enter all people:")
    repeat(numPeople) {
        print("> ")
        data.add(readLine()!!)
    }
    println()
}

fun ArrayList<String>.toInvertedIndex(): MutableMap<String, MutableList<Int>> {
    val ii = mutableMapOf<String, MutableList<Int>>()

    for ((i, line) in this.withIndex()) {
        for (word in line.split(" ")) {
            if (word.toLowerCase() in ii)
                ii[word.toLowerCase()]?.add(i)
            else {
                ii.put(word.toLowerCase(), mutableListOf<Int>())
                ii[word.toLowerCase()]?.add(i)
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
    println("\nEnter a name or email to search all suitable people.")
    val query = readLine()!!

    // println("\nFound people:")
    // val fp = findPeople(query)
    val fp = findPeopleIndexed(query)
    println("${fp.size} persons found:")
    if (fp.isEmpty())
        println("No matching people found.")
    else {
        for (p in fp)
            println(data[p])
    }
}

fun getQueries() {
    println("Enter the number of search queries:")
    val numQueries = readLine()!!.toInt()
    println()

    repeat(numQueries) {
        println("Enter data to search people:")
        val query = readLine()!!

        println("\nFound people:")
        val fp = findPeople(query)
        if (fp.isEmpty())
            println("No matching people found.")
        else {
            for (p in fp)
                println(p)
        }
    }
    println()
}

fun findPeopleIndexed(query: String): MutableList<Int> {
    return invertedIndex.getOrDefault(query.toLowerCase(), mutableListOf())
}

fun findPeople(query: String): ArrayList<String> {
    val result = arrayListOf<String>()
    for (line in data) {
        if (line.toLowerCase().contains(query.toLowerCase())) {
            result.add(line)
        }
    }
    return result
}

fun menu() {
    while (true) {
        println()
        println("""
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
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
    //getData()
    readData(args)
    menu()
}
