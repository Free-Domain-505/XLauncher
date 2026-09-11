package com.example.xlauncher.managers

interface AccountManager {
    fun getActiveAccount(): String?
    fun loginWithMicrosoft()
    fun loginLocal(username: String)
    fun logout()
}

class AccountManagerImpl : AccountManager {
    private var activeUser: String? = null

    override fun getActiveAccount(): String? = activeUser

    override fun loginWithMicrosoft() {
        // Architecture stub for real MS OAuth via Microsoft Identity / Xbox Live API
        activeUser = "MicrosoftUser (Pending Auth)"
    }

    override fun loginLocal(username: String) {
        activeUser = username
    }

    override fun logout() {
        activeUser = null
    }
}
