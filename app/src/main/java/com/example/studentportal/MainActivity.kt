package com.example.studentportal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: экран создан")

        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val btnOpen = findViewById<Button>(R.id.btnOpen)
        val btnClear = findViewById<Button>(R.id.btnClear)

        // Как только пользователь начал печатать, убираем сообщение об ошибке
        etName.doOnTextChanged { _, _, _, _ -> tilName.error = null }

        btnOpen.setOnClickListener {
            val name = etName.text.toString().trim()

            if (name.isEmpty()) {
                Log.w(TAG, "Поле имени пустое, переход отменён")
                tilName.error = getString(R.string.error_empty_name)
                return@setOnClickListener
            }

            Log.i(TAG, "Нажата кнопка «Открыть список», имя: $name")

            // Явный Intent с передачей данных
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(ListActivity.EXTRA_NAME, name)
            startActivity(intent)
        }

        btnClear.setOnClickListener {
            Log.i(TAG, "Нажата кнопка «Очистить»")
            etName.text?.clear()
            tilName.error = null
        }
    }

    override fun onStart()   { super.onStart();   Log.d(TAG, "onStart") }
    override fun onResume()  { super.onResume();  Log.d(TAG, "onResume") }
    override fun onPause()   { super.onPause();   Log.d(TAG, "onPause") }
    override fun onStop()    { super.onStop();    Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}