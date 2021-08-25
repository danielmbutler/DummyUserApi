package com.dbtechprojects.dummyuserapi.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.view.Gravity
import android.view.View
import com.dbtechprojects.dummyuserapi.R
import com.google.android.material.snackbar.Snackbar

object ViewUtils {
    private var progressDialog: Dialog? = null

    fun showProgress(activity: Activity?) {

        if (activity != null) {
            if (progressDialog == null && !activity.isFinishing) {
                progressDialog = Dialog(activity)
                progressDialog?.setContentView(R.layout.progress_loader)
                progressDialog?.let{
                    it.window?.setBackgroundDrawable(
                        ColorDrawable(
                            Color.TRANSPARENT
                        )
                    )
                }
                progressDialog?.window?.setGravity(Gravity.CENTER)
                progressDialog?.setCancelable(false)
                progressDialog?.show()
            }
        }
    }


    fun progressDismiss(activity: Activity?) {
        if (activity != null) if (progressDialog != null && progressDialog?.isShowing == true && !activity.isFinishing) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    fun showSnackBar(rootView: View?, msg: String?) {
        rootView?.let {
            val snackBar = Snackbar.make(rootView, Html.fromHtml(msg), Snackbar.LENGTH_LONG)
            snackBar.view.setBackgroundColor(Color.parseColor("#95C384"))
            snackBar.show()
        }
    }

    fun showErrorSnackBar(rootView: View?, msg: String?) {
        rootView?.let {
            val snackBar = Snackbar.make(rootView, Html.fromHtml(msg), Snackbar.LENGTH_LONG)
            snackBar.view.setBackgroundColor(Color.parseColor("#ff0000"))
            snackBar.show()
        }
    }

}