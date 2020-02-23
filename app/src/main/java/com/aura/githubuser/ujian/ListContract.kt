package com.aura.githubuser.ujian

interface ListContract {
    interface View {
        fun setElements(elements: List<Element>)
        fun showLoading()
        fun hideLoading()
        fun showError()
    }
}