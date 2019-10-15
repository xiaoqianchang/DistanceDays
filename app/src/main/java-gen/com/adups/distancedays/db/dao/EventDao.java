package com.adups.distancedays.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.adups.distancedays.db.entity.EventEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "event".
*/
public class EventDao extends AbstractDao<EventEntity, Long> {

    public static final String TABLENAME = "event";

    /**
     * Properties of entity EventEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property EventTitle = new Property(1, String.class, "eventTitle", false, "EVENT_TITLE");
        public final static Property CreateDate = new Property(2, Long.class, "createDate", false, "CREATE_DATE");
        public final static Property TargetDate = new Property(3, Long.class, "targetDate", false, "TARGET_DATE");
        public final static Property IsLunarCalendar = new Property(4, Boolean.class, "isLunarCalendar", false, "IS_LUNAR_CALENDAR");
        public final static Property IsTop = new Property(5, Boolean.class, "isTop", false, "IS_TOP");
        public final static Property RepeatType = new Property(6, Integer.class, "repeatType", false, "REPEAT_TYPE");
    }


    public EventDao(DaoConfig config) {
        super(config);
    }
    
    public EventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"event\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"EVENT_TITLE\" TEXT," + // 1: eventTitle
                "\"CREATE_DATE\" INTEGER," + // 2: createDate
                "\"TARGET_DATE\" INTEGER," + // 3: targetDate
                "\"IS_LUNAR_CALENDAR\" INTEGER," + // 4: isLunarCalendar
                "\"IS_TOP\" INTEGER," + // 5: isTop
                "\"REPEAT_TYPE\" INTEGER);"); // 6: repeatType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"event\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EventEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String eventTitle = entity.getEventTitle();
        if (eventTitle != null) {
            stmt.bindString(2, eventTitle);
        }
 
        Long createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(3, createDate);
        }
 
        Long targetDate = entity.getTargetDate();
        if (targetDate != null) {
            stmt.bindLong(4, targetDate);
        }
 
        Boolean isLunarCalendar = entity.getIsLunarCalendar();
        if (isLunarCalendar != null) {
            stmt.bindLong(5, isLunarCalendar ? 1L: 0L);
        }
 
        Boolean isTop = entity.getIsTop();
        if (isTop != null) {
            stmt.bindLong(6, isTop ? 1L: 0L);
        }
 
        Integer repeatType = entity.getRepeatType();
        if (repeatType != null) {
            stmt.bindLong(7, repeatType);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EventEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String eventTitle = entity.getEventTitle();
        if (eventTitle != null) {
            stmt.bindString(2, eventTitle);
        }
 
        Long createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(3, createDate);
        }
 
        Long targetDate = entity.getTargetDate();
        if (targetDate != null) {
            stmt.bindLong(4, targetDate);
        }
 
        Boolean isLunarCalendar = entity.getIsLunarCalendar();
        if (isLunarCalendar != null) {
            stmt.bindLong(5, isLunarCalendar ? 1L: 0L);
        }
 
        Boolean isTop = entity.getIsTop();
        if (isTop != null) {
            stmt.bindLong(6, isTop ? 1L: 0L);
        }
 
        Integer repeatType = entity.getRepeatType();
        if (repeatType != null) {
            stmt.bindLong(7, repeatType);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public EventEntity readEntity(Cursor cursor, int offset) {
        EventEntity entity = new EventEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // eventTitle
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // createDate
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // targetDate
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0, // isLunarCalendar
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // isTop
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6) // repeatType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EventEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEventTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateDate(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setTargetDate(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setIsLunarCalendar(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
        entity.setIsTop(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setRepeatType(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(EventEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(EventEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EventEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
