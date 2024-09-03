package com.omo.rememberme.domain.manager

import kotlinx.coroutines.flow.Flow

interface OnBoardingManager {
    suspend fun saveAppEntry()
    suspend fun readAppEntry(): Flow<Boolean>

}