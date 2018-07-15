package com.example.macx.bookstoreapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.macx.bookstoreapp.data.BookContract.*;
import com.example.macx.bookstoreapp.data.BookDbHelper;

public class MainActivity extends AppCompatActivity
{

	TextView text;
	private BookDbHelper bookDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bookDbHelper = new BookDbHelper(this);

		insert("Dark Knight", 35.99, 50, "Booker", "789-345-123");
		insert("All about fruits", 27.99, 25, "Booker", "789-345-123");
		insert("Blue Dragon", 34.99, 50, "White Raven", "976-543-343");

		displayDatabase();
	}

	private void displayDatabase()
	{
		SQLiteDatabase db = bookDbHelper.getReadableDatabase();

		String[] projection = {
				BaseColumns._ID,
				BookEntry.COLUMN_BOOK_NAME,
				BookEntry.COLUMN_BOOK_PRICE,
				BookEntry.COLUMN_BOOK_QUANTITY
		};

		Cursor cursor = db.query(BookEntry.TABLE_NAME,
				projection,
				null,
				null,
				null,
				null,
				null);

		try
		{
			TextView displayView = (TextView) findViewById(R.id.main_activity_text_view);
			displayView.setText(getString(R.string.number_of_rows_text, cursor.getCount()));

			displayView.append(BookEntry._ID + "\t\t" +
					BookEntry.COLUMN_BOOK_NAME + "\t\t" +
					BookEntry.COLUMN_BOOK_PRICE + "\t\t" +
					BookEntry.COLUMN_BOOK_QUANTITY + "\n");

			int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
			int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
			int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
			int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

			while (cursor.moveToNext()){
				int currentId = cursor.getInt(idColumnIndex);
				String currentName = cursor.getString(nameColumnIndex);
				String currentPrice = cursor.getString(priceColumnIndex);
				String currentQuantity = cursor.getString(quantityColumnIndex);
				displayView.append(("\n" +
						currentId + "\t\t" +
						currentName + "\t\t" +
						currentPrice + "\t\t" +
						currentQuantity));
			}

		} finally
		{
			cursor.close();
		}
	}

	private void insert(String name, double price, int quantity, String supplierName, String supplierPhone)
	{
		SQLiteDatabase db = bookDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BookEntry.COLUMN_BOOK_NAME, name);
		values.put(BookEntry.COLUMN_BOOK_PRICE, price);
		values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
		values.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, supplierName);
		values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, supplierPhone);

		db.insert(BookEntry.TABLE_NAME, null, values);
	}
}
