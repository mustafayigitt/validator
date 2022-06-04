package com.mustafayigit.validator.base


/**
 * Created by Mustafa Yiğit on 26/05/2022
 * mustafa.yt65@gmail.com
 */

open class BaseValidatableRule (
    open val errorMessage: String,
    open val rule: (input: String) -> Boolean,
)