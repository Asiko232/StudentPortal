package com.example.studentportal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class StudentAdapter(context: Context, students: MutableList<Student>) :
    ArrayAdapter<Student>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Переиспользуем уже созданную строку, если система её даёт
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        val student = getItem(position) ?: return view

        view.findViewById<TextView>(R.id.tvAvatar).text = student.initials
        view.findViewById<TextView>(R.id.tvName).text = student.name
        view.findViewById<TextView>(R.id.tvLabel).setText(gradeLabelRes(student.average))

        val tvAverage = view.findViewById<TextView>(R.id.tvAverage)
        tvAverage.text = formatAverage(student.average)
        tvAverage.setTextColor(ContextCompat.getColor(context, gradeColorRes(student.average)))
        return view
    }
}