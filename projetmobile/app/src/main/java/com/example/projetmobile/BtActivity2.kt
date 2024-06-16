package com.example.projetmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.Random

class BtActivity2 : AppCompatActivity() {
    private lateinit var gamelayout: LinearLayout
    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button
    private lateinit var timerTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var questionNumberTextView: Button
    private var currentQuestionNumber: Int = 1

    private val random = Random()
    private var score = 0
    private var currentQuestionIndex = 0
    private lateinit var currentQuestion: Question
    private var timer: CountDownTimer? = null

    private lateinit var scoreTextView: TextView
    private lateinit var title: TextView
    private val correctColor: Int by lazy { ContextCompat.getColor(this, R.color.correctColor) }
    private val incorrectColor: Int by lazy { ContextCompat.getColor(this, R.color.incorrectColor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bt1)

        gamelayout = findViewById(R.id.gamelayout)
        progressBar = findViewById(R.id.progressBar)
        timerTextView = findViewById(R.id.timerTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        questionNumberTextView = findViewById(R.id.questionNumberTextView)
        questionTextView = findViewById(R.id.questionTextView)
        option1Button = findViewById(R.id.option1Button)
        option2Button = findViewById(R.id.option2Button)
        option3Button = findViewById(R.id.option3Button)
        option4Button = findViewById(R.id.option4Button)

        option1Button.setOnClickListener { onOptionSelected(it) }
        option2Button.setOnClickListener { onOptionSelected(it) }
        option3Button.setOnClickListener { onOptionSelected(it) }
        option4Button.setOnClickListener { onOptionSelected(it) }

        option1Button.setBackgroundResource(R.drawable.rounded_button_background)
        option2Button.setBackgroundResource(R.drawable.rounded_button_background)
        option3Button.setBackgroundResource(R.drawable.rounded_button_background)
        option4Button.setBackgroundResource(R.drawable.rounded_button_background)

        score = 0
        currentQuestionIndex = 0
        showNextQuestion()
        updateScoreDisplay()
    }

    private fun showNextQuestion() {
        timer?.cancel() // Cancel the previous timer before showing the next question
        progressBar.progress = 0
        timerTextView.text = "10"

        currentQuestion = generateRandomQuestion()
        updateQuestionUI()
        startTimer()
        questionNumberTextView.text = "    Question $currentQuestionNumber/50    "
        currentQuestionNumber++
    }

    private fun updateQuestionUI() {
        questionTextView.text = currentQuestion.question
        val options = currentQuestion.options
        option1Button.text = options[0]
        option2Button.text = options[1]
        option3Button.text = options[2]
        option4Button.text = options[3]
    }

    private fun startTimer() {
        timer?.cancel() // Ensure the previous timer is cancelled
        progressBar.progress = 0
        val duration = 10000L // Total duration of the timer in milliseconds
        val interval = 100L // Update interval in milliseconds

        timer = object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((duration - millisUntilFinished) * 100 / duration).toInt()
                progressBar.progress = progress
                timerTextView.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                progressBar.progress = 100
                timerTextView.text = "Time's up!"
                endGame()
            }
        }.start()
    }

    private fun generateRandomQuestion(): Question {
        val num1 = random.nextInt(100)
        val num2 = random.nextInt(100)
        val correctAnswer = num1 + num2
        val options = generateOptions(correctAnswer)

        return Question("$num1 + $num2?", options, options.indexOf(correctAnswer.toString()))
    }

    private fun generateOptions(correctAnswer: Int): List<String> {
        val options = mutableListOf<String>()
        options.add(correctAnswer.toString())

        while (options.size < 4) {
            val wrongAnswer = correctAnswer + random.nextInt(20) - 10
            if (wrongAnswer != correctAnswer && !options.contains(wrongAnswer.toString())) {
                options.add(wrongAnswer.toString())
            }
        }

        return options.shuffled()
    }

    private fun endGame() {
        Toast.makeText(this, "Game Over! Your score: $score", Toast.LENGTH_SHORT).show()
        timer?.cancel()
        currentQuestionNumber = 1
        val intent = Intent(this, BtActivity1::class.java)
        startActivity(intent)
    }

    fun onOptionSelected(view: View) {
        val selectedOption = when (view.id) {
            R.id.option1Button -> 0
            R.id.option2Button -> 1
            R.id.option3Button -> 2
            R.id.option4Button -> 3
            else -> -1
        }

        if (selectedOption == currentQuestion.correctOption) {
            score += 10  // Increase the score by 10
            updateScoreDisplay()
            view.setBackgroundColor(correctColor)
        } else {
            view.setBackgroundColor(incorrectColor)
            Toast.makeText(this, "Wrong! Game Over.", Toast.LENGTH_SHORT).show()
            endGame()
            return
        }

        Handler(Looper.getMainLooper()).postDelayed({
            view.setBackgroundResource(R.drawable.rounded_button_background)
            currentQuestionIndex++
            showNextQuestion()
        }, 1000)
    }

    private fun updateScoreDisplay() {
        scoreTextView.text = "Score: $score"
    }
}
