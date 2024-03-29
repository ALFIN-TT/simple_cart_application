package com.alfin.productlistingapp.core.domain.usecase

interface BaseUseCase<In, Out> {
    suspend operator fun invoke(input: In): Out
}