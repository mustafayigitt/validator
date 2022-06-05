<h1 align=center>validator</h1>

<div align="center">
  <a href="https://jitpack.io/#mustafayigitt/validator">
    <img src="https://jitpack.io/v/mustafayigitt/validator.svg" />
  </a>
  <p> Validate your inputs <strong>notify based</strong> </p>
</div>

<p align=center>
  <img src="https://user-images.githubusercontent.com/43048105/172043553-72a15316-f81d-496f-ad21-eccadc42c473.gif" width="250">
</p>

````kotlin

val emailValidator = Validator.Builder()
    .addRules(
        ValidatableRule.Email("Enter valid mail address"),
        ValidatableRule.Required("Email is required", NotifyType.ON_FOCUS_CHANGE),
        ValidatableRule.Required("Email is required", NotifyType.ON_FORM_SUBMIT),
        ValidatableRule.Email("Enter valid mail address", NotifyType.ON_FORM_SUBMIT),
    )
    .addCollector { validator ->
        mBinding.txtInputEmail.editText?.doOnTextChanged { text, _, _, _ ->
            validator.input = text.toString()
        }
        mBinding.txtInputEmail.editText?.setOnFocusChangeListener { _, isFocused ->
            validator.isFocused = isFocused
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
````
