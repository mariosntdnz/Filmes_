package com.example.filmes_

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        print("activity\n")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}