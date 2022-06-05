package com.mustafayigit.validator.util


/**
 * Created by Mustafa Yiğit on 26/05/2022
 * mustafa.yt65@gmail.com
 */
object Constants {

    // regex for email
    val REGEX_EMAIL =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$".toRegex()

}