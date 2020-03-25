package com.realitix.mealassistant.work.parser

import android.content.Context

interface ParserInterface {
    fun update(context: Context, repositoryName: String, uuid: String)
}