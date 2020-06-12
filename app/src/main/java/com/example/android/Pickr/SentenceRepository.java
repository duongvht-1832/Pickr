package com.example.android.Pickr;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


class SentenceRepository {

    private SentenceDao mSentenceDao;
    private LiveData<List<Sentence>> mAllSentences;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    SentenceRepository(Application application) {
        SentenceRoomDatabase db = SentenceRoomDatabase.getDatabase(application);
        mSentenceDao = db.sentenceDao();
        mAllSentences = mSentenceDao.getAllSentences();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Sentence>> getAllSentences() {
        return mAllSentences;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Sentence sentence) {
        SentenceRoomDatabase.databaseWriteExecutor.execute(() -> {
            mSentenceDao.insertSentence(sentence);
        });
    }

    void deleteAll() {
        SentenceRoomDatabase.databaseWriteExecutor.execute(() -> {
            mSentenceDao.deleteAll();
        });
    }
}