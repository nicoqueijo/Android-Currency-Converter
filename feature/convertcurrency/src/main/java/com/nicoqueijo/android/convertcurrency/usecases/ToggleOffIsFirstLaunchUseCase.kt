package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.data.Repository

/**
 * Use case to toggle off the flag indicating the first launch of the application. It updates the repository
 * to mark the first launch as completed.
 *
 * @property repository The repository interface for data operations.
 */
class ToggleOffIsFirstLaunchUseCase(
    private val repository: Repository,
) {

    /**
     * Toggles off the first launch flag in the repository.
     */
    suspend operator fun invoke() {
        repository.setFirstLaunch(value = false)
    }
}