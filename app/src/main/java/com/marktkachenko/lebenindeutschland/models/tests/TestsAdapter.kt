package com.marktkachenko.lebenindeutschland.models.tests

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.databinding.ItemTestBinding

interface TestActionListener {
    fun onStartTest(test: Test)
}

class TestsDiffCallback(
    private val oldList: List<Test>,
    private val newList: List<Test>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTest = oldList[oldItemPosition]
        val newTest = newList[newItemPosition]
        return oldTest.id == newTest.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTest = oldList[oldItemPosition]
        val newTest = newList[newItemPosition]
        return oldTest == newTest
    }

}

class TestsAdapter(
    private val testActionListener: TestActionListener
) : RecyclerView.Adapter<TestsAdapter.TestsViewHolder>(), View.OnClickListener {

    var tests: List<Test> = emptyList()
        set(newValue) {
            val diffCallback = TestsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onClick(v: View) {
        val test = v.tag as Test

        when (v.id) {
            R.id.test_card_view -> testActionListener.onStartTest(test)
        }
    }

    override fun getItemCount(): Int {
        return tests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTestBinding.inflate(inflater, parent, false)

        binding.testCardView.setOnClickListener(this)

        return TestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        val test = tests[position]
        with(holder.binding) {
            testCardView.tag = test

            if (test.id == tests.last().id) {
                testCardView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = dpToPx(8)
                }
            }else {
                testCardView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = dpToPx(0)
                }
            }

            if (test.id == 0L) {
                testsHeaderText.visibility = View.GONE
            }else if (test.section == tests[position - 1].section) {
                testsHeaderText.visibility = View.GONE
            } else {
                testsHeaderText.visibility = View.VISIBLE
                testsHeaderText.text = test.section
                testsHeaderText.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = dpToPx(24)
                }
            }

            testTitleText.text = test.title
            testSubtitleText.text = test.subTitle
            testIcon.setImageResource(test.icon)
            testNumberText.text = test.numberOfQuestions
        }
    }

    private fun dpToPx(dp: Int): Int{
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    class TestsViewHolder(
        val binding: ItemTestBinding
    ) : RecyclerView.ViewHolder(binding.root)
}