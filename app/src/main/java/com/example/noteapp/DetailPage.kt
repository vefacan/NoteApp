package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.noteapp.database.DB

class DetailPage : AppCompatActivity() {

    private lateinit var db: DB
    private lateinit var detailTxtTitle: TextView
    private lateinit var detailTxtDesc: TextView
    private lateinit var detailBtnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_page)

        detailTxtTitle = findViewById(R.id.detailTxtTitle)
        detailTxtDesc = findViewById(R.id.detailTxtDesc)
        detailBtnDelete = findViewById(R.id.detailBtnDelete)

        db = DB(this)

        val nidData = intent.getStringExtra("noteNid")
        val titleData = intent.getStringExtra("noteTitle")
        val descData = intent.getStringExtra("noteDesc")


        detailTxtTitle.setText(titleData)
        detailTxtDesc.setText(descData)

        detailBtnDelete.setOnClickListener {
            db.deleteNote(nidData!!.toInt())
            val intentToHomePage = Intent(this,HomePage::class.java)
            startActivity(intentToHomePage)
        }

    }
}