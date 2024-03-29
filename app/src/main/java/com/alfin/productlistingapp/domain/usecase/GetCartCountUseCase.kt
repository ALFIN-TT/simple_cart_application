package com.alfin.productlistingapp.domain.usecase

interface GetCartCountUseCase {
    suspend operator fun invoke(): Int
}