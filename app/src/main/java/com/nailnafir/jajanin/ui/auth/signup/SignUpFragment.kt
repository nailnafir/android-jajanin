package com.nailnafir.jajanin.ui.auth.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.model.request.RegisterRequest
import com.nailnafir.jajanin.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    var filePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDummy()
        initListener()


    }

    private fun initListener() {
        iv_profile.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .start()
        }

        button_continue.setOnClickListener {
            var fullname = et_full_name.text.toString()
            var email = et_email.text.toString()
            var password = et_password.text.toString()

            if (fullname.isNullOrEmpty()) {
                et_full_name.error = "Silakan masukkan nama lengkap"
                et_full_name.requestFocus()
            } else if (email.isNullOrEmpty()) {
                et_email.error = "Silakan masukkan email"
                et_email.requestFocus()
            } else if (password.isNullOrEmpty()) {
                et_password.error = "Silakan masukkan password"
                et_password.requestFocus()
            } else {
                var data = RegisterRequest(
                    fullname,
                    email,
                    password,
                    password,
                    "", "", "", "",
                    filePath
                )

                var bundle = Bundle()
                bundle.putParcelable("data", data)

                Navigation.findNavController(it)
                    .navigate(R.id.action_sign_up_address, bundle)
                (activity as AuthActivity).toolbarSignUpAddress()
            }




        }
    }

    private fun initDummy() {
        et_full_name.setText("Park Jihyo")
        et_email.setText("park.jihyo@twice.com")
        et_password.setText("12345678")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Tindakan dibatalkan", Toast.LENGTH_LONG).show()
        }
    }
}