package com.example.demo.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    fun insert(note:Note)

    @Update
    fun update(note: Note)

//    @Query("select * from note_table order by priority desc")
//    fun getAllNotes(): LiveData<List<Note>>
    @Query("select * from note_table")
    fun getNotes(): List<Note>
}