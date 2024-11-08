package com.marktkachenko.lebenindeutschland.models.statistics

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.databinding.ItemStatisticBinding

interface StatisticActionListener {
    fun onStartTest(statistic: Statistic)
}

class StatisticAdapter(
    private val statisticsActionListener: StatisticActionListener
) : RecyclerView.Adapter<StatisticAdapter.StatisticsViewHolder>(), View.OnClickListener {

    var statistics: List<Statistic> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val statistics = v.tag as Statistic

        when (v.id) {
            R.id.statistic_card_view -> statisticsActionListener.onStartTest(statistics)
        }
    }

    override fun getItemCount(): Int {
        return statistics.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticBinding.inflate(inflater, parent, false)

        binding.statisticCardView.setOnClickListener(this)

        return StatisticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val statistic = statistics[position]
        with(holder.binding) {
            statisticCardView.tag = statistic

            if (statistic.id == statistics.last().id) {
                statisticCardView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = dpToPx(8)
                }
            }else {
                statisticCardView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = dpToPx(0)
                }
            }

            statisticTitleText.text = statistic.title
            statisticSubtitleText.text = statistic.subtitle
            statisticIcon.setImageResource(statistic.icon)
            statisticDecorLine.setBackgroundColor(statistic.decorLine)
            statisticNumberText.text = statistic.numberOfQuestions
        }
    }

    private fun dpToPx(dp: Int): Int{
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    class StatisticsViewHolder(
        val binding: ItemStatisticBinding
    ) : RecyclerView.ViewHolder(binding.root)
}