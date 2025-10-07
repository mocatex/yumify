// simple game of life implementation in kotlin
// having a random generation of cells as starting point within a 20x20 grid
// and one iteration step only

import kotlin.random.Random

fun main() {

    val singleStep: Boolean = false

    val size: Int = 30

    // number of ms wait in autorun-mode
    val speed: Long = 20

    if (singleStep) {
        var gameBoard = MutableList(size) { MutableList(size) { Random.nextInt(0, 2) } }

        print("starting Board:\n")
        printBoardState(gameBoard)

        var nextBoardStep: MutableList<MutableList<Int>> = gameBoard.map { it.toMutableList() }.toMutableList()

        for (i in 0 until size) {
            for (j in 0 until size) {
                var numNeighbours: Int = countNeighbours(gameBoard, i, j, size)
                nextBoardStep[i][j] = when{
                    numNeighbours <= 1 || numNeighbours >= 4 -> 0
                    numNeighbours == 3 -> 1
                    else -> nextBoardStep[i][j]
                }
            }
        }

        print("\n\nGame of Life Step 0:")
        printBoardState(nextBoardStep)
    } else {

        Runtime.getRuntime().addShutdownHook(Thread {
            println("\nExiting gracefully...")
        })

        var roundCounter: Int = 1
        var gameBoard = MutableList(size) { MutableList(size) { Random.nextInt(0, 2) } }
        printBoardState(gameBoard)

        while (true) {

            Thread.sleep(speed)

            clearConsole()
            
            var nextBoardStep: MutableList<MutableList<Int>> = gameBoard.map { it.toMutableList() }.toMutableList()

            for (i in 0 until size) {
                for (j in 0 until size) {
                    var numNeighbours: Int = countNeighbours(gameBoard, i, j, size)
                    nextBoardStep[i][j] = when{
                        numNeighbours <= 1 || numNeighbours >= 4 -> 0
                        numNeighbours == 3 -> 1
                        else -> nextBoardStep[i][j]
                    }
                }
            }

            print("\n\nGame of Life Step " + roundCounter + "\n\n")
            printBoardState(nextBoardStep)

            roundCounter++

            gameBoard = nextBoardStep.map { it.toMutableList() }.toMutableList()

            
        }

    }
}

/** goes in a 3x3 square around the selected cell and counts all ones. 
 * then returns the count - 1 because it counted itself */
fun countNeighbours(board: MutableList<MutableList<Int>>, rowIndex: Int, columnIndex: Int, boardSize: Int): Int {
    var neighbourCount: Int = 0

    for (i in -1..1) {
        for (j in -1..1) {
            var cellValue: Int = getValueOfField(board, rowIndex + i, columnIndex + j, boardSize)

            neighbourCount += if (i != 0 || j != 0) cellValue else 0
        }
    }

    return neighbourCount
}

/** checks the value inside the cell based on a row and column index
 * If the index is outside of the Board it returns 0 else the value inside it */
fun getValueOfField(board: MutableList<MutableList<Int>>, rowIndex: Int, columnIndex: Int, boardSize: Int): Int {
    val value =
        when {
            rowIndex < 0 || columnIndex < 0 || rowIndex >= boardSize || columnIndex >= boardSize -> 0
            else -> board[rowIndex][columnIndex]
        }

    return value
}

/** prints the currents boardState row by row */
fun printBoardState(boardState: MutableList<MutableList<Int>>) {
    for (gameRow in boardState) {
        var cleanedRow: String = gameRow.joinToString("").replace('0', '.').replace('1', '@')
        println(cleanedRow)
    }
}

fun clearConsole() {
    print("\u001b[H\u001b[2J")
    System.out.flush()
}

