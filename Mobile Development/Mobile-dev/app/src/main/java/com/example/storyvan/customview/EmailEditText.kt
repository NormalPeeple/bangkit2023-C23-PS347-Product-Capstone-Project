package com.example.storyvan.customview

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText : AppCompatEditText {

    private val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmailFormat()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validateEmailFormat() {
        val input = text.toString().trim()
        if (input.isEmpty()) {
            setError(null)
            return
        }

        val isValid = emailRegex.matches(input)
        if (!isValid) {
            setError("Invalid email format")
        } else {
            setError(null)
        }
    }

    private fun setError(errorMessage: String?) {
        error = errorMessage
        error?.let {
            setTextColor(Color.RED)
        } ?: run {
            setTextColor(Color.BLACK)
        }
    }
}