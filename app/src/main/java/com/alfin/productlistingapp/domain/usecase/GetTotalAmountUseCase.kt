package com.alfin.productlistingapp.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetTotalAmountUseCase {
    suspend operator fun invoke(): Flow<Int>
}