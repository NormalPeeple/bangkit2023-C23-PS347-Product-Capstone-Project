package com.example.storyvan.customview

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyvan.R

class PasswordEditText : AppCompatEditText {

    private val errorColor = Color.RED
    private val normalColor = Color.BLACK

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.length < 8) {
                    setErrorMessage("Password should be at least 8 characters")
                    (this@PasswordEditText.parent as? ViewGroup)?.findViewById<View>(R.id.buttonLogin)?.isEnabled = false
                } else {
                    removeErrorMessage()
                    (this@PasswordEditText.parent as? ViewGroup)?.findViewById<View>(R.id.buttonLogin)?.isEnabled = true
                }
            }
        })
    }

    private fun setErrorMessage(errorMessage: String) {
        error = errorMessage
        setTextColor(errorColor)
    }

    private fun removeErrorMessage() {
        error = null
        setTextColor(normalColor)
    }
}
