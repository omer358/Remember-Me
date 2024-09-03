package com.omo.rememberme.domain.usecases.app_entry

import com.omo.rememberme.domain.manager.OnBoardingManager

class SaveAppEntry(
    private val onBoardingManager: OnBoardingManager
) {
    suspend operator fun invoke() {
        onBoardingManager.saveAppEntry()
    }

}