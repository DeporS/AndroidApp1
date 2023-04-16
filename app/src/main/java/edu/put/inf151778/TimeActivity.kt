package edu.put.inf151778

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        val hPlain1 = findViewById<TextView>(R.id.hText1)
        val hPlain2 = findViewById<TextView>(R.id.hText2)
        val mPlain1 = findViewById<TextView>(R.id.mText1)
        val mPlain2 = findViewById<TextView>(R.id.mText2)
        val sPlain1 = findViewById<TextView>(R.id.sText1)
        val sPlain2 = findViewById<TextView>(R.id.sText2)

        val pBut = findViewById<Button>(R.id.plusBut)
        val mBut = findViewById<Button>(R.id.minusBut)
        val aBut = findViewById<Button>(R.id.acBut)

        pBut.setOnClickListener{
            var h = 0
            var m = 0
            var s = 0
            h = hPlain1.text.toString().toInt() + hPlain2.text.toString().toInt()
            m = mPlain1.text.toString().toInt() + mPlain2.text.toString().toInt()
            s = sPlain1.text.toString().toInt() + sPlain2.text.toString().toInt()

            while(s >= 60){
                s -= 60
                m += 1
            }
            while(m >= 60){
                m -= 60
                h += 1
            }
            hPlain1.text = h.toString()
            hPlain2.text = ""
            mPlain1.text = m.toString()
            mPlain2.text = ""
            sPlain1.text = s.toString()
            sPlain2.text = ""
        }

        mBut.setOnClickListener{
            var h = 0
            var m = 0
            var s = 0
            h = hPlain1.text.toString().toInt() - hPlain2.text.toString().toInt()
            m = mPlain1.text.toString().toInt() - mPlain2.text.toString().toInt()
            s = sPlain1.text.toString().toInt() - sPlain2.text.toString().toInt()

            while(m <= -1){
                h -= 1
                m += 60
            }
            while(s <= -1){
                m -= 1
                s += 60
            }
            if(h < 0 || m < 0 || s < 0){
                hPlain1.text = "0"
                hPlain2.text = ""
                mPlain1.text = "0"
                mPlain2.text = ""
                sPlain1.text = "0"
                sPlain2.text = ""
            }
            else {
                hPlain1.text = h.toString()
                hPlain2.text = ""
                mPlain1.text = m.toString()
                mPlain2.text = ""
                sPlain1.text = s.toString()
                sPlain2.text = ""
            }
        }
        aBut.setOnClickListener{
            hPlain1.text = ""
            hPlain2.text = ""
            mPlain1.text = ""
            mPlain2.text = ""
            sPlain1.text = ""
            sPlain2.text = ""
        }
    }
}

fun addTime(first : Int, second : Int){
    var x = 0
}