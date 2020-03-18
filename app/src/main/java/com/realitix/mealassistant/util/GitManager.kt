package com.realitix.mealassistant.util

import com.realitix.mealassistant.database.entity.GitCredentials
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File


class GitManager(private val repoDir: File, private val url: String, private val credentials: GitCredentials?) {
    private val git: Git by lazy {
        if(!repoDir.exists()) {
            val git = Git.cloneRepository()
                .setURI(url)
                .setDirectory(repoDir)

            if( credentials != null ) {
                git.setCredentialsProvider(UsernamePasswordCredentialsProvider(credentials.username, credentials.password))
            }

            git.call()
        }
        else {
            val builder = FileRepositoryBuilder()

            val repo = builder.setGitDir(File(repoDir, ".git"))
                .readEnvironment()
                .findGitDir()
                .setMustExist(true)
                .build()

            Git(repo)
        }

    }
}