package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.ImageRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.MealUtil
import java.io.File

class ImageSynchronizer(val context: Context, val repository: ImageRepository): SynchronizerInterface {
    private fun getEntityType(): EntityType = EntityType.IMAGE

    override fun fromGitToDb(repositoryName: String, uuid: String) {
        if(repository.get(uuid) == null) {
            val srcPath = File(File(MealUtil.getRepositoryFolder(context, repositoryName), getEntityType().folderName), uuid)
            val dstPath = File(MealUtil.getImageFolder(context), uuid)
            srcPath.copyTo(dstPath)

            repository.insert(ImageRaw(uuid, dstPath.absolutePath))
        }
    }

}