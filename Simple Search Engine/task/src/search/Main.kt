package search

import kotlin.system.exitProcess


val data = arrayListOf<String>()

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

fun getQuery() {
    println("\nEnter a name or email to search all suitable people.")
    val query = readLine()!!

    // println("\nFound people:")
    val fp = findPeople(query)
    if (fp.isEmpty())
        println("No matching people found.")
    else {
        for (p in fp)
            println(p)
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
        val option = readLine()!!.toInt()
        when (option) {
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

fun main() {
    getData()
    menu()
}
