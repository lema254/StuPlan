

package com.example.stuplan.utils
import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {
    fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open URI: $uri")
        val file = File.createTempFile("upload", ".jpg", context.cacheDir)
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return file
    }
}
