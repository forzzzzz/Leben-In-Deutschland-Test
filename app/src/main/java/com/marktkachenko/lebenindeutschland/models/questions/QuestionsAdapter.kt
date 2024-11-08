package com.marktkachenko.lebenindeutschland.models.questions

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.databinding.ItemQuestionBinding

interface QuestionActionListener {
    fun onClickAnswer1(question: Question)

    fun onClickAnswer2(question: Question)

    fun onClickAnswer3(question: Question)

    fun onClickAnswer4(question: Question)

    fun onTranslateClick(question: Question)

    fun onFavoriteClick(question: Question)
}

class QuestionsDiffCallback(
    private val oldList: List<Question>,
    private val newList: List<Question>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuestion = oldList[oldItemPosition]
        val newQuestion = newList[newItemPosition]
        return oldQuestion.id == newQuestion.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuestion = oldList[oldItemPosition]
        val newQuestion = newList[newItemPosition]
        return oldQuestion == newQuestion
    }

}

class QuestionsAdapter(
    private val questionActionListener: QuestionActionListener
) : RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>(), View.OnClickListener {

    var questions: List<Question> = emptyList()
        set(newValue) {
            val diffCallback = QuestionsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }


    override fun onClick(v: View) {
        val questions = v.tag as Question

        when (v.id) {
            R.id.answer_1 -> questionActionListener.onClickAnswer1(questions)
            R.id.answer_2 -> questionActionListener.onClickAnswer2(questions)
            R.id.answer_3 -> questionActionListener.onClickAnswer3(questions)
            R.id.answer_4 -> questionActionListener.onClickAnswer4(questions)
            R.id.translate_button -> questionActionListener.onTranslateClick(questions)
            R.id.favorite_button -> questionActionListener.onFavoriteClick(questions)
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionBinding.inflate(inflater, parent, false)

        binding.answer1.setOnClickListener(this)
        binding.answer2.setOnClickListener(this)
        binding.answer3.setOnClickListener(this)
        binding.answer4.setOnClickListener(this)
        binding.translateButton.setOnClickListener(this)
        binding.favoriteButton.setOnClickListener(this)

        return QuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val question = questions[position]
        with(holder.binding) {

            answer1.tag = question
            answer2.tag = question
            answer3.tag = question
            answer4.tag = question
            translateButton.tag = question
            favoriteButton.tag = question


            questionText.text = question.question
            answer1.text = question.answer1
            answer2.text = question.answer2
            answer3.text = question.answer3
            answer4.text = question.answer4

            if (question.image != 0){
                image.setImageResource(question.image)
                image.visibility = View.VISIBLE
            } else {
                image.visibility = View.GONE
            }

            topicText.text = question.topic
            questionsNumberText.text = question.questionNumber

            if (question.isFavorite == 1){
                favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            if (question.id == questions.first().id){
                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = dpToPx(0)
                    bottomMargin = dpToPx(0)
                }
            }else if (question.id != questions.last().id) {
                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = dpToPx(24)
                    bottomMargin = dpToPx(0)
                }
            } else {
                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = dpToPx(24)
                    bottomMargin = dpToPx(24)
                }
            }

            if (question.translatedQuestion == null) {
                translatedQuestionText.visibility = View.GONE
            } else {
                translatedQuestionText.visibility = View.VISIBLE
                translatedQuestionText.text = question.translatedQuestion
            }
            if (question.translatedAnswer1 == null) {
                translatedAnswer1.visibility = View.GONE
            } else {
                translatedAnswer1.visibility = View.VISIBLE
                translatedAnswer1.text = question.translatedAnswer1
            }
            if (question.translatedAnswer2 == null) {
                translatedAnswer2.visibility = View.GONE
            } else {
                translatedAnswer2.visibility = View.VISIBLE
                translatedAnswer2.text = question.translatedAnswer2
            }
            if (question.translatedAnswer3 == null) {
                translatedAnswer3.visibility = View.GONE
            } else {
                translatedAnswer3.visibility = View.VISIBLE
                translatedAnswer3.text = question.translatedAnswer3
            }
            if (question.translatedAnswer4 == null) {
                translatedAnswer4.visibility = View.GONE
            } else {
                translatedAnswer4.visibility = View.VISIBLE
                translatedAnswer4.text = question.translatedAnswer4
            }

            if (question.isCheckedAnswer1){
                answer1.isChecked = true
            } else {
                answer1.isChecked = false
            }
            if (question.isCheckedAnswer2){
                answer2.isChecked = true
            } else {
                answer2.isChecked = false
            }
            if (question.isCheckedAnswer3){
                answer3.isChecked = true
            } else {
                answer3.isChecked = false
            }
            if (question.isCheckedAnswer4){
                answer4.isChecked = true
            } else {
                answer4.isChecked = false
            }

            answer1.setBackgroundColor(question.answer1Color)
            answer2.setBackgroundColor(question.answer2Color)
            answer3.setBackgroundColor(question.answer3Color)
            answer4.setBackgroundColor(question.answer4Color)

            if (question.isCorrectAnswer != null){
                answer1.isEnabled = false
                answer2.isEnabled = false
                answer3.isEnabled = false
                answer4.isEnabled = false
            } else {
                answer1.isEnabled = true
                answer2.isEnabled = true
                answer3.isEnabled = true
                answer4.isEnabled = true
            }
        }
    }

    private fun dpToPx(dp: Int): Int{
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    class QuestionsViewHolder(
        val binding: ItemQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root)
}