package com.example.projetmobile


import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import com.example.projetmobile.databinding.ActivityMg2Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MgActivity2 : AppCompatActivity() , OnClickListener
{
    private var score = 0
    private lateinit var binding : ActivityMg2Binding
    private var result : String = ""
    private var userAnswer : String = ""
    private var level = 3
    private val COUNTDOWN_DURATION: Long = 3000
    private val COUNTDOWN_INTERVAL: Long = 1000

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMg2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        startCountdown();
    }


    private fun startCountdown() {
        disableButtons()
        object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt() + 1
                binding.counter.setText(secondsLeft.toString())
            }

            override fun onFinish() {
                lifecycleScope.launch {
                    binding.counter.setText("Go!")
                    delay(1000)
                }
                binding.counter.setText(" ");
                //initViews
                binding.apply {
                    panel1.setOnClickListener(this@MgActivity2)
                    panel2.setOnClickListener(this@MgActivity2)
                    panel3.setOnClickListener(this@MgActivity2)
                    panel4.setOnClickListener(this@MgActivity2)
                    startGame()
                }
            }
        }.start()
    }

    private fun startGame() {
        result = ""
        userAnswer = ""
        disableButtons()
        lifecycleScope.launch {
            val round = (3 .. 5).random()
            repeat(level) {

                delay(400)
                val randomPanel = (1 .. 4).random()
                result += randomPanel

                val panel = when (randomPanel) {
                    1 -> binding.panel1
                    2 -> binding.panel2
                    3 -> binding.panel3
                    else -> binding.panel4
                }

                val drawableYellow =
                    ActivityCompat.getDrawable(this@MgActivity2 , R.drawable.btn_green)
                val drawableDefault =
                    ActivityCompat.getDrawable(this@MgActivity2 , R.drawable.btn_state)
                panel.background = drawableYellow
                delay(1000)
                panel.background = drawableDefault
            }
            runOnUiThread {
                enableButtons()
            }
        }
    }

    private fun loseAnimation() {
        binding.apply {
            score = 0
            level = 3
            UIscore.text = "0"
            disableButtons()
            val drawableLose = ActivityCompat.getDrawable(this@MgActivity2 , R.drawable.btn_red)
            val drawableDefault =
                ActivityCompat.getDrawable(this@MgActivity2 , R.drawable.btn_state)
            lifecycleScope.launch {
                binding.root.forEach { view ->
                    if (view is Button) {
                        view.background = drawableLose
                        delay(200)
                        view.background = drawableDefault
                    }
                }
                delay(2000)
                startGame()
            }
        }
    }

    private fun enableButtons() {
        binding.root.forEach { view ->
            if (view is Button) {
                view.isEnabled = true
            }
        }
    }

    private fun disableButtons() {
        binding.root.forEach { view ->
            if (view is Button) {
                view.isEnabled = false
            }
        }
    }

    private fun WinComment(){
        lifecycleScope.launch{
            val comments = arrayOf(
                "Well done!",
                "Incredible!",
                "Next level!",
                "Keep winning 💪💪",
                "You did it!",
                "Fantastic job!",
                "Congratulations!")
            binding.UIresult.text = comments.random()
            delay(2000)
            binding.UIresult.text = ""
        }
    }

    private fun LostComment(){
        lifecycleScope.launch{
            binding.UIresult.text = "You lost :("
            delay(2000)
            binding.UIresult.text = ""
        }
    }

    override fun onClick(view : View?) {
        view?.let {
            userAnswer += when (it.id) {
                R.id.panel1 -> "1"
                R.id.panel2 -> "2"
                R.id.panel3 -> "3"
                R.id.panel4 -> "4"
                else -> ""
            }

            if (userAnswer == result) {
                // Toast.makeText(this@MemoryGameMain , "W I N :)" , Toast.LENGTH_SHORT).show()
                score ++
                level ++
                binding.UIscore.text = score.toString()
                WinComment()
                startGame()

            }
            else if (userAnswer.length >= result.length)
            {
                loseAnimation()
                LostComment()
            }
        }
    }

}