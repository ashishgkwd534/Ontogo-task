package com.ashish.ontogotask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ashish.ontogotask.R
import com.ashish.ontogotask.api.Api
import com.ashish.ontogotask.pojo.LoginResponse
import com.ashish.ontogotask.pojo.RegistrationResponse
import com.ashish.ontogotask.utils.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_check_email.*
import kotlinx.android.synthetic.main.activity_check_email.root
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_email)
        btnNext.setOnClickListener {
            if (TextUtils.isEmpty(edtEmail.text.toString())) {
                Snackbar.make(root, "Invalid Email", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
                Snackbar.make(root, "Invalid Email", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(edtPassword.text.toString())) {
                Snackbar.make(root, "Invalid Password", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Utils.showProgress(this, "Signing in... Please wait...")
            val call =
                Api.getRetrofit().getLogin(edtEmail.text.toString(), edtPassword.text.toString())
            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Utils.dismissProgress()
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.success == 1) {
                                Utils.showDialog(
                                    this@CheckEmailActivity,
                                    getString(R.string.login_success),
                                    "warning"
                                )
                            } else {
                                Utils.showDialog(
                                    this@CheckEmailActivity,
                                    response.body()!!.message!!,
                                    "warning"
                                )
                            }
                        } else {
                            Utils.showDialog(
                                this@CheckEmailActivity,
                                getString(R.string.server_fail),
                                "warning"
                            )
                        }
                    } else {
                        Utils.showDialog(
                            this@CheckEmailActivity,
                            getString(R.string.server_fail),
                            "warning"
                        )
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Utils.dismissProgress()
                    /*Snackbar.make(
                        root,
                        getString(R.string.server_fail),
                        Snackbar.LENGTH_SHORT
                    ).show()*/
                    Utils.showDialog(
                        this@CheckEmailActivity,
                        getString(R.string.server_fail),
                        "warning"
                    )
                }
            })
        }
        ivBack.setOnClickListener {
            finish()
        }
        tvSignup.setOnClickListener {
            tvSignup.visibility = View.GONE
            tilRePassword.visibility = View.VISIBLE
            tilName.visibility = View.VISIBLE
            btnNext.setOnClickListener {
                createAccount()
            }
            tvQuestion.text = getString(R.string.sing_up)

        }
    }


    private fun createAccount() {
        if (TextUtils.isEmpty(edtEmail.text.toString())) {
            Snackbar.make(root, "Invalid Email", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
            Snackbar.make(root, "Invalid Email", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(edtPassword.text.toString())) {
            Snackbar.make(root, "Invalid Password", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (edtPassword.text.toString().length < 6) {
            Snackbar.make(
                root,
                "Password must contain more than 6 characters",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        if (TextUtils.isEmpty(edtConformPassword.text.toString())) {
            Snackbar.make(root, "Invalid Confirm Password", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (edtConformPassword.text.toString().length < 6) {
            Snackbar.make(
                root,
                "Confirm Password must contain more than 6 characters",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        if (edtConformPassword.text.toString() != edtPassword.text.toString()) {
            Snackbar.make(root, "Password do not match", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(edtName.text.toString())) {
            Snackbar.make(root, "Invalid Name", Snackbar.LENGTH_SHORT).show()
            return
        }

        Utils.showProgress(this, "Signing up.. please wait..")
        val call = Api.getRetrofit().getRegister(
            edtEmail.text.toString(),
            edtPassword.text.toString(),
            edtName.text.toString()
        )
        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: Response<RegistrationResponse>
            ) {
                Utils.dismissProgress()
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success == 1) {
                            Utils.showDialog(
                                this@CheckEmailActivity,
                                getString(R.string.registrastion_success),
                                "Success"
                            )
                        } else Utils.showDialog(
                            this@CheckEmailActivity,
                            it.message!!,
                            "Success"
                        )
                    }
                } else Utils.showDialog(
                    this@CheckEmailActivity,
                    getString(R.string.server_fail),
                    "Success"
                )
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                Utils.dismissProgress()
                //Snackbar.make(root, getString(R.string.server_fail), Snackbar.LENGTH_SHORT).show()
                Utils.showDialog(
                    this@CheckEmailActivity,
                    getString(R.string.server_fail),
                    "Success"
                )

            }
        })
    }

}