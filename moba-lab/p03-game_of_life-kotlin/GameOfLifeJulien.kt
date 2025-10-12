fun main() {
    println("Game of Life")
    val rows = 5
    val cols = 5
    var oldBoard = Array(rows) { BooleanArray(cols) { Math.random() < 0.5 } }
    var newBoard: Array<BooleanArray>

    oldBoard = predefinedOldBoard() // For consistent testing

    println("Old Board:")
    printBoard(oldBoard)

    newBoard = runEvolution(oldBoard)
    println("Final Board:")
    printBoard(newBoard)

}

fun runEvolution(oldBoard: Array<BooleanArray>): Array<BooleanArray> {
    val amountOfSteps = 30
    var currentBoard = oldBoard
    for (step in 1..amountOfSteps) {
        currentBoard = evolutionStep(currentBoard)
        println("After step $step:")
        printBoard(currentBoard)
    }
    return currentBoard
}


fun predefinedOldBoard(): Array<BooleanArray> {
    return arrayOf(
        booleanArrayOf(false, false, false, false, false),
        booleanArrayOf(false, true, true, true, false),
        booleanArrayOf(false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false)
    )
}

fun printBoard(board: Array<BooleanArray>) {
    for (row in board) {
        for (cell in row) {
            print(if (cell) "█" else ".")
        }
        println()
    }
}

fun evolutionStep(oldBoard: Array<BooleanArray>): Array<BooleanArray> {
    val rows = oldBoard.size
    val cols = oldBoard[0].size
    val newBoard = Array(rows) { BooleanArray(cols) }

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val aliveNeighbors = countAliveNeighbors(oldBoard, r, c)
            newBoard[r][c] = checkifAliveInNextGen(aliveNeighbors, oldBoard[r][c])
        }
    }
    return newBoard
}

fun countAliveNeighbors(board: Array<BooleanArray>, row: Int, col: Int): Int {
    val directions = arrayOf(
        intArrayOf(-1, -1), intArrayOf(-1, 0), intArrayOf(-1, 1),
        intArrayOf(0, -1), /* current cell */ intArrayOf(0, 1),
        intArrayOf(1, -1), intArrayOf(1, 0), intArrayOf(1, 1)
    )
    var count = 0
    for (dir in directions) {
        val newRow = row + dir[0]
        val newCol = col + dir[1]
        if (newRow in board.indices && newCol in board[0].indices && board[newRow][newCol]) {
            count++
        }
    }
    return count

}

fun checkifAliveInNextGen(amountOfNeighbors: Int, isAlive: Boolean): Boolean {
    return if (isAlive) {
        amountOfNeighbors == 2 || amountOfNeighbors == 3
    } else {
        amountOfNeighbors == 3
    }
}