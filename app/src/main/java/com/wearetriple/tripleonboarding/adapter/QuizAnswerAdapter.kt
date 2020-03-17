package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.QuizAnswer
import kotlinx.android.synthetic.main.item_quiz_answer.view.*

class QuizAnswerAdapter(
    private val quizAnswers: List<QuizAnswer>,
    private val clickListener: (QuizAnswer) -> Unit
) : RecyclerView.Adapter<QuizAnswerAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(quizAnswer: QuizAnswer) {
            itemView.btnQuizAnswer.text = quizAnswer.content
            itemView.btnQuizAnswer.setOnClickListener { clickListener(quizAnswer) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_answer, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return quizAnswers.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quizAnswers[position])
    }
}