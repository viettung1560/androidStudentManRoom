package vn.edu.hust.studentmanroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val hoten: String,
    val mssv: String,
    val birthday: String,
    val email: String
)
