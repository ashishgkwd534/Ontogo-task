package com.ashish.ontogotask.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashish.ontogotask.R
import com.ashish.ontogotask.api.Api
import com.ashish.ontogotask.pojo.LoginResponse
import com.ashish.ontogotask.pojo.RegistrationResponse
import com.ashish.ontogotask.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_check_email.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.root
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    val RC_SIGN_IN = 1001
    lateinit var gso:GoogleSignInOptions
    lateinit var mGoogleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnGoogleSignIn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        btnFbSignIn.setOnClickListener {
            Snackbar.make(root,getString(R.string.no_support),Snackbar.LENGTH_SHORT).show()
        }
        btnEmailSignIn.setOnClickListener {
            startActivity(Intent(this,CheckEmailActivity::class.java))
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Utils.showProgress(this,"Signing in.. please wait..")
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email=account!!.email
            val call=Api.getRetrofit().getRegister(email!!)
            call.enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    Utils.dismissProgress()
                    if (response.isSuccessful){
                        response.body()?.let {
                            if (it.success==1){
                                Utils.showDialog(
                                    this@LoginActivity,
                                    getString(R.string.login_success),
                                    "warning"
                                )
                            }else login(account)
                        }
                    }else Utils.showDialog(
                        this@LoginActivity,
                        getString(R.string.server_fail),
                        "warning"
                    )
                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Utils.dismissProgress()
                    Utils.showDialog(
                        this@LoginActivity,
                        getString(R.string.server_fail),
                        "warning"
                    )
                }
            })
        } catch (e: ApiException) {
            Utils.showDialog(
                this@LoginActivity,
                getString(R.string.sonthing_wrong),
                "warning"
            )

        }
    }

    private fun login(account: GoogleSignInAccount) {
        Utils.showProgress(this, "Signing in... Please wait...")
        val call =
            Api.getRetrofit().getLogin(account.email!!)
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
                                this@LoginActivity,
                                getString(R.string.login_success),
                                "warning"
                            )
                        } else {
                            Utils.showDialog(
                                this@LoginActivity,
                                response.body()!!.message!!,
                                "warning"
                            )
                        }
                    } else {
                        Utils.showDialog(
                            this@LoginActivity,
                            getString(R.string.server_fail),
                            "warning"
                        )
                    }
                } else {
                    Utils.showDialog(
                        this@LoginActivity,
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
                    this@LoginActivity,
                    getString(R.string.server_fail),
                    "warning"
                )
            }
        })
    }
}