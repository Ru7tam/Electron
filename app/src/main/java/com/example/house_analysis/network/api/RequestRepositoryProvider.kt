package com.example.house_analysis.network.api

object RequestRepositoryProvider {
    fun provideRequestRepository(): RequestRepository {
        return RequestRepository(ApiService.create())
    }

}