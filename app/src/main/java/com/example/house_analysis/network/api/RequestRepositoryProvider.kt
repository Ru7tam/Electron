package com.example.house_analysis.network.api

import com.example.house_analysis.network.api.builtRequests.RequestRepository

object RequestRepositoryProvider {
    fun provideRequestRepository(): RequestRepository {
        return RequestRepository(ApiService.create())
    }

}