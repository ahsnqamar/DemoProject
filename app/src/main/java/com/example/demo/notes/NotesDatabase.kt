package com.example.demo.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NotesDatabase?= null

        @Synchronized
        fun getInstance(ctx: Context) : NotesDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(ctx.applicationContext, NotesDatabase::class.java,
                "note_database")
                     .build()
            }

            return  instance!!
        }


    }





}