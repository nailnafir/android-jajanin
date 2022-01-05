package com.nailnafir.jajanin.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nailnafir.jajanin.R
import com.nailnafir.jajanin.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_continue.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_sign_up_address, null)

            (activity as AuthActivity).toolbarSignUpAddress()
        }
    }
}