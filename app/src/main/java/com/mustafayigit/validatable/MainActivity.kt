package com.mustafayigit.validatable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.mustafayigit.validatable.databinding.ActivityMainBinding
import com.mustafayigit.validator.ValidatableRule
import com.mustafayigit.validator.Validator

class MainActivity : AppCompatActivity() {
    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        /* Init Validator with Default Constructor */
        val validator = Validator(
            onValidation = { isValid, errorMessage ->
                Log.d("Validator", "isValid: $isValid, errorMessage: $errorMessage")
                mBinding.btnAction.isEnabled = isValid
            }
        )
        validator.addRules(
            ValidatableRule.Email("Enter valid mail address"),
            ValidatableRule.Required("Input is required"),
        )
        mBinding.editText.doOnTextChanged { text, _, _, _ ->
            validator.input = text.toString()
        }
        /* ------------------------------------------------- */

        /* Init Validator with Constructor */
        val validatorWithConstructor = Validator(
            ValidatableRule.Email("Enter valid mail address"),
            ValidatableRule.Required("Input is required"),
        ) { isValid, errorMessage ->
            Log.d("Validator", "isValid: $isValid, errorMessage: $errorMessage")
            mBinding.btnAction.isEnabled = isValid
        }
        mBinding.editText.doOnTextChanged { text, _, _, _ ->
            validatorWithConstructor.input = text.toString()
        }
        /* ------------------------------------------------- */

        /* Init Validator with Builder */
        val validatorWithBuilder = Validator.Builder()
            .addRules(
                ValidatableRule.Email("Enter valid mail address"),
                ValidatableRule.Required("Input is required"),
            )
            .addCollector {
                mBinding.editText.doOnTextChanged { text, _, _, _ ->
                    it.input = text.toString()
                }
            }
            .onValidate { isValid, errorMessage ->
                Log.d("Validator", "isValid: $isValid, errorMessage: $errorMessage")
                mBinding.btnAction.isEnabled = isValid
            }
            .build()
        /* ------------------------------------------------- */


    }
}