package com.maykmenezes.desafiosicred.util.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import com.maykmenezes.desafiosicred.R

data class ScreenStatus(
        val loadingVisibility: Int,
        val contentVisibility: Int,
        val errorVisibility: Int? = null,
        val errorIcon: Int? = null,
        val errorMessage: Int? = null,
        val dialogTitle: Int? = null,
        val dialogTextButton: Int? = null)

object CustomScreenStates {
    val loading = ScreenStatus(
            loadingVisibility = VISIBLE,
            contentVisibility = GONE,
            errorVisibility = GONE)

    val success = ScreenStatus(
            loadingVisibility = GONE,
            contentVisibility = VISIBLE,
            errorVisibility = GONE,
            dialogTitle = R.string.textSuccess,
            dialogTextButton = R.string.textButtonOk)

    val unknownError = ScreenStatus(
            loadingVisibility = GONE,
            contentVisibility = GONE,
            errorVisibility = VISIBLE,
            errorIcon = R.drawable.ic_error,
            errorMessage = R.string.errorRequest,
            dialogTitle = R.string.textError,
            dialogTextButton = R.string.textButtonOk)

    val noConnectionError = ScreenStatus(
            loadingVisibility = GONE,
            contentVisibility = GONE,
            errorVisibility = VISIBLE,
            errorIcon = R.drawable.ic_no_internet_error,
            errorMessage = R.string.noConnectionError,
            dialogTitle = R.string.textError,
            dialogTextButton = R.string.textButtonOk)
}