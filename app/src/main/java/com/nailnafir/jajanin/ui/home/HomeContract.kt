package com.nailnafir.jajanin.ui.home

import com.nailnafir.jajanin.base.BasePresenter
import com.nailnafir.jajanin.base.BaseView
import com.nailnafir.jajanin.model.response.home.HomeResponse
import com.nailnafir.jajanin.model.response.login.LoginResponse

interface HomeContract {
    interface  View: BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message: String)
    }

    interface  Presenter: HomeContract, BasePresenter {
        fun getHome()
    }
}