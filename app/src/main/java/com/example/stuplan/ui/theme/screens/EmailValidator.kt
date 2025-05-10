

package com.example.lema.util

import android.util.Patterns

/**
 * Validates if the given string is a properly formatted email address
 *
 * @param email The email address to validate
 * @return True if the email is valid, false otherwise
 */
fun validateEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
