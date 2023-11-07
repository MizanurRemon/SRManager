package com.srmanager.core.common.domain.use_case

import com.srmanager.core.common.util.EMAIL_REGEX
import java.util.regex.Pattern

class EmailValidationResult {
    operator fun invoke(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_REGEX)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}