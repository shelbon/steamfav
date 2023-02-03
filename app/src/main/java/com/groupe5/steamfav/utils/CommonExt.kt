package com.groupe5.steamfav.utils

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI


fun TextView.applyBoldEndingAtDelimiter(delimiter:String=":") {
    val index = this.text.indexOf(delimiter)
    if (this.text.isNotEmpty() && index != -1) {
        val spannable = SpannableString(this.text)
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, index, 0)
        this.text = spannable
    }
}
fun TextView.applyUnderlineEndingAtDelimiter(delimiter: String=":"){
    val index = this.text.indexOf(delimiter)
    if (this.text.isNotEmpty() && index != -1) {
        val spannable = SpannableString(this.text)
        spannable.setSpan(UnderlineSpan(), 0, index, 0)
        this.text = spannable
    }
}
fun String.onlyLetters() = all { it.isLetter() }

fun AppCompatActivity.setupActionBarFromFragment(toolbar: Toolbar, navController: NavController, appBarConfiguration: AppBarConfiguration) {

    this.setSupportActionBar(toolbar)

    NavigationUI.setupWithNavController(toolbar, navController,appBarConfiguration)

}