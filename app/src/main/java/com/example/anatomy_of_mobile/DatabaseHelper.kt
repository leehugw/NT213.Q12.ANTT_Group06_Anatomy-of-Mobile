package com.example.anatomy_of_mobile

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "UserDB.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table users(username TEXT primary key, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists users")
        onCreate(db)
    }

    // Hàm thêm user (Đăng ký)
    fun insertUser(username: String, pass: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", pass)
        val result = db.insert("users", null, contentValues)
        return result != -1L
    }

    // Hàm kiểm tra user (Đăng nhập)
    fun checkUser(username: String, pass: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from users where username = ? and password = ?", arrayOf(username, pass))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Kiểm tra xem username đã tồn tại chưa (để tránh đăng ký trùng)
    fun checkUsername(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from users where username = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}