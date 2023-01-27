package cinema

import java.util.Collections

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    println()

    val grid = MutableList(rows) { MutableList(seatsPerRow) { "S" } }

    do{
        showMenu()
        var option = readln().toInt()
        println()

        when (option){
            1 -> printSeats(grid)
            2 -> {
                var ticketRow: Int
                var ticketSeatNumber: Int
                do {
                    println("Enter a row number:")
                    ticketRow = readln().toInt()

                    println("Enter a seat number in that row:")
                    ticketSeatNumber = readln().toInt()
                    println()

                    var repeat = true
                    if (ticketRow !in 1 .. rows || ticketSeatNumber !in 1 .. seatsPerRow) {
                        println("Wrong input!")
                        println()
                    }
                    else if (grid[ticketRow-1][ticketSeatNumber-1] == "B") {
                        println("That ticket has already been purchased!")
                        println()
                    } else {
                        repeat = false
                    }

                }while (repeat)

                calculateTicketPrice(rows, seatsPerRow, ticketRow)

                //update grid
                grid[ticketRow-1][ticketSeatNumber-1] = "B"
            }
            3 -> {
                showStatistics(grid)
            }
        }

    }while(option != 0)

}

fun showStatistics(grid: MutableList<MutableList<String>>) {
    var purchasedTickets = 0
    for (i in 0 until grid.size){
        purchasedTickets += Collections.frequency(grid[i], "B")
    }

    val totalSeats = (grid.size * grid[0].size).toDouble()
    val percentage= (purchasedTickets / totalSeats) * 100
    val formatPercentage = "%.2f".format(percentage)

    val totalIncome = calculateTotalIncome(grid)
    val currentIncome = calculateCurrentIncome(grid)

    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current Income: $$currentIncome")
    println("Total income: $$totalIncome")
    println()

}

fun calculateCurrentIncome(grid: MutableList<MutableList<String>>): Int {
    val totalSeats = grid.size * grid[0].size
    val rows = grid.size
    var currentIncome = 0

    for (i in 0 until grid.size){
        for( j in 0 until grid[0].size){
            if(grid[i][j] == "B") {
                currentIncome += if (totalSeats > 60 && i+1 > rows / 2) {
                    8
                } else {
                    10
                }
            }
        }
    }

    return currentIncome
}

fun showMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun calculateTicketPrice(rows: Int, seatsPerRow: Int, ticketRow: Int) {
    val totalSeats = rows * seatsPerRow

    var ticketPrice = 10
    if(totalSeats > 60 && ticketRow > rows/2 ){
        ticketPrice = 8
    }

    println("Ticket price: $$ticketPrice")
    println()
}

fun printSeats(grid: MutableList<MutableList<String>>){

    println("Cinema:")
    print(" ")
    for (x in 1..grid[0].size){
        print(" $x")
    }
    println()

    for(i in 0 until  grid.size){
        for(j in 0 until grid[0].size){

            if (j == 0){
                print(i+1)
            }
            print(" ${grid[i][j]}")
        }
        println()
    }
    println()
}

fun calculateTotalIncome(grid: MutableList<MutableList<String>>): Int{
    val rows = grid.size
    val seatsPerRow = grid[0].size
    val totalSeats = rows * seatsPerRow

    val income: Int
    if(totalSeats <= 60){
        income = totalSeats * 10
    }
    else{
        val frontRows = rows / 2
        var backRows = rows / 2
        if(rows % 2 != 0) backRows += 1

        income = (frontRows * 10 + backRows * 8) * seatsPerRow
    }
    return income
}