package com.example.app5.business

import android.content.Context
import com.example.app5.entities.PriorityEntity
import com.example.app5.repository.PriorityRepository

class PriorityBusiness (context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    /**
     * Retorna lista de prioridades
     * */
    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()

}