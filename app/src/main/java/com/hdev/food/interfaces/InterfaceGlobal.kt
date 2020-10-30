package com.hdev.food.interfaces

class InterfaceGlobal {
   interface mainGlobal {
        fun showLoading()
        fun dismissLoading()
    }

    interface NetworkListener {
            fun onSuccess(success: Boolean)
            fun onFailure(message: String)
            fun onError(message: String)
    }
}