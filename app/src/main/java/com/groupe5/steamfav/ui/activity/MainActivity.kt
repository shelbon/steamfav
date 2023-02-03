package com.groupe5.steamfav.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.groupe5.steamfav.R
import com.groupe5.steamfav.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // Firebase instance variables
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var appBarConfiguration: AppBarConfiguration
    val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        if (BuildConfig.DEBUG) {
//            Firebase.firestore.useEmulator("10.0.2.2", 8080)
//            Firebase.auth.useEmulator("10.0.2.2", 9099)
//        }
        db = Firebase.firestore
        auth = Firebase.auth
    }
 
}

