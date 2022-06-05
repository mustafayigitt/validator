package com.mustafayigit.validator

import com.mustafayigit.validator.base.BaseValidatableRule
import com.mustafayigit.validator.base.NotifyType
import com.mustafayigit.validator.util.Constants


/**
 * Created by Mustafa Yiğit on 26/05/2022
 * mustafa.yt65@gmail.com
 */

sealed class ValidatableRule(
    override val errorMessage: String,
    override val notifyType: NotifyType = NotifyType.ON_VALUE_CHANGE,
    override val rule: (input: String) -> Boolean
) : BaseValidatableRule(errorMessage, notifyType, rule) {

    class Email(
        override val errorMessage: String,
        override val notifyType: NotifyType = NotifyType.ON_VALUE_CHANGE,
    ) : ValidatableRule(
        errorMessage = errorMessage,
        rule = { input -> Constants.REGEX_EMAIL.matches(input) }
    )

    class Password(
        override val errorMessage: String,
        override val notifyType: NotifyType = NotifyType.ON_VALUE_CHANGE,
    ) : ValidatableRule(
        errorMessage = errorMessage,
        rule = { input ->
            input.length >= 6 && input.any { it.isUpperCase() } && input.any { it.isDigit() } && input.any { it.isLowerCase() }
        }
    )

    class CharacterLength(
        private val minCharacter: Int = 0,
        private val maxCharacter: Int = 30,
        override val errorMessage: String,
        override val notifyType: NotifyType = NotifyType.ON_FOCUS_CHANGE,
    ) : ValidatableRule(
        errorMessage = errorMessage,
        rule = { input -> input.length in minCharacter..maxCharacter }
    )

    class Required(
        override val errorMessage: String,
        override val notifyType: NotifyType = NotifyType.ON_FOCUS_CHANGE,
    ) : ValidatableRule(
        errorMessage = errorMessage,
        rule = { input -> input.isNotEmpty() }
    )
}