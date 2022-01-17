package com.nailnafir.jajanin.ui.auth.signin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.nailnafir.jajanin.Jajanin
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.response.login.LoginResponse
import com.nailnafir.jajanin.ui.MainActivity
import com.nailnafir.jajanin.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(), SignInContract.View {

    lateinit var presenter: SignInPresenter
    var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = SignInPresenter(this)

        // jika sudah ada data token, alihkan ke main activity (main page: home), tidak perlu login
        if (!Jajanin.getApp().getToken().isNullOrEmpty()) {
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }

        // inisialisasi
        initDummy()
        initView()

        // perintah tombol daftar
        button_sign_up.setOnClickListener {
            val signup = Intent(activity, AuthActivity::class.java)
            signup.putExtra("page_request", 2)
            startActivity(signup)
        }

        // perintah tombol masuk
        button_sign_in.setOnClickListener {
            var email = et_email.text.toString()
            var password = et_password.text.toString()

            if (email.isNullOrEmpty()) {
                et_email.error = "Silakan masukkan email Anda"
                et_email.requestFocus()
            } else if (password.isNullOrEmpty()) {
                et_password.error = "Silakan masukkan password Anda"
                et_password.requestFocus()
            } else {
                presenter.submitLogin(email, password)
            }
        }
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        Jajanin.getApp().setToken(loginResponse.access_token)

        // menyimpan data user ke dalam json
        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        Jajanin.getApp().setUser(json)

        // menuju ke main activity (main page: home)
        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finish()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initDummy() {
        et_email.setText("manusia.silver@gmail.com")
        et_password.setText("12345678")
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}