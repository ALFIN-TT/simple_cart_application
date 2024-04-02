package com.alfin.simplecartapp.core.domain.usecase

interface BaseUseCase<In, Out> {
    suspend operator fun invoke(input: In): Out
}