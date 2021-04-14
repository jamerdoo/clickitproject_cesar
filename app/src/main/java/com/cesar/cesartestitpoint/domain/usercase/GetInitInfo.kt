package com.cesar.cesartestitpoint.domain.usercase


import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.MyDataResponse
import com.cesar.cesartestitpoint.domain.repository.InfoRepository

class GetInitInfo(private val infoRepository: InfoRepository) :
    BaseUseCase<Resource<MyDataResponse>, Any>() {

    override suspend fun run(params: Any?): Resource<MyDataResponse>{
        return infoRepository.getInfo()
    }

}