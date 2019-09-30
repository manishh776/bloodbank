package com.bloodbank.ui.auth

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bloodbank.R
import com.bloodbank.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_login.*
import com.bloodbank.utils.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), KodeinAware, AuthListener{
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private var progressDialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignUpBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_sign_up
        )
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        progressDialog = ProgressDialog(this)
        bottom_arc.setTargetView(top_image)
    }

    override fun onInputError(message: String) {
        root_layout.snackbar(message)
    }

    override fun onRegisterSuccess(message: String) {
        progressDialog?.dismiss()
        root_layout.snackbar(message)
    }

    override fun onMobileExists() {
        progressDialog?.dismiss()
        root_layout.snackbar("This mobile is already in use")
    }

    override fun onFailure(message: String) {
        root_layout.snackbar(message)
    }

    override fun onMobileExistsCheckStarted(message: String) {
        progressDialog?.setMessage(message)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    override fun onLoginSuccess() {
    }

    override fun onLoginFailed() {
    }

    override fun onLoginStarted() {
    }
}
