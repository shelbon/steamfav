package com.groupe5.steamfav.utils

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView


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