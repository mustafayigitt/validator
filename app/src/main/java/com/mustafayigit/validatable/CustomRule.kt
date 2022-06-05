package com.mustafayigit.validatable

import com.mustafayigit.validator.base.BaseValidatableRule
import com.mustafayigit.validator.base.NotifyType


/**
 * Created by Mustafa Yiğit on 05/06/2022
 * mustafa.yt65@gmail.com
 */
class PasswordConfirmRule(
    override val errorMessage: String,
    override val notifyType: NotifyType,
    val originalInputGetter: () -> String
) : BaseValidatableRule(
    errorMessage = errorMessage,
    notifyType = notifyType,
    rule = { it == originalInputGetter.invoke() }
)
