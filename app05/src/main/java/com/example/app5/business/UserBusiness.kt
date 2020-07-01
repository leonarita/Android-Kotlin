package com.example.app5.business

import android.content.Context
import com.example.app5.R
import com.example.app5.constants.TaskConstants
import com.example.app5.entities.UserEntity
import com.example.app5.infra.SecurityPreferences
import com.example.app5.infra.ValidationException
import com.example.app5.repository.UserRepository

class UserBusiness(val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    /**
     * Responsável por verificar se usuário existe e se os dados estão corretos
     * Caso exista, salva dos dados no SharedPreferences para uso posterior
     * */
    fun login(email: String, password: String): Boolean {

        val user: UserEntity? = mUserRepository.get(email, password)
        return if (user != null) {

            // Salva os dados no SharedPreferences
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            // Retorna usuário válido
            true

        } else {
            false
        }

    }

    /**
     * Salva o usuário no banco de dados e verifica se já existe
     * Caso já exista, lança um erro de email já existente
     * */
    fun save(name: String, email: String, password: String) {
        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.preencha_todos_campos))
            }

            // Verifica se email já existe no banco de dados
            if (mUserRepository.isEmailExistent(email)) {
                throw ValidationException(context.getString(R.string.email_ja_em_uso))
            }

            // Salva novo usuário, retornando o ID inserido
            val userId = mUserRepository.insert(name, email, password)

            // Salva os dados no SharedPreferences
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)
        } catch (e: Exception) {
            throw e
        }
    }

}