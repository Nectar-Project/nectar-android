package com.realitix.nectar.util

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object ZipUtil {
    private const val BUFFER_SIZE = 1024 * 10

    fun unzipFromAssets(
        context: Context,
        zipFile: String,
        destination: String
    ) {
        val stream: InputStream = context.assets.open(zipFile)
        unzip(stream, destination)
    }

    fun unzip(zipFile: String, location: String) {
        val fin = FileInputStream(zipFile)
        unzip(fin, location)
    }

    private fun unzip(stream: InputStream, destination: String) {
        dirChecker(destination, "")
        val buffer = ByteArray(BUFFER_SIZE)
        val zin = ZipInputStream(stream)
        var ze: ZipEntry?
        while (zin.nextEntry.also { ze = it } != null) {
            if (ze!!.isDirectory) {
                dirChecker(destination, ze!!.name)
            } else {
                val f = File(destination, ze!!.name)
                if (!f.exists()) {
                    val success = f.createNewFile()
                    if (!success) {
                        continue
                    }
                    val fout = FileOutputStream(f)
                    var count: Int
                    while (zin.read(buffer).also { count = it } != -1) {
                        fout.write(buffer, 0, count)
                    }
                    zin.closeEntry()
                    fout.close()
                }
            }
        }
        zin.close()
    }

    private fun dirChecker(destination: String, dir: String) {
        val f = File(destination, dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }
}