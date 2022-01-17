package com.nailnafir.jajanin.ui.auth.signin

import com.nailnafir.jajanin.base.BasePresenter
import com.nailnafir.jajanin.base.BaseView
import com.nailnafir.jajanin.model.response.login.LoginResponse

interface SignInContract {
    interface  View: BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message: String)
    }

    interface  Presenter: SignInContract, BasePresenter {
        fun submitLogin(email: String, password: String)
    }
}