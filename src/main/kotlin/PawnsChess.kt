class PawnsChess(private val player1: String, private val player2: String) {
    private val pawns = Pair(MutableList(8) { i -> "${'a' + i}2" }, MutableList(8) { i -> "${'a' + i}7" })
    private val chessboard = Array(8) { i -> Array(8) { if (i == 1) 'w' else if (i == 6) 'b' else ' ' } }
    private val regexMoves = Regex("[a-h][1-8]")
    private var isWhitePawn = true
    private val enPassant = EnPassant()
    var gameOverMessage = ""
        private set
    var gameOver = false
        private set

    fun printChessboard() {
        val line = "  +---+---+---+---+---+---+---+---+"
        val end = "    a   b   c   d   e   f   g   h\n"

        println(line)
        for (i in chessboard.indices.reversed()) {
            print("${i + 1} |")
            for (j in chessboard.indices) print(" ${chessboard[i][j].uppercaseChar()} |")
            println("\n" + line)
        }
        println(end)
    }

    fun movePawn(move: String): Boolean {
        var invalid = "Invalid Input"
        val firstHalf = move.substring(0, 2)
        val secondHalf = move.substring(2)

        if (isValidRange(firstHalf) && isValidRange(secondHalf)) {
            val pawn = getChar(firstHalf)
            if (pawn.isLetter() && pawn.uppercaseChar() == getPawn(isWhitePawn)) {
                val capture = verticalMoves(firstHalf, secondHalf)
                if (capture) {
                    val captured = if (enPassant.isCaptured) enPassant.getCapture() else secondHalf
                    updatePawn(!isWhitePawn, captured)
                    if (enPassant.isCaptured) updateChessboard(captured, ' ')
                }
                if (capture || forwardMoves(firstHalf, secondHalf, pawn.isLowerCase())) {
                    updatePawn(isWhitePawn, firstHalf, secondHalf)
                    updateChessboard(firstHalf, ' ')
                    updateChessboard(secondHalf, pawn.uppercaseChar())
                    if ("18".contains(move.last()) || getPawnList(!isWhitePawn).isEmpty()) {
                        gameOverMessage = (if (isWhitePawn) "White" else "Black") + " Wins!"
                        gameOver = true
                    } else {
                        isWhitePawn = !isWhitePawn
                        gameOver = !canContinue()
                        if (gameOver) gameOverMessage = "Stalemate!"
                    }
                    return true
                }
            } else invalid = "No " + (if (isWhitePawn) "white" else "black") + " pawn at $firstHalf"
        }
        println(invalid)
        return false
    }

    fun currentPlayer() = if (isWhitePawn) player1 else player2

    private fun verticalMoves(position: String, move: String?): Boolean {
        val nextNum = nextMove(position)[1]
        val firstMove = (position[0] - 1) + "" + nextNum
        val secondMove = (position[0] + 1) + "" + nextNum
        val check = { place: String -> if (move == null) isValidRange(place) else place == move }
        val isValid = { place: String -> check(place) && getPawn(!isWhitePawn) == getChar(place).uppercaseChar() }
        val canMove = enPassant.canBeCaptured(firstMove, secondMove, move) || isValid(firstMove) || isValid(secondMove)

        if (canMove && move != null) enPassant.resetEnPassant()
        return canMove
    }

    private fun forwardMoves(position: String, move: String?, isTwoMoves: Boolean): Boolean {
        val firstMove = nextMove(position)
        val secondMove = if (isTwoMoves) nextMove(firstMove) else ""
        val isValid = { place: String -> isValidRange(place) && getChar(place) == ' ' }

        if (isValid(firstMove)) {
            if (move == null) return true
            if (firstMove == move) {
                enPassant.resetEnPassant()
                return true
            } else if (isValid(secondMove) && secondMove == move) {
                enPassant.updateEnPassant(Pair(firstMove, secondMove))
                return true
            }
        }
        return false
    }

    private fun canContinue(): Boolean {
        for (pawn in getPawnList(isWhitePawn)) {
            if (forwardMoves(pawn, null, false) || verticalMoves(pawn, null)) return true
        }
        return false
    }

    private fun updateChessboard(location: String, change: Char) {
        chessboard[numToInt(location[1])][letterToInt(location[0])] = change
    }

    private fun updatePawn(isWhite: Boolean, wasHere: String, moveHere: String = "") = with(getPawnList(isWhite)) {
        this.remove(wasHere)
        if (moveHere != "") this.add((moveHere))
    }

    private fun getPawnList(isWhite: Boolean) = if (isWhite) pawns.first else pawns.second

    private fun nextMove(position: String) = position[0] + "" + (position[1] + (if (isWhitePawn) 1 else -1))

    private fun isValidRange(position: String) = position.matches(regexMoves)

    private fun getPawn(isWhite: Boolean) = if (isWhite) 'W' else 'B'

    private fun getChar(position: String) = chessboard[numToInt(position[1])][letterToInt(position[0])]

    private fun numToInt(num: Char) = num.digitToInt() - 1

    private fun letterToInt(letter: Char) = letter.code - (if (letter.isLowerCase()) 97 else 65)

    private inner class EnPassant {
        private var enPassant1 = Pair("", "")
        private var enPassant2 = Pair("", "")
        private val getFirst = { isPassant1: Boolean -> (if (isPassant1) enPassant1 else enPassant2).first }
        var isCaptured = false
            private set

        fun resetEnPassant() = if (getFirst(isWhitePawn) != "") updateEnPassant(Pair("", "")) else Unit

        fun updateEnPassant(passant: Pair<String, String>) =
            if (isWhitePawn) enPassant1 = passant else enPassant2 = passant

        fun getCapture() = (if (isWhitePawn) enPassant2 else enPassant1).second

        fun canBeCaptured(firstMove: String, secondMove: String, move: String?): Boolean {
            val passant = getFirst(!isWhitePawn)
            val test = passant == firstMove || passant == secondMove
            return if (move != null) {
                isCaptured = test && (firstMove == move || secondMove == move)
                isCaptured
            } else test
        }
    }
}