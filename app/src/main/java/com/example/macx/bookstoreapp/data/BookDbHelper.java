package com.example.macx.bookstoreapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.macx.bookstoreapp.data.BookContract.*;

public class BookDbHelper extends SQLiteOpenHelper
{
	private static final String DATA_BASE_NAME = "bookStore.db";

	private static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_ENTRIES = new StringBuilder()
			.append("CREATE TABLE ").append(BookEntry.TABLE_NAME).append(" (")
			.append(BookEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append(BookEntry.COLUMN_BOOK_NAME).append(" TEXT NOT NULL, ")
			.append(BookEntry.COLUMN_BOOK_PRICE).append(" REAL NOT NULL, ")
			.append(BookEntry.COLUMN_BOOK_QUANTITY).append(" INTEGER NOT NULL DEFAULT 0, ")
			.append(BookEntry.COLUMN_BOOK_SUPPLIER_NAME).append(" TEXT NOT NULL, ")
			.append(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE).append(" INTEGER NOT NULL DEFAULT 0);").toString();

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

	public BookDbHelper(Context context)
	{
		super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase)
	{
		sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
	{
		sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
		onCreate(sqLiteDatabase);
	}
}
