package com.example.noteapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.database.DB
import com.example.noteapp.recyclerview.NoteAdapter
import java.text.SimpleDateFormat
import java.util.*

class HomePage : AppCompatActivity() {

    private lateinit var db: DB
    private lateinit var editTxtTitle: EditText
    private lateinit var editTxtDesc: EditText
    private lateinit var editTxtDate: EditText
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private var adapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        allFindViewById()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        recyclerView.adapter = adapter
        db = DB(this)


        editTxtDate.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(Calendar.YEAR, year)
                        selectedDate.set(Calendar.MONTH, monthOfYear)
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date = SimpleDateFormat(
                            "dd.MM.yyyy",
                            Locale.getDefault()
                        ).format(selectedDate.time)
                        editTxtDate.setText(date)
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            }
            true
        }

        btnAdd.setOnClickListener { addNote(); getNote() }
        adapter?.setOnClickItem {
            val intent = Intent(this, DetailPage::class.java)
            intent.putExtra("noteNid", it.nid.toString())
            intent.putExtra("noteTitle", it.title)
            intent.putExtra("noteDesc", it.detail)
            intent.putExtra("noteDate", it.date)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        getNote()
    }

    private fun addNote() {
        val title = editTxtTitle.text.toString()
        val desc = editTxtDesc.text.toString()
        val date = editTxtDate.text.toString()

        if (title.isNotEmpty() && desc.isNotEmpty() && date.isNotEmpty()) {
            val status = db.addNote(title, desc, date)
            if (status > -1) {
                Toast.makeText(this@HomePage, "Notes Added Succesfully.", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this@HomePage, "Adding Notes Failed....", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@HomePage, "Please Fill in All the Boxes!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNote() {
        val status = db.allNote()
        adapter?.addItems(status)
    }

    private fun clearEditText() {
        editTxtTitle.setText("")
        editTxtDesc.setText("")
        editTxtDate.setText("")
        editTxtTitle.requestFocus()
    }

    private fun allFindViewById() {
        editTxtTitle = findViewById(R.id.editTxtTitle)
        editTxtDesc = findViewById(R.id.editTxtDesc)
        editTxtDate = findViewById(R.id.editTxtDate)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recycler_view)
    }

}

