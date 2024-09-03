package com.omo.rememberme.domain.usecases.app_entry

import com.omo.rememberme.domain.manager.OnBoardingManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val onBoardingManager: OnBoardingManager
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return onBoardingManager.readAppEntry()
    }

}