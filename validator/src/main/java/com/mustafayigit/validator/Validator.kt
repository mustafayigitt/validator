package com.mustafayigit.validator

import com.mustafayigit.validator.base.BaseValidatableRule


/**
 * Created by Mustafa Yiğit on 26/05/2022
 * mustafa.yt65@gmail.com
 */

class Validator(
    override var onValidation: (Boolean, String?) -> Unit
) : IValidatable {

    constructor(
        vararg rules: BaseValidatableRule,
        onValidation: (Boolean, String?) -> Unit
    ) : this(onValidation) {
        this.rules.addAll(rules)
    }

    override var input: String = ""
        set(value) {
            field = value
            validate()
        }

    override var currentErrorText: String? = null
    override val rules: MutableList<BaseValidatableRule> = mutableListOf()

    override fun isValid(): Boolean {
        var isValid = true
        currentErrorText = null
        for (rule in rules) {
            if (!rule.rule.invoke(input)) {
                isValid = false
                currentErrorText = rule.errorMessage
                break
            }
        }
        onValidation(isValid, currentErrorText)
        return isValid
    }


    /**
    Validator.Builder()
        .addRules(
            ValidatableRule.Email("Enter valid mail address"),
            ValidatableRule.Required("Input is required"),
        )
        .addCollector {
            mBinding.editText.doOnTextChanged { text, _, _, _ ->
                inputValidator.input = text.toString()
            }
        }
        .onValidate { isValid, errorMessage ->
            Log.d("Validator", "isValid: $isValid, errorMessage: $errorMessage")
            mBinding.btnAction.isEnabled = isValid
        }
        .build()
     */

    class Builder {
        private val rules: MutableList<BaseValidatableRule> = mutableListOf()
        private var onValidation: (Boolean, String?) -> Unit = { _, _ -> }
        private var collector: ((Validator) -> Unit)? = null

        fun addRules(vararg rules: BaseValidatableRule): Builder {
            this.rules.addAll(rules)
            return this
        }

        fun addCollector(collector: (Validator) -> Unit): Builder {
            this.collector = collector
            return this
        }

        fun onValidate(onValidation: (Boolean, String?) -> Unit): Builder {
            this.onValidation = onValidation
            return this
        }

        fun build(): Validator {
            val validator = Validator(onValidation)
            validator.rules.addAll(rules)
            collector?.invoke(validator)
            return validator
        }
    }
}