package com.realitix.nectar.work.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.ImageRepository
import com.realitix.nectar.util.EntityType
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