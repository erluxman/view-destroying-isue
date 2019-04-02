package com.example.testapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.test_fragment_first_screen.*

class FirstScreen : Fragment() {
    private var adapter: FirstScreenAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.test_fragment_first_screen, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (adapter == null) adapter = FirstScreenAdapter(::navigateToSecondScreen)
        dummyList.layoutManager = LinearLayoutManager(this.context)
        dummyList.adapter = adapter
    }

    private fun navigateToSecondScreen() {
        NavHostFragment.findNavController(this as Fragment)
            .navigate(R.id.action_test_fragment_1_to_test_fragment_2)
    }
}

class FirstScreenAdapter(private val navigate: () -> Any) : RecyclerView.Adapter<FirstListVH>() {
    private val items: List<ListData> = getDummyData().sortedBy { it.rollNo }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstListVH {
        return FirstListVH(LayoutInflater.from(parent.context).inflate(R.layout.test_item_first_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FirstListVH, position: Int) {
        holder.bindData(items[position], navigate)
    }
}

class FirstListVH(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTextView = view.findViewById<TextView>(R.id.name)
    private val rollNo = view.findViewById<TextView>(R.id.rollNo)

    @SuppressLint("SetTextI18n")
    fun bindData(item: ListData, navigate: () -> Any) {
        nameTextView.text = item.name
        rollNo.text = "Roll no  ${item.rollNo}"
        itemView.setOnClickListener { navigate() }
    }
}

data class ListData(
    val name: String,
    val rollNo: Int
)

fun getDummyData(): List<ListData> {

    val users = mutableListOf<ListData>()
    for (i in 0..30) {
        users.add(ListData("Student Name $i", i))
    }
    return users
}
