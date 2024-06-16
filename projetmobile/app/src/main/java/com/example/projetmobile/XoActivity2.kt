package com.example.projetmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class XoActivity2 : AppCompatActivity() {
    private lateinit var board: Array<IntArray>
    private var currentPlayer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xo2)

        initializeGame()
    }

    private fun initializeGame() {
        // Initialize the game board
        board = Array(3) { IntArray(3) }

        val playerTurnText = findViewById<TextView>(R.id.player_turn_text)

        for (i in 0 until board.size) {
            for (j in 0 until board[i].size) {
                board[i][j] = 0
                val buttonId = resources.getIdentifier("btn_${i * 3 + j}", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.setOnClickListener {
                    if (board[i][j] == 0) {
                        board[i][j] = currentPlayer
                        button.text = if (currentPlayer == 1) "X" else "O"
                        if (checkForWin(i, j)) {
                            Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()
                            resetBoard()
                        } else if (checkForDraw()) {
                            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
                            resetBoard()
                        } else {
                            currentPlayer = if (currentPlayer == 1) 2 else 1
                            playerTurnText.text = "Player ${currentPlayer}'s Turn"
                        }
                    }
                }
            }
        }

        // Set the reset button functionality
        val resetButton = findViewById<Button>(R.id.start_btn)
        resetButton.text = "Reset"
        resetButton.setOnClickListener {
            resetBoard()
            playerTurnText.text = "Player 1's Turn" // Reset the player turn text to Player 1
        }
    }

    private fun resetBoard() {
        for (i in 0 until board.size) {
            for (j in 0 until board[i].size) {
                board[i][j] = 0
                val buttonId = resources.getIdentifier("btn_${i * 3 + j}", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.text = ""
            }
        }
        currentPlayer = 1
    }

    private fun checkForWin(row: Int, col: Int): Boolean {
        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer)
            return true
        // Check column
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer)
            return true
        // Check diagonals
        if (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true
        if (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
            return true
        return false
    }

    private fun checkForDraw(): Boolean {
        for (i in 0 until board.size) {
            for (j in 0 until board[i].size) {
                if (board[i][j] == 0) {
                    return false
                }
            }
        }
        return true
    }
}