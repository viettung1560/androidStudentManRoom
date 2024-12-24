package vn.edu.hust.studentmanroom.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("select * from students")
    fun getAllStudents(): List<Student>

    @Query("select * from students where mssv=:mssv")
    fun findStudentById(mssv: String): List<Student>

    @Query("select * from students where hoten like :name")
    fun findStudentByName(name: String): List<Student>

    @Query("select * from students where _id=:id")
    fun findStudentByPK(id: Int): List<Student>

    @Insert
    fun insertStudent(student: Student): Long

    @Delete
    fun deleteStudent(student: Student)

    @Query("delete from students where _id=:id")
    fun deleteStudentById(id: Int)

    @Query("update students set hoten=:hoten, mssv=:mssv, birthday=:birthday, email=:email where _id=:id")
    fun updateStudent(hoten: String, mssv: String, birthday: String, email: String, id: Int)
}