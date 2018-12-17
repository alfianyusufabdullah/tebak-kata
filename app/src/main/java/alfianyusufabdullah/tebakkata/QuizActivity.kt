package alfianyusufabdullah.tebakkata

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Placeholder
import android.support.v4.content.ContextCompat
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class QuizActivity : AppCompatActivity() {

    private var answer = ""
    private var correctAnswer = ""
    private var counter = 1
    private var quizCounter = 0
    private var answerCounter = 0

    companion object {
        const val PREFIX_PLACEHOLDER_ID = 100
        const val PREFIX_EMPTY_VIEW_ID = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quizData = Data.getQuizData.toMutableList()
        quizData.shuffle()

        generateQuiz(quizData[quizCounter])

        btnReset.setOnClickListener {
            reset(correctAnswer)
        }

        btnCekJawaban.setOnClickListener {
            answerCounter++
            if (answer == correctAnswer.toUpperCase()) {

                quizCounter++
                if (quizCounter == 4) {
                    showAlertDialog(answerCounter) {
                        quizCounter = 0
                        answerCounter = 0
                        quizData.shuffle()
                        remove(correctAnswer)
                        generateQuiz(quizData[quizCounter])
                    }
                } else {
                    showToast("Yeay! Benar")
                    remove(correctAnswer)
                    generateQuiz(quizData[quizCounter])
                }

            } else {
                showToast("Salah Bosque!")
                reset(correctAnswer)
            }
        }
    }

    private fun remove(word: String) {
        TransitionManager.beginDelayedTransition(rootView)
        for (id in 1..word.length) {
            rootView.removeView(findViewById(id))
            rootView.removeView(findViewById(PREFIX_PLACEHOLDER_ID + id))
            rootView.removeView(findViewById(PREFIX_EMPTY_VIEW_ID + id))
        }

        counter = 1
        answer = ""
    }

    private fun reset(word: String) {
        TransitionManager.beginDelayedTransition(rootView)
        for (index in 1..word.length) {
            val holder = rootView.findViewById<Placeholder?>(PREFIX_PLACEHOLDER_ID + index)
            holder?.setContentId(PREFIX_EMPTY_VIEW_ID + index)

            val letterText = rootView.findViewById<TextView>(index)
            letterText.setOnClickListener(letterClickListener(word, index))
        }

        btnCekJawaban.visibility = View.GONE
        btnReset.visibility = View.GONE

        counter = 1
        answer = ""
    }

    private fun generateQuiz(quiz: Entity) {

        tvQuestion.text = quiz.question

        btnCekJawaban.visibility = View.GONE
        btnReset.visibility = View.GONE

        correctAnswer = quiz.correctAnswer

        val chars = correctAnswer.toCharArray().toMutableList()
        chars.shuffle()

        var generateText = ""
        chars.forEach {
            generateText += it.toString()
        }

        val constraintSet = ConstraintSet()
        for (index in 1..generateText.length) {
            val lparam = ConstraintLayout.LayoutParams(100, 100)
            val placeHolder = Placeholder(this).apply {
                id = PREFIX_PLACEHOLDER_ID + index
                layoutParams = lparam
            }

            val textLetter = TextView(this).apply {
                id = index
                height = 100
                width = 100
                background = ContextCompat.getDrawable(context, R.drawable.bg_word)
                setTextColor(Color.WHITE)
                gravity = Gravity.CENTER
                text = generateText[index - 1].toString().toUpperCase()
                textSize = 24.toFloat()
                setOnClickListener(letterClickListener(correctAnswer, index))
            }

            val emptyView = TextView(this).apply {
                id = PREFIX_EMPTY_VIEW_ID + index
                height = 100
                width = 100
            }

            rootView.apply {
                addView(placeHolder)
                addView(textLetter)
                addView(emptyView)
            }

            constraintSet.clone(rootView)
            constraintSet.apply {
                setMargin(textLetter.id, ConstraintSet.END, 10)
                setMargin(textLetter.id, ConstraintSet.START, 10)
                setMargin(textLetter.id, ConstraintSet.TOP, 75)
                setMargin(emptyView.id, ConstraintSet.END, 10)
                setMargin(emptyView.id, ConstraintSet.START, 10)
                setMargin(emptyView.id, ConstraintSet.TOP, 50)
                setMargin(placeHolder.id, ConstraintSet.END, 10)
                setMargin(placeHolder.id, ConstraintSet.START, 10)

                connect(textLetter.id, ConstraintSet.TOP, frameAnswerForm.id, ConstraintSet.BOTTOM)
                connect(emptyView.id, ConstraintSet.TOP, textLetter.id, ConstraintSet.BOTTOM)
                connect(placeHolder.id, ConstraintSet.TOP, frameAnswerForm.id, ConstraintSet.TOP)
                connect(placeHolder.id, ConstraintSet.BOTTOM, frameAnswerForm.id, ConstraintSet.BOTTOM)

                when (index) {
                    1 -> {
                        addToHorizontalChain(textLetter.id, ConstraintSet.PARENT_ID, textLetter.id + 1)
                        addToHorizontalChain(placeHolder.id, ConstraintSet.PARENT_ID, placeHolder.id + 1)
                        addToHorizontalChain(emptyView.id, ConstraintSet.PARENT_ID, emptyView.id + 1)
                    }
                    correctAnswer.length -> {
                        addToHorizontalChain(textLetter.id, textLetter.id - 1, ConstraintSet.PARENT_ID)
                        addToHorizontalChain(placeHolder.id, placeHolder.id - 1, ConstraintSet.PARENT_ID)
                        addToHorizontalChain(emptyView.id, emptyView.id - 1, ConstraintSet.PARENT_ID)
                    }
                    else -> {
                        addToHorizontalChain(textLetter.id, textLetter.id - 1, textLetter.id + 1)
                        addToHorizontalChain(placeHolder.id, placeHolder.id - 1, placeHolder.id + 1)
                        addToHorizontalChain(emptyView.id, emptyView.id - 1, emptyView.id + 1)
                    }
                }

                if (index == 1) {
                    setHorizontalChainStyle(textLetter.id, ConstraintSet.CHAIN_PACKED)
                    setHorizontalChainStyle(placeHolder.id, ConstraintSet.CHAIN_PACKED)
                    setHorizontalChainStyle(emptyView.id, ConstraintSet.CHAIN_PACKED)
                }
            }

            constraintSet.applyTo(rootView)
        }

        rootView.requestLayout()
    }

    private fun letterClickListener(word: String, index: Int) = View.OnClickListener {
        TransitionManager.beginDelayedTransition(rootView as ViewGroup)
        val holder = rootView.findViewById<Placeholder?>(PREFIX_PLACEHOLDER_ID + counter)
        holder?.setContentId(index)

        counter++
        when {
            counter >= word.length + 1 -> btnCekJawaban.visibility = View.VISIBLE
            counter > 1 -> btnReset.visibility = View.VISIBLE
        }
        answer += (it as TextView).text.toString()

        it.setOnClickListener(null)
    }
}

