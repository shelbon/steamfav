package com.groupe5.steamfav.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.groupe5.steamfav.R
import com.groupe5.steamfav.ui.fragments.LoginFragment

class LoginAndRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_register)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_view_tag , LoginFragment())
        fragmentTransaction.commit()

    }
}