package com.realitix.mealassistant.command

import android.content.Context
import android.util.Log
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.AlimentCategory
import com.realitix.mealassistant.database.entity.AlimentNutrition
import com.realitix.mealassistant.database.entity.Receipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.text.Normalizer
import java.util.*


class UAliment {
    var ciqual_code: Int = 0
    lateinit var name_fr: String
    lateinit var group_name_fr: String
    lateinit var nutrition: AlimentNutrition
}

class UAlimentRoot {
    var version: Long = 0
    lateinit var aliments: List<UAliment>
}

interface AlimentService {
    @GET("realitix/food-database/master/out/data.json")
    fun fetch(): Call<UAlimentRoot>
}

class AlimentUpdater {
    suspend fun update(context: Context) {
        Log.i("rawfood", "Update aliments")
        val retrofit = Retrofit.Builder()
            .baseUrl(ALIMENT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: AlimentService = retrofit.create<AlimentService>(AlimentService::class.java)
        val fetch = service.fetch()
        val call = fetch.execute()
        val data = call.body()

        //GlobalScope.launch {
            updateInternal(context, data)
        //}
    }

    suspend fun updateInternal(context: Context, data: UAlimentRoot?) {
        val db: MealDatabase = MealDatabase.getInstance(context)
        var nbAlimentsUpdated = 0

        // Check create receipe
        //val rt = Receipe("Popolopo", 1, 3)
        //db.receipeDao().insert(rt)

        for (aliment in data!!.aliments) {
            // Get category
            val group_name = aliment.group_name_fr
            var ac: AlimentCategory? = db.alimentCategoryDao().getByName(group_name)
            if (ac == null) {
                val ac_insert = AlimentCategory(group_name)
                val new_id: Long = db.alimentCategoryDao().insert(ac_insert)
                ac = ac_insert
                ac.id = new_id
            }
            // Insert aliment
            val asciiName =
                Normalizer.normalize(aliment.name_fr, Normalizer.Form.NFD)
                    .replace("[^\\p{ASCII}]".toRegex(), "")
            val a: Aliment? = db.alimentDao().getByName(aliment.name_fr)
            if (a == null) {
                val a_insert = Aliment(aliment.name_fr, asciiName, ac.id, aliment.nutrition)
                db.alimentDao().insert(a_insert)
                nbAlimentsUpdated++
            }
        }

        Log.i("rawfood", "$nbAlimentsUpdated aliments updated")
    }

    companion object {
        const val ALIMENT_URL =
            "https://raw.githubusercontent.com"
    }
}