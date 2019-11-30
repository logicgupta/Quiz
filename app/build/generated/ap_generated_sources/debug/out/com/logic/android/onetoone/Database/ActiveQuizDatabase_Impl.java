package com.logic.android.onetoone.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class ActiveQuizDatabase_Impl extends ActiveQuizDatabase {
  private volatile ActiveQuizDao _activeQuizDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `quiz1` (`id` TEXT NOT NULL, `name` TEXT, `coins` TEXT, `desc` TEXT, `duration` TEXT, `imageUrl` TEXT, `date` TEXT, `startTime` TEXT, `enrollFee` INTEGER NOT NULL, `maxUsers` INTEGER NOT NULL, `noOfRegistered` INTEGER NOT NULL, `open` INTEGER, `percentWinners` INTEGER NOT NULL, `questionList` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"cf8ce0e5d5ba17eba1ac089615d89aa7\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `quiz1`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsQuiz1 = new HashMap<String, TableInfo.Column>(14);
        _columnsQuiz1.put("id", new TableInfo.Column("id", "TEXT", true, 1));
        _columnsQuiz1.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsQuiz1.put("coins", new TableInfo.Column("coins", "TEXT", false, 0));
        _columnsQuiz1.put("desc", new TableInfo.Column("desc", "TEXT", false, 0));
        _columnsQuiz1.put("duration", new TableInfo.Column("duration", "TEXT", false, 0));
        _columnsQuiz1.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0));
        _columnsQuiz1.put("date", new TableInfo.Column("date", "TEXT", false, 0));
        _columnsQuiz1.put("startTime", new TableInfo.Column("startTime", "TEXT", false, 0));
        _columnsQuiz1.put("enrollFee", new TableInfo.Column("enrollFee", "INTEGER", true, 0));
        _columnsQuiz1.put("maxUsers", new TableInfo.Column("maxUsers", "INTEGER", true, 0));
        _columnsQuiz1.put("noOfRegistered", new TableInfo.Column("noOfRegistered", "INTEGER", true, 0));
        _columnsQuiz1.put("open", new TableInfo.Column("open", "INTEGER", false, 0));
        _columnsQuiz1.put("percentWinners", new TableInfo.Column("percentWinners", "INTEGER", true, 0));
        _columnsQuiz1.put("questionList", new TableInfo.Column("questionList", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuiz1 = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuiz1 = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuiz1 = new TableInfo("quiz1", _columnsQuiz1, _foreignKeysQuiz1, _indicesQuiz1);
        final TableInfo _existingQuiz1 = TableInfo.read(_db, "quiz1");
        if (! _infoQuiz1.equals(_existingQuiz1)) {
          throw new IllegalStateException("Migration didn't properly handle quiz1(com.logic.android.onetoone.Database.ActiveQuizEntity).\n"
                  + " Expected:\n" + _infoQuiz1 + "\n"
                  + " Found:\n" + _existingQuiz1);
        }
      }
    }, "cf8ce0e5d5ba17eba1ac089615d89aa7", "61110451c851a8d59f426026e1bc964b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "quiz1");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `quiz1`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ActiveQuizDao activeQuizDao() {
    if (_activeQuizDao != null) {
      return _activeQuizDao;
    } else {
      synchronized(this) {
        if(_activeQuizDao == null) {
          _activeQuizDao = new ActiveQuizDao_Impl(this);
        }
        return _activeQuizDao;
      }
    }
  }
}
