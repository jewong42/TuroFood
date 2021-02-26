package com.jewong.turofood.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jewong.turofood.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, ListFragment())
            commit()
        }
    }

}