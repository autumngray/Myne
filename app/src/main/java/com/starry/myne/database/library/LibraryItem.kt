/*
Copyright 2022 - 2023 Stɑrry Shivɑm

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.starry.myne.database.library

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.io.IOException
import java.text.CharacterIterator
import java.text.DateFormat
import java.text.StringCharacterIterator
import java.util.Date
import java.util.Locale

@Entity(tableName = "book_library")
data class LibraryItem(
    @ColumnInfo(name = "book_id")
    val bookId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "authors")
    val authors: String,
    @ColumnInfo(name = "file_path")
    val filePath: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun fileExist(): Boolean {
        val file = File(filePath)
        return file.exists()
    }

    fun getFileSize(): String {
        val file = File(filePath)
        var bytes = file.length()
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return java.lang.String.format(Locale.US, "%.1f %cB", bytes / 1000.0, ci.current())
    }

    fun getDownloadDate(): String {
        val date = Date(createdAt)
        return DateFormat.getDateInstance().format(date)
    }

    fun deleteFile(): Boolean {
        val file = File(filePath)
        return try {
            file.delete()
        } catch (exc: IOException) {
            false
        }
    }
}