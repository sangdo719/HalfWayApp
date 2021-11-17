package com.olayg.halfwayapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import com.olayg.halfwayapp.R

val Context.dataStore by preferencesDataStore(name = "characters")

class MainActivity : AppCompatActivity(R.layout.activity_main)
