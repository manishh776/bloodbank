package com.bloodbank.ui.auth

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bloodbank.R
import com.bloodbank.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import com.bloodbank.utils.snackbar
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware, AuthListener{
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private var progressDialog : ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        progressDialog = ProgressDialog(this)
        bottom_arc.setTargetView(top_image)
    }

    override fun onLoginSuccess() {
        progressDialog?.dismiss()
        root_layout.snackbar("Login Success")
    }

    override fun onLoginFailed() {
        progressDialog?.dismiss()
        root_layout.snackbar("Login failure")
    }

    override fun onLoginStarted() {
        progressDialog?.setMessage("Loggin in ...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    override fun onInputError(message: String) {
    }

    override fun onMobileExistsCheckStarted(message: String) {
    }

    override fun onMobileExists() {
    }

    override fun onRegisterSuccess(message: String) {
    }

    override fun onFailure(message: String) {
    }
}
