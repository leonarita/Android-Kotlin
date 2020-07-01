package com.example.app5.repository.cache

import com.example.app5.entities.PriorityEntity

/**
 * Acesso rápido a prioridades
 * */
class PriorityCacheConstants private constructor() {

    companion object {
        fun setCache(list: List<PriorityEntity>) {
            for (item in list) {
                mPriorityCache[item.id] = item.description
            }
        }

        fun getPriorityDescription(id: Int): String {
            return if (mPriorityCache[id] == null) {
                ""
            } else
                mPriorityCache[id].toString()
        }

        private val mPriorityCache = hashMapOf<Int, String>()
    }

}