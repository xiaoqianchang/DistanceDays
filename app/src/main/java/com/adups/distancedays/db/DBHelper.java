package com.adups.distancedays.db;

import android.content.Context;

import com.adups.distancedays.db.dao.DaoMaster;
import com.adups.distancedays.db.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * DevOpenHelper helper = new DevOpenHelper(this, "notes-db");
 * Database db = helper.getWritableDb();
 * daoSession = new DaoMaster(db).newSession();
 *
 * 参考：
 * http://greenrobot.org/greendao/documentation/how-to-get-started/#Extending_and_adding_entities
 * <p>
 * Created by Chang.Xiao on 2017/9/15.
 *
 * @version 1.0
 */
public class DBHelper extends DaoMaster.OpenHelper {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    private static DBHelper mInstance;

    private Database mDb;
    private DaoSession mDaoSession;

    private DBHelper(Context context, String name) {
        super(context, name);
        mDb = ENCRYPTED ? getEncryptedReadableDb("super-secret") : getWritableDb();
        mDaoSession = new DaoMaster(mDb).newSession();
    }

    public static DBHelper getInstance(Context context) {
        if (null == mInstance) {
            synchronized (DBHelper.class) {
                if (null == mInstance) {
                    mInstance = new DBHelper(context.getApplicationContext(), getDbName());
                }
            }
        }
        return mInstance;
    }

    public static String getDbName() {
        return ENCRYPTED ? DBConfig.DB_NAME_ENCRYPTED : DBConfig.DB_NAME;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
    }

    public void dropAllTables() {
        DaoMaster.dropAllTables(mDb, true);
    }
}
