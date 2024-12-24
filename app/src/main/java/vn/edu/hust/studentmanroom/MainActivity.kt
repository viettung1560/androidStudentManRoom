package vn.edu.hust.studentmanroom

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.edu.hust.studentmanroom.database.Student
import vn.edu.hust.studentmanroom.database.StudentDao
import vn.edu.hust.studentmanroom.database.StudentDatabase
import vn.edu.hust.studentmanroom.databinding.ActivityMainBinding
import vn.edu.hust.studentmanroom.list.StudentAdapter
import vn.edu.hust.studentmanroom.list.StudentModel
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    var students: MutableList<StudentModel> = mutableListOf()

    var addLauncher: ActivityResultLauncher<Intent>? = null
    var editLauncher: ActivityResultLauncher<Intent>? = null

    lateinit var studentDao: StudentDao

    lateinit var studentAdapter: StudentAdapter

    lateinit var listViewStudent: RecyclerView

    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        studentDao = StudentDatabase.getInstance(this).studentDao()

        listViewStudent = binding.recyclerView

        studentAdapter = StudentAdapter(students){ student: StudentModel, position: Int ->
            Log.v("TAG", "Clicked on item  ${student.studentName} at position $position")
            val intent = Intent(this, AddStudentActivity::class.java)
            val dbStudent = studentDao.findStudentByPK(student.db_id).first()
//            val dbStudent = studentDao.findStudentByName(student.studentName).intersect(
//                studentDao.findStudentById(student.studentId).toSet()
//            ).first()
            intent.putExtra("hoten", dbStudent.hoten)
            intent.putExtra("mssv", dbStudent.mssv)
            intent.putExtra("birthday", dbStudent.birthday)
            intent.putExtra("email", dbStudent.email)
            intent.putExtra("pos", position)
            intent.putExtra("db_id", student.db_id)
            editLauncher!!.launch(intent)
        }

        listViewStudent.run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }
        //listViewStudent.adapter = studentAdapter


        lifecycleScope.launch(Dispatchers.Main) {
            if (studentDao.getAllStudents().isEmpty()){
                //lifecycleScope.launch(Dispatchers.IO) {
                studentDao.insertStudent(Student(hoten = "Nguyễn Văn An", mssv = "SV001", birthday = "01/01/2004", email = "an.nv@email.com"))
                studentDao.insertStudent(Student(hoten = "Trần Thị Bảo", mssv = "SV002", birthday = "02/02/2004", email = "bao.tt@email.com"))
                studentDao.insertStudent(Student(hoten = "Lê Hoàng Cường", mssv = "SV003", birthday = "03/03/2004", email = "cuong.lh@email.com"))
                studentDao.insertStudent(Student(hoten = "Phạm Thị Dung", mssv = "SV004", birthday = "04/04/2004", email = "dung.pt@email.com"))
                studentDao.insertStudent(Student(hoten = "Đỗ Minh Đức", mssv = "SV005", birthday = "05/05/2004", email = "duc.dm@email.com"))
                studentDao.insertStudent(Student(hoten = "Vũ Thị Hoa", mssv = "SV006", birthday = "06/06/2004", email = "hoa.vt@email.com"))
                studentDao.insertStudent(Student(hoten = "Hoàng Văn Hải", mssv = "SV007", birthday = "07/07/2004", email = "hai.hv@email.com"))
                studentDao.insertStudent(Student(hoten = "Bùi Thị Hạnh", mssv = "SV008", birthday = "08/08/2004", email = "hanh.bt@email.com"))
                studentDao.insertStudent(Student(hoten = "Đinh Văn Hùng", mssv = "SV009", birthday = "09/09/2004", email = "hung.dv@email.com"))
                studentDao.insertStudent(Student(hoten = "Nguyễn Thị Linh", mssv = "SV010", birthday = "10/10/2004", email = "linh.nt@email.com"))
                studentDao.insertStudent(Student(hoten = "Phạm Văn Long", mssv = "SV011", birthday = "11/11/2004", email = "long.pv@email.com"))
                studentDao.insertStudent(Student(hoten = "Trần Thị Mai", mssv = "SV012", birthday = "12/12/2004", email = "mai.tt@email.com"))
                studentDao.insertStudent(Student(hoten = "Lê Thị Ngọc", mssv = "SV013", birthday = "13/01/2004", email = "ngoc.lt@email.com"))
                studentDao.insertStudent(Student(hoten = "Vũ Văn Nam", mssv = "SV014", birthday = "14/02/2004", email = "nam.vv@email.com"))
                studentDao.insertStudent(Student(hoten = "Hoàng Thị Phương", mssv = "SV015", birthday = "15/03/2004", email = "phuong.ht@email.com"))
                studentDao.insertStudent(Student(hoten = "Đỗ Văn Quân", mssv = "SV016", birthday = "16/04/2004", email = "quan.dv@email.com"))
                studentDao.insertStudent(Student(hoten = "Nguyễn Thị Thu", mssv = "SV017", birthday = "17/05/2004", email = "thu.nt@email.com"))
                studentDao.insertStudent(Student(hoten = "Trần Văn Tài", mssv = "SV018", birthday = "18/06/2004", email = "tai.tv@email.com"))
                studentDao.insertStudent(Student(hoten = "Phạm Thị Tuyết", mssv = "SV019", birthday = "19/07/2004", email = "tuyet.pt@email.com"))
                studentDao.insertStudent(Student(hoten = "Lê Văn Vũ", mssv = "SV020", birthday = "20/08/2004", email = "vu.lv@email.com"))

                Log.v("TAG","insert done")
            }
            Log.v("TAG", "list add")
            for (student in studentDao.getAllStudents()) {
                Log.v("TAG", "$student.hoten $student.mssv")
                students.add((StudentModel(student.hoten, student.mssv, student._id)))
                studentAdapter.notifyItemInserted(studentAdapter.itemCount)
            }
        }

        addLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it: ActivityResult ->
                if (it.resultCode == RESULT_OK) {
                    val hoten = it.data?.getStringExtra("hoten")!!
                    val mssv = it.data?.getStringExtra("mssv")!!
                    val birthday = it.data?.getStringExtra("birthday")!!
                    val email = it.data?.getStringExtra("email")!!

                    lifecycleScope.launch(Dispatchers.Main) {
                        withContext(Dispatchers.IO){
                            val result = studentDao.insertStudent(Student(hoten = hoten, mssv = mssv, birthday = birthday, email = email))
                            Log.v("TAG", "Result: $result")
                            students.add(StudentModel(hoten, mssv, result.toInt()))
                        }
                        studentAdapter.notifyItemInserted(studentAdapter.itemCount)
                    }

                } else {
                    //textResult.text = "CANCELLED"
                }
            }

        editLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it: ActivityResult ->
                if (it.resultCode == RESULT_OK) {
                    val hoten = it.data?.getStringExtra("hoten")!!
                    val mssv = it.data?.getStringExtra("mssv")!!
                    val birthday = it.data?.getStringExtra("birthday")!!
                    val email = it.data?.getStringExtra("email")!!
                    val pos = it.data?.getIntExtra("pos", 0)!!
                    val db_id = it.data?.getIntExtra("db_id", 0)!!
                    students[pos].studentName = hoten
                    students[pos].studentId = mssv
                    studentAdapter.notifyItemChanged(pos)

                    lifecycleScope.launch(Dispatchers.IO) {
                        studentDao.updateStudent(hoten, mssv, birthday, email, db_id)
                    }
                } else {
                    val delete = it.data?.getBooleanExtra("delete", false)!!
                    if (delete)
                    {
                        val pos = it.data?.getIntExtra("pos", 0)!!
                        val db_id = it.data?.getIntExtra("db_id", 0)!!
                        lifecycleScope.launch(Dispatchers.Main) {
                            withContext(Dispatchers.IO) {
                                studentDao.deleteStudentById(db_id)
                            }
                            students.removeAt(pos)
                            studentAdapter.notifyItemRemoved(pos)
                        }
                    }
                    //textResult.text = "CANCELLED"
                }
            }


    }

    var myMenu: Menu? = null

    // TODO: Ham khoi tao option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        myMenu = menu

        val addItem: MenuItem = menu?.findItem(R.id.add_new)!!
        val deleteItem: MenuItem = menu?.findItem(R.id.delete)!!
        val searchItem: MenuItem = menu?.findItem(R.id.search)!!

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                addItem.setVisible(msg.isEmpty())
                deleteItem.setVisible(msg.isEmpty())
                if (msg.isEmpty())
                    studentAdapter.unfilterList(students)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun filter(text: String) {
        val filteredlist: MutableList<StudentModel> = mutableListOf()

        for (item in students) {
            if (item.studentName.lowercase().contains(text.lowercase())) {
                filteredlist.add(item)
            }
        }
        if (!filteredlist.isEmpty()) {
            studentAdapter.filterList(filteredlist)
        }
    }

    // TODO: Ham xu ly su kien nhan vao option menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addLauncher!!.launch(intent)
            }

            R.id.delete -> {
                lifecycleScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO){
                        for (student in students.filter { it.selected }) {
                            Log.v("TAG", "$student.id")
                            studentDao.deleteStudentById(student.db_id)
                        }
                    }
                    for (student in students.filter { it.selected }) {
                        val pos = students.indexOf(student)
                        students.remove(student)
                        studentAdapter.notifyItemRemoved(pos)
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}