package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.data.Repository

/**
 * Use case for retrieving the "is first launch" flag from the repository.
 *
 * @property repository The repository that handles data operations for the first launch flag.
 */
class RetrieveIsFirstLaunchUseCase(
    private val repository: Repository,
) {

    /**
     * Retrieves the "is first launch" flag, which indicates whether the application is being launched for the first time.
     *
     * @return `true` if it is the first launch, `false` otherwise.
     */
    suspend operator fun invoke(): Boolean {
        return repository.isFirstLaunch()
    }
}
