package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.data.Repository

class RetrieveIsFirstLaunchUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(): Boolean {
        return repository.isFirstLaunch()
    }
}
