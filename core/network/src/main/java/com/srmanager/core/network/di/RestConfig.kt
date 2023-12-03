package com.srmanager.core.network.di

import org.json.JSONObject
import java.util.*

object RestConfig {
    const val LOCAL_URL = "http://115.127.82.2:8080/"

    fun validateToken(token: String): Boolean {
        try {
            val parts = token.split(".")
            val decode = String(Base64.getUrlDecoder().decode(parts[1]))
            val payload = JSONObject(decode)

            if (payload.getLong("exp") > (System.currentTimeMillis() / 1000)) {
                return true
            }
        } catch (_: Exception) {
        }
        return false
    }
}