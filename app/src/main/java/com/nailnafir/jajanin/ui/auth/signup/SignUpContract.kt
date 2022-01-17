package com.nailnafir.jajanin.ui.auth.signup

import android.net.Uri
import com.nailnafir.jajanin.base.BasePresenter
import com.nailnafir.jajanin.base.BaseView
import com.nailnafir.jajanin.model.request.RegisterRequest
import com.nailnafir.jajanin.model.response.login.LoginResponse

interface SignUpContract {
    interface  View: BaseView {
        fun onRegisterSuccess(loginResponse: LoginResponse, view:android.view.View)
        fun onRegisterPhotoSuccess(view:android.view.View)
        fun onRegisterFailed(message: String)
    }

    interface  Presenter: SignUpContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, view:android.view.View)
        fun submitPhotoRegister(filePath: Uri, view:android.view.View)
    }
}