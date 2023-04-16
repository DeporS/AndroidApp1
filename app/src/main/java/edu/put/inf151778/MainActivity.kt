package edu.put.inf151778

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dButton = findViewById<Button>(R.id.dateButton)
        dButton.setOnClickListener{
            val intent = Intent(this, DateActivity::class.java)
            startActivity(intent)
        }

        val tButton = findViewById<Button>(R.id.timeButton)
        tButton.setOnClickListener{
            val intent = Intent(this, TimeActivity::class.java)
            startActivity(intent)
        }
    }
}