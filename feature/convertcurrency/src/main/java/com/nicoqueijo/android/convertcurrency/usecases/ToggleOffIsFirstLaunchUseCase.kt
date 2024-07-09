package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.data.Repository

class ToggleOffIsFirstLaunchUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke() {
        repository.setFirstLaunch(value = false)
    }
}