package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.BookImageRepository
import com.realitix.nectar.repository.BookReceipeRepository
import com.realitix.nectar.repository.BookRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class BookSynchronizer(
    private val rBook: BookRepository,
    private val rBookImage: BookImageRepository,
    private val rBookReceipe: BookReceipeRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<BookSynchronizer.ParseResult>(baseRepositoryFolder) {
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

    override fun updateDb(parseResult: ParseResult) {
        // Create book only if not exists
        if(rBook.get(parseResult.uuid) == null) {
            rBook.insert(BookRaw(parseResult.uuid, parseResult.nameUuid, parseResult.author, parseResult.publishDate))
        }

        // images
        for(imageUuid in parseResult.images) {
            rBookImage.insert(BookImageRaw(parseResult.uuid, imageUuid))
        }

        // receipes
        for(receipeUuid in parseResult.receipes) {
            rBookReceipe.insert(BookReceipeRaw(parseResult.uuid, receipeUuid))
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val book = rBook.get(uuid)!!

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