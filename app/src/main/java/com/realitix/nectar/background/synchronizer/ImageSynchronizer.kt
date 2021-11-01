package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.background.DummyNotifier
import com.realitix.nectar.background.NotifierInterface
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
            srcPath.copyTo(dstPath, true)

            repository.insert(ImageRaw(uuid, dstPath.absolutePath))
        }
    }

    override fun fromDbToGit(gitRepositoryName: String, uuid: String, forceDelete: Boolean) {
        val image = repository.get(uuid)!!
        val srcPath = File(image.path)
        val dstPath = File(File(File(baseRepositoryFolder, gitRepositoryName), getEntityType().folderName), uuid)
        srcPath.copyTo(dstPath, true)
    }

    override fun fromGitDeleteInDb(uuid: String) {
    }
}