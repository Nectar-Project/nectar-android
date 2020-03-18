package com.realitix.mealassistant.util

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File


class GitUtil(private val repoDir: File, private val url: String, private val credentials: Credentials?) {
    class Credentials(val username: String, val password: String)

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
                .build()

            Git(repo)
        }

    }
}