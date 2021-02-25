package search


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

fun main() {
    getData()
    getQueries()
}
