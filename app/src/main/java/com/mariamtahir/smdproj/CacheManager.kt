package com.mariamtahir.smdproj

import android.content.Context
import java.io.File

class CacheManager {
    companion object {
        fun clearCache(context: Context) {
            val cacheDir: File? = context.cacheDir
            val cacheFiles: Array<File>? = cacheDir?.listFiles()
            cacheFiles?.forEach { it.delete() }
        }
    }
}
