package org.android.go.sopt.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.R

fun View.showSnackbar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun View.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT)
        .show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.loadImage(image: Drawable?) {
    load(image) {
        placeholder(R.drawable.kitkat)
            .transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
            .error(R.drawable.ic_launcher_background)
    }
}
