package org.android.go.sopt.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import org.android.go.sopt.R

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_loading, null)
        setContentView(view)
    }
}
