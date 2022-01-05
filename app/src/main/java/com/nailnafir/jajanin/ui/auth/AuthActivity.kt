package com.nailnafir.jajanin.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.nailnafir.jajanin.R
import kotlinx.android.synthetic.main.layout_toolbar.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val pageRequest = intent.getIntExtra("page_request", 0);
        if (pageRequest == 2) {
            toolbarSignUp()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragment_sign_in, true)
                .build()

            Navigation.findNavController(findViewById(R.id.auth_host_fragment))
                .navigate(R.id.action_sign_up, null, navOptions);
        }
    }

    private fun toolbarSignUp() {
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Daftar"
        toolbar.subtitle = "Daftar dulu lalu makan"
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000, null)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun toolbarSignUpAddress() {
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Alamat"
        toolbar.subtitle = "Pastikan semuanya benar"
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000, null)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun toolbarSignUpSuccess() {
        toolbar.visibility = View.GONE
    }
}