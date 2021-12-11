package com.kosyakoff.todolistapp.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity) {
    activity.currentFocus?.let {
        val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}