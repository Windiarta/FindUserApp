package com.suitmediatest.ui.first_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.suitmediatest.NAME
import com.suitmediatest.PREF_KEY
import com.suitmediatest.R
import com.suitmediatest.ui.second_screen.SecondActivity
import com.suitmediatest.ui.transparentSystemUi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transparentSystemUi(window)
        supportActionBar?.hide()

        val pref = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        val intentToSecond = Intent(this, SecondActivity::class.java)
        findViewById<Button>(R.id.btn_next).setOnClickListener {
            val name = findViewById<TextView>(R.id.ed_name).text.toString()
            pref.edit().putString(NAME, name).apply()
            startActivity(intentToSecond)
        }

        findViewById<Button>(R.id.btn_check).setOnClickListener {
            val palindrome = findViewById<EditText>(R.id.ed_palindrome).text.toString()
            showDialog(if (isPalindrome(palindrome)) "isPalindrome" else "not palindrome")
        }
    }

    private fun isPalindrome(str: String): Boolean {
        val normalizedStr = str.replace("\\s".toRegex(), "").toLowerCase()
        val reversedStr = normalizedStr.reversed()
        return normalizedStr == reversedStr
    }

    private fun showDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .create()
        dialog.show()
    }
}