package com.example.studentportal

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_STUDENT = "EXTRA_STUDENT"
        const val EXTRA_GRADES = "EXTRA_GRADES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Принимаем данные из списка
        val name = intent.getStringExtra(EXTRA_STUDENT) ?: "—"
        val grades = intent.getIntArrayExtra(EXTRA_GRADES) ?: IntArray(0)
        Log.d(TAG, "onCreate: получены данные: $name, оценки ${grades.toList()}")

        val student = Student(name, grades.toList())
        val average = student.average
        val averageColor = ContextCompat.getColor(this, gradeColorRes(average))

        findViewById<TextView>(R.id.tvAvatar).text = student.initials
        findViewById<TextView>(R.id.tvStudentName).text = name
        findViewById<TextView>(R.id.tvAverage).text =
            getString(R.string.average_format, formatAverage(average))

        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        tvStatus.setText(gradeLabelRes(average))
        tvStatus.setTextColor(averageColor)

        // Для каждого предмета создаём строку «название, оценка, полоска»
        val subjects = resources.getStringArray(R.array.subjects)
        val container = findViewById<LinearLayout>(R.id.gradesContainer)
        grades.forEachIndexed { index, grade ->
            val row = layoutInflater.inflate(R.layout.item_grade, container, false)
            val color = ContextCompat.getColor(this, gradeColorRes(grade.toDouble()))

            row.findViewById<TextView>(R.id.tvSubject).text = subjects.getOrElse(index) { "—" }

            val tvGradeValue = row.findViewById<TextView>(R.id.tvGradeValue)
            tvGradeValue.text = grade.toString()
            tvGradeValue.setTextColor(color)

            val progress = row.findViewById<ProgressBar>(R.id.pbGrade)
            progress.progress = grade
            progress.progressTintList = ColorStateList.valueOf(color)

            container.addView(row)
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            Log.i(TAG, "Нажата кнопка «Назад»")
            finish()
        }
    }

    override fun onStart()   { super.onStart();   Log.d(TAG, "onStart") }
    override fun onResume()  { super.onResume();  Log.d(TAG, "onResume") }
    override fun onPause()   { super.onPause();   Log.d(TAG, "onPause") }
    override fun onStop()    { super.onStop();    Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}