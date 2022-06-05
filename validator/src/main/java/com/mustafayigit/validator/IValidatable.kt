package com.mustafayigit.validator

import com.mustafayigit.validator.base.BaseValidatableRule
import com.mustafayigit.validator.base.NotifyType

/**
 * Created by Mustafa Yiğit on 26/05/2022
 * mustafa.yt65@gmail.com
 */

interface IValidatable {

    var input: String

    // show the error message if the validation fails
    var currentErrorText: String?

    // rule list
    val rules: MutableList<BaseValidatableRule>

    var onValidation: (Boolean, String?, NotifyType) -> Unit

    // add rule and result action
    fun addRules(vararg rules: BaseValidatableRule) {
        this.rules.addAll(rules)
    }

    fun isValid(notifyType: NotifyType = NotifyType.ON_VALUE_CHANGE): Boolean

    fun validate(notifyType: NotifyType): Boolean {
        return isValid(notifyType)
    }
}