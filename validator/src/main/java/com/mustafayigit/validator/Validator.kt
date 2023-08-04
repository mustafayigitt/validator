package com.mustafayigit.validator

import com.mustafayigit.validator.base.BaseValidatableRule
import com.mustafayigit.validator.base.NotifyType


/**
 * Created by Mustafa Yiğit on 26/05/2022
 * mustafa.yt65@gmail.com
 */

class Validator(
    override var onValidation: (Boolean, String?, NotifyType) -> Unit
) : IValidatable {

    constructor(
        vararg rules: BaseValidatableRule,
        onValidation: (Boolean, String?, NotifyType) -> Unit
    ) : this(onValidation) {
        this.rules.addAll(rules)
    }

    override var input: String = ""
        set(value) {
            field = value
            validate(NotifyType.ON_VALUE_CHANGE)
        }

    var isFocused: Boolean = false
        set(value) {
            field = value
            validate(NotifyType.ON_FOCUS_CHANGE)
        }

    override var currentErrorText: String? = null
    override val rules: MutableList<BaseValidatableRule> = mutableListOf()

    override fun isValid(notifyType: NotifyType): Boolean {
        val filteredRules = rules.filter { it.notifyType == notifyType }

        for (rule in filteredRules) {
            if (!rule.rule.invoke(input)) {
                currentErrorText = rule.errorMessage
                break
            }
        }
        val isValid = rules.all { it.rule.invoke(input) }
        onValidation(isValid, currentErrorText, notifyType)
        return isValid
    }

    fun submitForm(): Boolean {
        return validate(NotifyType.ON_FORM_SUBMIT)
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
        private var onValidation: (Boolean, String?, NotifyType) -> Unit = { _, _, _ -> }
        private var collector: ((Validator) -> Unit)? = null

        fun addRules(vararg rules: BaseValidatableRule): Builder {
            this.rules.addAll(rules)
            return this
        }

        fun addCollector(collector: (Validator) -> Unit): Builder {
            this.collector = collector
            return this
        }

        fun onValidate(onValidation: (Boolean, String?, NotifyType) -> Unit): Builder {
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