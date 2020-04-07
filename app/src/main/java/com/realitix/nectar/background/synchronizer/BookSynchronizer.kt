package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.BookRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class BookSynchronizer(repository: BookRepository, baseRepositoryFolder: File):
    BaseSynchronizer<BookSynchronizer.ParseResult, BookRepository>(repository, baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String,
        val images: List<String>,
        val receipes: List<String>,
        val author: String,
        val publishDate: Long
    )

    override fun getEntityType(): EntityType = EntityType.BOOK
    override fun getParseResult(repositoryName: String, uuid: String): ParseResult = getInnerParseResult(repositoryName, uuid)

    override fun updateDb(repo: BookRepository, parseResult: ParseResult) {
        // Create book only if not exists
        if(repo.getBook(parseResult.uuid) == null) {
            repo.insertBook(BookRaw(parseResult.uuid, parseResult.nameUuid, parseResult.author, parseResult.publishDate))
        }

        // images
        for(imageUuid in parseResult.images) {
            repo.insertBookImage(BookImageRaw(parseResult.uuid, imageUuid))
        }

        // receipes
        for(receipeUuid in parseResult.receipes) {
            repo.insertBookReceipe(BookReceipeRaw(parseResult.uuid, receipeUuid))
        }
    }

    override fun populateParseResult(repo: BookRepository, uuid: String): ParseResult {
        val book = repo.getBook(uuid)!!

        val images = mutableListOf<String>()
        for(a in book.images) {
            images.add(a.imageUuid)
        }

        val receipes = mutableListOf<String>()
        for(a in book.receipes) {
            receipes.add(a.receipeUuid)
        }

        return ParseResult(book.uuid, book.nameUuid, images, receipes, book.author, book.publishDate)
    }
}