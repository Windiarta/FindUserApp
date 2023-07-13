package com.suitmediatest.ui.third_screen

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.suitmediatest.INTENT_KEY
import com.suitmediatest.R
import com.suitmediatest.ui.clearSystemUi
import com.suitmediatest.ui.second_screen.SecondActivity

class ThirdActivity : AppCompatActivity() {
    private lateinit var viewModel: ThirdViewModel
    private lateinit var recycler: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val fullName = intent.getStringExtra(INTENT_KEY)

        clearSystemUi(window)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.customView?.findViewById<TextView>(R.id.tv_title)?.text =
            getString(R.string.third_screen)
        supportActionBar?.customView?.findViewById<ImageButton>(R.id.backButton)
            ?.setOnClickListener {
                val intentBack = Intent(this, SecondActivity::class.java)
                intentBack.putExtra(INTENT_KEY, fullName)
                startActivity(intentBack)
            }

        recycler = findViewById(R.id.rv_list)
        recycler.layoutManager = LinearLayoutManager(this)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        viewModel = ViewModelProvider(this, ThirdViewModelFactory())[ThirdViewModel::class.java]
        showRecyclerView()

        swipeRefreshLayout.setOnRefreshListener {
            showRecyclerView()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun showRecyclerView() {
        val adapter = ThirdAdapter()
        recycler.adapter = adapter
        viewModel.users.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}