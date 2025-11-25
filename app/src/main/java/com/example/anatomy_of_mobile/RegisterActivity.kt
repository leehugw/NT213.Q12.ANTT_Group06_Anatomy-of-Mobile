package com.example.anatomy_of_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = DatabaseHelper(this)
        val user = findViewById<EditText>(R.id.etUsernameRegister)
        val pass = findViewById<EditText>(R.id.etPasswordRegister)
        val repass = findViewById<EditText>(R.id.etRePasswordRegister)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = user.text.toString()
            val password = pass.text.toString()
            val repassword = repass.text.toString()

            if (username.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                if (password == repassword) {
                    if (!db.checkUsername(username)) {
                        val insert = db.insertUser(username, password)
                        if (insert) {
                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            finish() // Quay lại màn hình Login
                        } else {
                            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}