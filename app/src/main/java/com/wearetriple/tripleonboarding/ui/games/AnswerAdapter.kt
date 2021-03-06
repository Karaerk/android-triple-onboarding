package com.wearetriple.tripleonboarding.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Answer
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_answer.view.*

/**
 * Used to prepare recyclerview's items.
 */
class AnswerAdapter(
    override val clickListener: (Answer) -> Unit
) : AbstractAdapter<Answer>(clickListener) {

    override var items: List<Answer> = listOf()

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Answer>.ViewHolder(itemView) {

        override fun bind(item: Answer) {
            itemView.btnAnswer.text = item.content
            itemView.btnAnswer.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<Answer>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)
        )
    }
}