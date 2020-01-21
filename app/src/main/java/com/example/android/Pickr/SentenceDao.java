package com.example.android.Pickr;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SentenceDao {
    @Query("SELECT * FROM sentence")
    LiveData<List<Sentence>> getAllSentences();
    @Query("SELECT * FROM sentence WHERE sid=:id")
    Sentence findSentenceById(String id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSentence(Sentence sentence);
    @Update
    void updateSentence(Sentence sentence);
    @Delete
    void deleteSentence(Sentence sentence);
    @Query("DELETE FROM sentence")
    void deleteAll();
}
