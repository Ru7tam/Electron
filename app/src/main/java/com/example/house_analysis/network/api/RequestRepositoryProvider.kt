package com.example.house_analysis.network.api

import com.example.house_analysis.network.api.requests.RequestProvider

object RequestRepositoryProvider {
    fun provideRequestRepository(): RequestProvider {
        return RequestProvider(ApiService.create())
    }

}