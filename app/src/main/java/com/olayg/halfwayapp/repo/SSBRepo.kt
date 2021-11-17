package com.olayg.halfwayapp.repo

import android.util.Log
import com.olayg.halfwayapp.model.custom.Character
import com.olayg.halfwayapp.model.response.CharacterResponse
import com.olayg.halfwayapp.repo.remote.RetrofitInstance

object SSBRepo {

    private val smashBrosUnofficialService by lazy { RetrofitInstance.smashBrosUnofficialService }
    private val smashLoungeService by lazy { RetrofitInstance.smashLoungeService }

    suspend fun getAllCharacters(): List<Character> = try {
        smashLoungeService.getAllCharacters().map { characterResponse ->
            val data = Character.convertToCharacter(characterResponse, getImage(characterResponse))
            data
        }
    } catch (ex: Exception) {
        Log.e("SSB_Repo: ", "Caught getAllCharacters $ex")
        emptyList()
    }

    private suspend fun getImage(character: CharacterResponse) = try {
        smashBrosUnofficialService.getAllCharacters(character.name).firstOrNull()?.image
    } catch (ex: Exception) {
        Log.e("SSB_Repo: ", "Caught getImage Exception")
        null
    }
}

