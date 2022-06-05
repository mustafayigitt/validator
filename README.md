<h1 align=center>Validator</h1>

<div align="center">
  <a href="https://jitpack.io/#mustafayigitt/validator">
    <img src="https://jitpack.io/v/mustafayigitt/validator.svg" />
  </a>
  <p>
    Validate your inputs by <strong>notify types</strong>
  </p> 
</div>

<p align=center>
  <img src="https://user-images.githubusercontent.com/43048105/172043553-72a15316-f81d-496f-ad21-eccadc42c473.gif" width="250">
</p>

## Import [![](https://jitpack.io/v/mustafayigitt/validator.svg)](https://jitpack.io/#mustafayigitt/validator)

- **project level**
````gradle
allprojects {
 repositories {
 // ...
   maven { url 'https://jitpack.io' }
  }
}
````

- **module level**
````gradle
dependencies {
    implementation 'com.github.mustafayigitt:validator:Tag'
}
````

## How to use

````kotlin
// Create a validator with Builder pattern
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

### Or you can write a custom ValidatableRule
````kotlin
class PasswordConfirmRule(
    override val errorMessage: String,
    override val notifyType: NotifyType,
    val originalInputGetter: () -> String
) : BaseValidatableRule(
    errorMessage = errorMessage,
    notifyType = notifyType,
    rule = { it == originalInputGetter.invoke() }
)

// Usage
val passwordConfirmValidator = Validator.Builder()
    .addRules(
        PasswordConfirmRule(
            errorMessage = "Passwords does not match",
            notifyType = NotifyType.ON_VALUE_CHANGE,
            originalInputGetter = { mBinding.txtInputPassword.editText?.text.toString() },
            )
        )
        ...
````

## License
```xml
Copyright 2022 mustafayigitt (Mustafa Yigit)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

