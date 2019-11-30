package com.logic.android.onetoone.Database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.logic.android.onetoone.models.Questions;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class ActiveQuizDao_Impl implements ActiveQuizDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfActiveQuizEntity;

  private final DataConvertor __dataConvertor = new DataConvertor();

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfActiveQuizEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeletetable;

  public ActiveQuizDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfActiveQuizEntity = new EntityInsertionAdapter<ActiveQuizEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `quiz1`(`id`,`name`,`coins`,`desc`,`duration`,`imageUrl`,`date`,`startTime`,`enrollFee`,`maxUsers`,`noOfRegistered`,`open`,`percentWinners`,`questionList`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ActiveQuizEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCoins() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCoins());
        }
        if (value.getDesc() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDesc());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDuration());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getImageUrl());
        }
        if (value.getDate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDate());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getStartTime());
        }
        stmt.bindLong(9, value.getEnrollFee());
        stmt.bindLong(10, value.getMaxUsers());
        stmt.bindLong(11, value.getNoOfRegistered());
        final Integer _tmp;
        _tmp = value.getOpen() == null ? null : (value.getOpen() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp);
        }
        stmt.bindLong(13, value.getPercentWinners());
        final String _tmp_1;
        _tmp_1 = __dataConvertor.fromQuizValues(value.getQuestionsList());
        if (_tmp_1 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfActiveQuizEntity = new EntityDeletionOrUpdateAdapter<ActiveQuizEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `quiz1` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ActiveQuizEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeletetable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from quiz1";
        return _query;
      }
    };
  }

  @Override
  public void insert(ActiveQuizEntity... entities) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfActiveQuizEntity.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(ActiveQuizEntity... entities) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfActiveQuizEntity.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletetable() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletetable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletetable.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ActiveQuizEntity>> getAllQuiz() {
    final String _sql = "Select * From quiz1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<ActiveQuizEntity>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<ActiveQuizEntity> compute() {
        if (_observer == null) {
          _observer = new Observer("quiz1") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfCoins = _cursor.getColumnIndexOrThrow("coins");
          final int _cursorIndexOfDesc = _cursor.getColumnIndexOrThrow("desc");
          final int _cursorIndexOfDuration = _cursor.getColumnIndexOrThrow("duration");
          final int _cursorIndexOfImageUrl = _cursor.getColumnIndexOrThrow("imageUrl");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfStartTime = _cursor.getColumnIndexOrThrow("startTime");
          final int _cursorIndexOfEnrollFee = _cursor.getColumnIndexOrThrow("enrollFee");
          final int _cursorIndexOfMaxUsers = _cursor.getColumnIndexOrThrow("maxUsers");
          final int _cursorIndexOfNoOfRegistered = _cursor.getColumnIndexOrThrow("noOfRegistered");
          final int _cursorIndexOfOpen = _cursor.getColumnIndexOrThrow("open");
          final int _cursorIndexOfPercentWinners = _cursor.getColumnIndexOrThrow("percentWinners");
          final int _cursorIndexOfQuestionsList = _cursor.getColumnIndexOrThrow("questionList");
          final List<ActiveQuizEntity> _result = new ArrayList<ActiveQuizEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ActiveQuizEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCoins;
            _tmpCoins = _cursor.getString(_cursorIndexOfCoins);
            final String _tmpDesc;
            _tmpDesc = _cursor.getString(_cursorIndexOfDesc);
            final String _tmpDuration;
            _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final int _tmpEnrollFee;
            _tmpEnrollFee = _cursor.getInt(_cursorIndexOfEnrollFee);
            final int _tmpMaxUsers;
            _tmpMaxUsers = _cursor.getInt(_cursorIndexOfMaxUsers);
            final int _tmpNoOfRegistered;
            _tmpNoOfRegistered = _cursor.getInt(_cursorIndexOfNoOfRegistered);
            final Boolean _tmpOpen;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfOpen)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfOpen);
            }
            _tmpOpen = _tmp == null ? null : _tmp != 0;
            final int _tmpPercentWinners;
            _tmpPercentWinners = _cursor.getInt(_cursorIndexOfPercentWinners);
            final List<Questions> _tmpQuestionsList;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfQuestionsList);
            _tmpQuestionsList = __dataConvertor.toValuesList(_tmp_1);
            _item = new ActiveQuizEntity(_tmpId,_tmpName,_tmpCoins,_tmpDesc,_tmpDuration,_tmpImageUrl,_tmpStartTime,_tmpDate,_tmpEnrollFee,_tmpMaxUsers,_tmpNoOfRegistered,_tmpOpen,_tmpPercentWinners,_tmpQuestionsList);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
