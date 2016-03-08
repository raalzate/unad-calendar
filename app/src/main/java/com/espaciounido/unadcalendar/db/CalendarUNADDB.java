package com.espaciounido.unadcalendar.db;

import android.provider.BaseColumns;

public class CalendarUNADDB {

	public CalendarUNADDB() {
	}

	public static final class MODELSERIALIZABLE implements BaseColumns {
		
		public static final String C2_TABLE = "c2_modelserializable";
		public static final String C2_MODEL = "c2_model";
		public static final String C2_KEY = "c2_key";
		public static final String C2_DATE = "c2_datetime";


		public static final String _CREATE =
                "CREATE TABLE IF NOT EXISTS " + MODELSERIALIZABLE.C2_TABLE + " ("
				+ MODELSERIALIZABLE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ MODELSERIALIZABLE.C2_MODEL + " TEXT ,"
				+ MODELSERIALIZABLE.C2_DATE + " TEXT ,"
				+ MODELSERIALIZABLE.C2_KEY + " TEXT " + ");";
		
		@Override
		public String toString() {
			return C2_TABLE;
		}
	}

    public static final class NOTIFICATION implements BaseColumns {

        public static final String C2_TABLE = "c2_notification";
        public static final String C2_TITLE = "c2_title";
        public static final String C2_BODY = "c2_body";
        public static final String C2_DATE = "c2_date";
        public static final String C2_TYPE = "c2_type";
        public static final String C2_STATUS = "c2_status";
        public static final String C2_CONTENT_TYPE = "c2_content_type";


        public static final String _CREATE =
                "CREATE TABLE IF NOT EXISTS " + NOTIFICATION.C2_TABLE + " ("
                        + NOTIFICATION._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                        + NOTIFICATION.C2_TITLE + " TEXT ,"
                        + NOTIFICATION.C2_DATE + " TEXT ,"
                        + NOTIFICATION.C2_TYPE + " TEXT ,"
                        + NOTIFICATION.C2_STATUS + " TEXT ,"
                        + NOTIFICATION.C2_CONTENT_TYPE + " TEXT ,"
                        + NOTIFICATION.C2_BODY + " TEXT " + ");";

        @Override
        public String toString() {
            return C2_TABLE;
        }
    }
}
