package com.srmanager.core.common.domain.use_case

import com.srmanager.core.common.util.PASSWORD_REGEX
import java.util.regex.Pattern

class PasswordValidationResult {
    operator fun invoke(password: String): Boolean {
        val pattern = Pattern.compile(PASSWORD_REGEX)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}