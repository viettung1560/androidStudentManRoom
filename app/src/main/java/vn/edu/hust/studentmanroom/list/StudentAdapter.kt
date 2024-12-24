package vn.edu.hust.studentmanroom.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.hust.studentmanroom.MainActivity
import vn.edu.hust.studentmanroom.R

class StudentAdapter(var students: MutableList<StudentModel>, val clickListener: (StudentModel, Int) -> Unit): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_hoten)
        val textStudentId: TextView = itemView.findViewById(R.id.text_mssv)
        val checkSelected: CheckBox = itemView.findViewById(R.id.check_selected)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    fun filterList(filterlist: MutableList<StudentModel>) {
        students = filterlist
        notifyDataSetChanged()
    }
    fun unfilterList(list: MutableList<StudentModel>){
        students = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId
        holder.checkSelected.isChecked = student.selected

        holder.checkSelected.setOnClickListener {
            student.selected = holder.checkSelected.isChecked
            notifyItemChanged(position)
        }

        holder.itemView.setOnClickListener { clickListener(student, position) }

    }
}