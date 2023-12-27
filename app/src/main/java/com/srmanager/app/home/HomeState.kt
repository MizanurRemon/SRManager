package com.srmanager.app.home


data class HomeState(
    val isShowDialog: Boolean = false,
    val isLogOutLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isProfileComplete: Boolean = false,
    val userName: String = "",
    val rank: String = "",
    val avatarUrl:String="",
    val userScore:Int=0,
    val searchText: String = "",
    val address: String = ""
)
