package com.cesar.cesartestitpoint.domain.usercase

import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.MyDataResponse
import com.cesar.cesartestitpoint.domain.repository.InfoRepository

class GetInfoByCurrency(private val infoRepository: InfoRepository) :
    BaseUseCase<Resource<MyDataResponse>, String>() {

    override suspend fun run(params: String?): Resource<MyDataResponse> {
        return infoRepository.getInfoByCurrency(params.toString())
    }
}