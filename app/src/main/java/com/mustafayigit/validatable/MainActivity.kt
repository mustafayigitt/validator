package com.mustafayigit.validatable

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.mustafayigit.validatable.databinding.ActivityMainBinding
import com.mustafayigit.validator.ValidatableRule
import com.mustafayigit.validator.Validator
import com.mustafayigit.validator.base.NotifyType

class MainActivity : AppCompatActivity() {
    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val emailValidator = Validator.Builder()
            .addRules(
                ValidatableRule.Email("Enter valid mail address"),
                ValidatableRule.Required(
                    "Email is required", NotifyType.ON_FOCUS_CHANGE,
                ),
                ValidatableRule.Email(
                    "Enter valid mail address", NotifyType.ON_FOCUS_CHANGE,
                ),
                ValidatableRule.Required(
                    "Email is required", NotifyType.ON_FORM_SUBMIT,
                ),
                ValidatableRule.Email(
                    "Enter valid mail address", NotifyType.ON_FORM_SUBMIT,
                ),
            )
            .addCollector {
                mBinding.txtInputEmail.editText?.doOnTextChanged { text, _, _, _ ->
                    it.input = text.toString()
                }
                mBinding.txtInputEmail.editText?.setOnFocusChangeListener { _, isFocused ->
                    it.isFocused = isFocused
                }
            }
            .onValidate { isValid, errorMessage, notifyType ->
                Log.d(
                    "Validator",
                    "notifyType: $notifyType, isValid: $isValid, errorMessage: $errorMessage"
                )
                mBinding.txtInputEmail.error = errorMessage
            }
            .build()

        val passwordValidator = Validator.Builder()
            .addRules(
                ValidatableRule.Password("Enter valid password"),
                PasswordConfirmRule(
                    errorMessage = "Passwords does not match",
                    notifyType = NotifyType.ON_VALUE_CHANGE,
                    originalInputGetter = { mBinding.txtInputPasswordConfirm.editText?.text.toString() },
                ),
                ValidatableRule.Required(
                    "Password is required", NotifyType.ON_FOCUS_CHANGE,
                ),
                ValidatableRule.Password(
                    "Enter valid password", NotifyType.ON_FOCUS_CHANGE,
                ),
                ValidatableRule.Required(
                    "Password is required", NotifyType.ON_FORM_SUBMIT,
                ),
                ValidatableRule.Password(
                    "Enter valid password", NotifyType.ON_FORM_SUBMIT,
                ),
            )
            .addCollector {
                mBinding.txtInputPassword.editText?.doOnTextChanged { text, _, _, _ ->
                    it.input = text.toString()
                }
                mBinding.txtInputPassword.editText?.setOnFocusChangeListener { _, isFocused ->
                    it.isFocused = isFocused
                }
            }
            .onValidate { isValid, errorMessage, notifyType ->
                Log.d(
                    "Validator",
                    "notifyType: $notifyType, isValid: $isValid, errorMessage: $errorMessage"
                )
                mBinding.txtInputPassword.error = errorMessage
            }
            .build()

        val passwordConfirmValidator = Validator.Builder()
            .addRules(
                PasswordConfirmRule(
                    errorMessage = "Passwords does not match",
                    notifyType = NotifyType.ON_VALUE_CHANGE,
                    originalInputGetter = { mBinding.txtInputPassword.editText?.text.toString() },
                ),
                PasswordConfirmRule(
                    errorMessage = "Passwords does not match",
                    notifyType = NotifyType.ON_FORM_SUBMIT,
                    originalInputGetter = { mBinding.txtInputPassword.editText?.text.toString() },
                ),
                ValidatableRule.Required(
                    "Password Confirm is required", NotifyType.ON_FOCUS_CHANGE,
                ),
                ValidatableRule.Required(
                    "Password Confirm is required", NotifyType.ON_FORM_SUBMIT,
                ),
            )
            .addCollector {
                mBinding.txtInputPasswordConfirm.editText?.doOnTextChanged { text, _, _, _ ->
                    it.input = text.toString()
                }
                mBinding.txtInputPasswordConfirm.editText?.setOnFocusChangeListener { _, isFocused ->
                    it.isFocused = isFocused
                }
            }
            .onValidate { isValid, errorMessage, notifyType ->
                Log.d(
                    "Validator",
                    "notifyType: $notifyType, isValid: $isValid, errorMessage: $errorMessage"
                )
                mBinding.txtInputPasswordConfirm.error = errorMessage
            }
            .build()

        // set isFormSubmitted to true to validate on form submit
        mBinding.btnRegister.setOnClickListener {
            emailValidator.submitForm()
            passwordValidator.submitForm()
            passwordConfirmValidator.submitForm()

            if (emailValidator.submitForm().not() || passwordValidator.submitForm().not()) {
                Toast.makeText(this, "Form is not valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(
                this,
                "Success",
                Toast.LENGTH_SHORT
            ).show()

        }


    }
}