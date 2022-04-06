package io.github.tuguzt.guessthenumber

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun showToast(context: Context, message: CharSequence) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showSnackbar(view: View, message: CharSequence) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}
