package com.espaciounido.unadcalendar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.espaciounido.unadcalendar.model.Notification;
import com.espaciounido.unadcalendar.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Class C2DBOpenHelper.
 * 
 * @author Raul .A Alzate <raalzate@everis.com>
 * @since 26/11/2014
 */
public class C2DBOpenHelper {

	/** Constante DATABASE_NAME. */
	private static final String DATABASE_NAME = "calendarunadDB.db";

	/** Constante DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 2;

	/** Variable m c2 list database. */
	public static SQLiteDatabase mC2ListDatabase;

	/** Variable m db helper. */
	private C2DBHelper mDBHelper;

	/** Variable m context. */
	private Context mContext;

	/**
	 * Class C2DBHelper.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @since 26/11/2014
	 */
	private class C2DBHelper extends SQLiteOpenHelper {

		/**
		 * Instancia de clase C2DBHelper. Metodo constructor.
		 * 
		 * @author Raul .A Alzate <raalzate@everis.com>
		 * @param context
		 *            para context
		 * @param name
		 *            para name
		 * @param factory
		 *            para factory
		 * @param version
		 *            para version
		 * @since 26/11/2014
		 */
		public C2DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		/**
		 * Instancia de clase C2DBHelper. Metodo constructor.
		 * 
		 * @author Raul .A Alzate <raalzate@everis.com>
		 * @param context
		 *            para context
		 * @since 26/11/2014
		 */
		public C2DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database
		 * .sqlite.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
            db.execSQL(CalendarUNADDB.MODELSERIALIZABLE._CREATE);
            db.execSQL(CalendarUNADDB.NOTIFICATION._CREATE);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database
		 * .sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                if(newVersion == 1)
                db.execSQL("DROP TABLE IF EXISTS "
                        + CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE);

                if(newVersion == 2)
                db.execSQL("DROP TABLE IF EXISTS "
                        + CalendarUNADDB.NOTIFICATION.C2_TABLE);

                onCreate(db);


		}
	}

	/**
	 * Instancia de clase C2DBOpenHelper. Metodo constructor.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param context
	 *            para context
	 * @since 26/11/2014
	 */
	public C2DBOpenHelper(Context context) {
		mContext = context;
	}

	/**
	 * Open.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @return c2 db open helper
	 * @throws SQLException
	 *             de SQL exception
	 * @since 26/11/2014
	 */
	public C2DBOpenHelper open() throws SQLException {
            if(mC2ListDatabase != null) {
                mC2ListDatabase.close();
            }
            mDBHelper = new C2DBHelper(mContext, DATABASE_NAME, null,
                    DATABASE_VERSION);

            mC2ListDatabase = mDBHelper.getWritableDatabase();

		return this;
	}

	// Insert the title.
	/**
	 * Insert column.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param table
	 *            para table
	 * @param colum
	 *            para colum
	 * @param item
	 *            para item
	 * @return long
	 * @since 26/11/2014
	 */
	public long insertModelSerializable(String key, String model) {

        if(existModelByKey(key)){
            return 1;
       }

		ContentValues values = new ContentValues();
		values.put(CalendarUNADDB.MODELSERIALIZABLE.C2_KEY, key);
		values.put(CalendarUNADDB.MODELSERIALIZABLE.C2_MODEL, model);
		values.put(CalendarUNADDB.MODELSERIALIZABLE.C2_DATE, Utils.getDataNow());
		return mC2ListDatabase.insert(CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE,
				null, values);
	}

    /**
     * Funcion para insertar las notificaciones
     *
     * @param contentType
     * @param type
     * @param body
     * @param title
     * @return
     */
    public long insertNotification(String contentType,
                                   String type,
                                   String body,
                                   String title) {

        ContentValues values = new ContentValues();
        values.put(CalendarUNADDB.NOTIFICATION.C2_CONTENT_TYPE, contentType);
        values.put(CalendarUNADDB.NOTIFICATION.C2_TYPE, type);
        values.put(CalendarUNADDB.NOTIFICATION.C2_BODY, body);
        values.put(CalendarUNADDB.NOTIFICATION.C2_TITLE, title);
        values.put(CalendarUNADDB.NOTIFICATION.C2_STATUS, Notification.STATUS_UNREAD);
        values.put(CalendarUNADDB.NOTIFICATION.C2_DATE, Utils.getDataNow());
        return mC2ListDatabase.insert(CalendarUNADDB.NOTIFICATION.C2_TABLE,
                null, values);
    }

	/**
	 * Update column.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param table
	 *            para table
	 * @param id
	 *            para id
	 * @param colum
	 *            para colum
	 * @param item
	 *            para item
	 * @return true, si todo esta correcto
	 * @since 26/11/2014
	 */
	public boolean updateColumn(String table, String id, String colum,
			String item) {
		ContentValues values = new ContentValues();
		values.put(colum, item);
		return mC2ListDatabase.update(table, values, "_id=" + id, null) > 0;
	}

	/**
	 * Delete column.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param table
	 *            para table
	 * @param id
	 *            para id
	 * @return true, si todo esta correcto
	 * @since 26/11/2014
	 */
	public boolean deleteColumn(String table, String id) {
		return mC2ListDatabase.delete(table, "_id=" + id, null) > 0;
	}

	/**
	 * Metodo Get para all columns.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param table
	 *            para table
	 * @return Cursor
	 * @since 26/11/2014
	 */
	public Cursor getAllColumns(String table) {
		return mC2ListDatabase.query(table, null, null, null, null, null, null);
	}

	/**
	 * Metodo Get para all datas.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param table
	 *            para table
	 * @return HashMap<String,String>
	 * @since 26/11/2014
	 */
	public List<HashMap<String, String>> getAllModelSerializable() {
		HashMap<String, String> element;
		List<HashMap<String, String>> datas = new ArrayList();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE;

		mC2ListDatabase = mDBHelper.getReadableDatabase();
		Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				element = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					element.put(cursor.getColumnName(i), cursor.getString(i));
				}
				datas.add(element);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		mC2ListDatabase.close();

		return datas;
	}


    /**
     * Esta funcion se encarga de retornar todos los valores
     * del la tabla notificaciones
     *
     * @return List<HashMap<String, String>>
     */
    public int hastNotification() {

        // Select All Query
        String selectQuery = "SELECT * FROM " + CalendarUNADDB.NOTIFICATION.C2_TABLE + " " +
                "WHERE "+CalendarUNADDB.NOTIFICATION.C2_STATUS+" = '"+Notification.STATUS_UNREAD+"'" +
                "ORDER BY _id DESC";

        mC2ListDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

	/**
	 * Esta funcion se encarga de retornar todos los valores
	 * del la tabla notificaciones
	 *
	 * @return List<HashMap<String, String>>
	 */
	public List<HashMap<String, String>> getAllNotificationBySala(String eventId) {
		HashMap<String, String> element;
		List<HashMap<String, String>> datas = new ArrayList();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + CalendarUNADDB.NOTIFICATION.C2_TABLE + " " +
				"WHERE "+ CalendarUNADDB.NOTIFICATION.C2_TYPE + " = '"+Notification.TYPE_EVENTROOM+eventId + "'"+
				" ORDER BY _id DESC";

		mC2ListDatabase = mDBHelper.getReadableDatabase();
		Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				element = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					element.put(cursor.getColumnName(i), cursor.getString(i));
				}
				datas.add(element);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		mC2ListDatabase.close();

		return datas;
	}

    /**
     * Esta funcion se encarga de retornar todos los valores
     * del la tabla notificaciones
     *
     * @return List<HashMap<String, String>>
     */
    public List<HashMap<String, String>> getAllNotification() {
        HashMap<String, String> element;
        List<HashMap<String, String>> datas = new ArrayList();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CalendarUNADDB.NOTIFICATION.C2_TABLE + " " +
                "ORDER BY _id DESC";

        mC2ListDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                element = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    element.put(cursor.getColumnName(i), cursor.getString(i));
                }
                datas.add(element);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        mC2ListDatabase.close();

        return datas;
    }



	public String getModelByKey(String value) {
		// Select All Query
		String selectQuery = "SELECT  * FROM " + CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE
				+ " WHERE " + CalendarUNADDB.MODELSERIALIZABLE.C2_KEY + " = '" + value + "'";

		mC2ListDatabase = mDBHelper.getReadableDatabase();
		Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (CalendarUNADDB.MODELSERIALIZABLE.C2_MODEL.equals(cursor.getColumnName(i))) {
						return cursor.getString(i);
					}
				}
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		mC2ListDatabase.close();
		return null;
	}
	
	public boolean deleteModelByKey(String value) {
        return mC2ListDatabase.delete(CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE,
                CalendarUNADDB.MODELSERIALIZABLE.C2_KEY+"=\"" + value+"\"", null) > 0;

	}


    public boolean existModel(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE;

        mC2ListDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);
        boolean  exist = cursor.getCount() > 0;

        cursor.close();

        return exist;
    }

    public boolean existModelByKey(String value) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + CalendarUNADDB.MODELSERIALIZABLE.C2_TABLE
                + " WHERE " + CalendarUNADDB.MODELSERIALIZABLE.C2_KEY + " = '" + value + "'";

        mC2ListDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mC2ListDatabase.rawQuery(selectQuery, null);
        boolean  exist = cursor.getCount() > 0;

        cursor.close();

        return exist;
    }

	/**
	 * clear all data.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @param table
	 *            para table
	 * @since 26/11/2014
	 */
	public C2DBOpenHelper clearData(String table) {
		mC2ListDatabase = mDBHelper.getReadableDatabase();
		mC2ListDatabase.delete(table, null, null);
		return this;
	}

	/**
	 * Close.
	 * 
	 * @author Raul .A Alzate <raalzate@everis.com>
	 * @since 26/11/2014
	 */
	public void close() {
		mC2ListDatabase.close();
	}
}
