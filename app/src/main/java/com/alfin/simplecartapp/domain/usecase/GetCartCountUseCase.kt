package com.alfin.simplecartapp.domain.usecase

interface GetCartCountUseCase {
    suspend operator fun invoke(): Int
}