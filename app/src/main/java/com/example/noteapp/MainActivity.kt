package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val splashScreen = 5000

    lateinit var img_gif : ImageView
    lateinit var txtNoteApp : TextView
    lateinit var txtDesc : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_gif = findViewById(R.id.img_gif)
        txtNoteApp = findViewById(R.id.txtNoteApp)
        txtDesc = findViewById(R.id.txtDesc)

        showGIF()
        supportActionBar?.hide()

        val animation1 = AnimationUtils.loadAnimation(this,R.anim.animation1)
        val animation2 = AnimationUtils.loadAnimation(this,R.anim.animation2)

        img_gif.animation = animation1
        txtNoteApp.animation = animation2

        // Splash Screen

        GlobalScope.launch(Dispatchers.Main) {
            delay(splashScreen.toLong())
            val intent = Intent(this@MainActivity,HomePage::class.java)
            startActivity(intent)
            finish()
        }
        
    }
    fun showGIF(){
        val img_gif :ImageView = findViewById(R.id.img_gif)
        Glide.with(this).load(R.raw.book_gif).into(img_gif)
    }
}