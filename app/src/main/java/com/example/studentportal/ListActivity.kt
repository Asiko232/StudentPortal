package com.example.studentportal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

class ListActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ListActivity"
        const val EXTRA_NAME = "EXTRA_NAME"
    }

    private lateinit var adapter: StudentAdapter
    private lateinit var tvStats: TextView

    private var query = ""            // текст поиска
    private var sortByGrade = false   // false: по алфавиту, true: по среднему баллу

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Принимаем данные с главного экрана
        val userName = intent.getStringExtra(EXTRA_NAME) ?: "—"
        Log.d(TAG, "onCreate: получено имя $userName")
        findViewById<TextView>(R.id.tvGreeting).text =
            getString(R.string.list_greeting, userName)

        tvStats = findViewById(R.id.tvStats)
        val etSearch = findViewById<TextInputEditText>(R.id.etSearch)
        val btnSort = findViewById<Button>(R.id.btnSort)
        val listView = findViewById<ListView>(R.id.lvStudents)

        adapter = StudentAdapter(this, mutableListOf())
        listView.adapter = adapter
        // Если список пуст, показывается надпись «Ничего не найдено»
        listView.emptyView = findViewById<TextView>(R.id.tvEmpty)
        refresh()

        etSearch.doOnTextChanged { text, _, _, _ ->
            query = text?.toString().orEmpty().trim()
            Log.d(TAG, "Поиск: \"$query\"")
            refresh()
        }

        btnSort.setOnClickListener {
            sortByGrade = !sortByGrade
            btnSort.setText(if (sortByGrade) R.string.sort_by_name else R.string.sort_by_grade)
            Log.i(TAG, "Сортировка изменена, по баллу: $sortByGrade")
            refresh()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val student = adapter.getItem(position) ?: return@setOnItemClickListener
            Log.i(TAG, "Выбран студент: ${student.name}, средний балл ${formatAverage(student.average)}")

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_STUDENT, student.name)
            intent.putExtra(DetailActivity.EXTRA_GRADES, student.grades.toIntArray())
            startActivity(intent)
        }
    }

    // Фильтрует и сортирует список, обновляет статистику
    private fun refresh() {
        var list = GroupData.students.filter { it.name.contains(query, ignoreCase = true) }
        list = if (sortByGrade) list.sortedByDescending { it.average } else list.sortedBy { it.name }

        adapter.clear()
        adapter.addAll(list)

        if (list.isEmpty()) {
            tvStats.text = ""
        } else {
            val groupAverage = list.map { it.average }.average()
            tvStats.text = getString(R.string.stats_format, list.size, formatAverage(groupAverage))
        }
    }

    override fun onStart()   { super.onStart();   Log.d(TAG, "onStart") }
    override fun onResume()  { super.onResume();  Log.d(TAG, "onResume") }
    override fun onPause()   { super.onPause();   Log.d(TAG, "onPause") }
    override fun onStop()    { super.onStop();    Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}