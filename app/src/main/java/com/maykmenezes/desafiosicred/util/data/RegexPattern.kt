package com.maykmenezes.desafiosicred.util.data

const val validEmailRegexPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

const val validNameRegexPattern = "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)"