package com.nailnafir.jajanin.ui.auth.signup

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.nailnafir.jajanin.Jajanin
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.request.RegisterRequest
import com.nailnafir.jajanin.model.response.login.LoginResponse
import com.nailnafir.jajanin.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_sign_up_address.*

class SignUpAddressFragment : Fragment(), SignUpContract.View {

    private lateinit var data: RegisterRequest
    lateinit var presenter: SignUpPresenter
    var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = SignUpPresenter(this)
        data = arguments?.getParcelable<RegisterRequest>("data")!!

        initDummy()
        initListener()
        initView()
    }

    private fun initListener() {
        button_sign_up_now.setOnClickListener {
            var phone_number = et_phone_number.text.toString()
            var address = et_address.text.toString()
            var house_number = et_house_number.text.toString()
            var city = et_city.text.toString()

            data.let {
                it.address = address
                it.city = city
                it.houseNumber = house_number
                it.phoneNumber = phone_number
            }

            if (phone_number.isNullOrEmpty()) {
                et_phone_number.error = "Silakan masukkan nomor telepon"
                et_phone_number.requestFocus()
            } else if (address.isNullOrEmpty()) {
                et_address.error = "Silakan masukkan alamat rumah"
                et_address.requestFocus()
            } else if (house_number.isNullOrEmpty()) {
                et_house_number.error = "Silakan masukkan nomor rumah"
                et_house_number.requestFocus()
            } else if (city.isNullOrEmpty()) {
                et_city.error = "Silakan masukkan nama kota"
                et_city.requestFocus()
            } else {
                presenter.submitRegister(data, it)
                presenter.submitPhotoRegister(data.filePath!!, it)
            }
        }
    }

    private fun initDummy() {
        et_phone_number.setText("081255880055")
        et_address.setText("Jalan Kenangan")
        et_house_number.setText("18")
        et_city.setText("Jakarta")
    }

    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {
        Jajanin.getApp().setToken(loginResponse.access_token)

        // menyimpan data user ke dalam json
        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        Jajanin.getApp().setUser(json)

        if (data.filePath == null) {
            Navigation.findNavController(view)
                .navigate(R.id.action_sign_up_success, null)

            (activity as AuthActivity).toolbarSignUpSuccess()
        } else {
            presenter.submitPhotoRegister(data.filePath!!, view)
        }
    }

    override fun onRegisterPhotoSuccess(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_sign_up_success, null)

        (activity as AuthActivity).toolbarSignUpSuccess()
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
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