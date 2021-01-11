package com.ashish.ontogotask.utils

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.webkit.ConsoleMessage

class Utils {
    companion object {
        const val BASE_URL = "https://quarterpillars.com/mobile_app/test/"
        var dialog: AlertDialog.Builder? = null
        var progressDialog: ProgressDialog? = null
        fun showDialog(context: Context, message: String, title: String) {

            dialog = AlertDialog.Builder(context)

            dialog?.let {
                it.setTitle(title)
                it.setMessage(message)
                it.setPositiveButton("ok") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                it.show()
            }
        }

        fun showProgress(context: Context, message: String) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage(message)
            progressDialog!!.show()
        }

        fun dismissProgress() {
            progressDialog?.let {
                if (it.isShowing)
                    it.dismiss()
            }
        }
    }
}