package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.ImageRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.MealUtil
import java.io.File

class ImageSynchronizer(
    private val repository: ImageRepository,
    private val baseRepositoryFolder: File,
    private val baseImageFolder: File
): SynchronizerInterface {
    private fun getEntityType(): EntityType = EntityType.IMAGE

    override fun fromGitToDb(gitRepositoryName: String, uuid: String) {
        if(repository.get(uuid) == null) {
            val repo = File(baseRepositoryFolder, gitRepositoryName)
            val srcPath = File(File(repo, getEntityType().folderName), uuid)
            val dstPath = File(baseImageFolder, uuid)
            srcPath.copyTo(dstPath)

            repository.insert(ImageRaw(uuid, dstPath.absolutePath))
        }
    }

}