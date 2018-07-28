package com.gp.gptest.view.history

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.gp.gptest.R
import kotlinx.android.synthetic.main.activity_history.*



class HistoryActivity : AppCompatActivity() {
    lateinit var vm: HistoryVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        vm = ViewModelProviders.of(this).get(HistoryVM::class.java)

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = HtoriyAdapter()

        history_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(context,viewManager.orientation))
        }

        vm.lacationHistoryList.observe(this, Observer {
            viewAdapter.submitList(it)
        })
    }
}
