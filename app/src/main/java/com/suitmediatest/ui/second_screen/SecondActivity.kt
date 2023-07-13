package com.suitmediatest.ui.second_screen

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suitmediatest.INTENT_KEY
import com.suitmediatest.NAME
import com.suitmediatest.PREF_KEY
import com.suitmediatest.R
import com.suitmediatest.ui.clearSystemUi
import com.suitmediatest.ui.first_screen.MainActivity
import com.suitmediatest.ui.third_screen.ThirdActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        clearSystemUi(window)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.customView?.findViewById<TextView>(R.id.tv_title)?.text =
            getString(R.string.second_screen)
        supportActionBar?.customView?.findViewById<ImageButton>(R.id.backButton)
            ?.setOnClickListener {
                val intentBack = Intent(this, MainActivity::class.java)
                startActivity(intentBack)
            }

        val pref = getSharedPreferences(PREF_KEY, MODE_PRIVATE)
        val name = pref.getString(NAME, getString(R.string.username))
        if (name != "") {
            findViewById<TextView>(R.id.tv_name).text = name
        }

        val fullName = intent.getStringExtra(INTENT_KEY) ?: getString(R.string.choose_a_user)
        if (fullName != "") {
            findViewById<TextView>(R.id.tv_selected_name).text = fullName
        }

        findViewById<Button>(R.id.btn_choose).setOnClickListener {
            val intentToThird = Intent(this, ThirdActivity::class.java)
            intentToThird.putExtra(INTENT_KEY, fullName)
            startActivity(intentToThird)
        }
    }
}