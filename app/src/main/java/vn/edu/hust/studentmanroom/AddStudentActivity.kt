package vn.edu.hust.studentmanroom

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val editHoten = findViewById<EditText>(R.id.edit_hoten)
        val editMssv = findViewById<EditText>(R.id.edit_mssv)
        val editBirthday = findViewById<EditText>(R.id.edit_birthday)
        val editEmail = findViewById<EditText>(R.id.edit_email)

        val buttonOK = findViewById<Button>(R.id.button_ok)
        val buttonCancel = findViewById<Button>(R.id.button_cancel)
        val buttonDelete = findViewById<Button>(R.id.button_delete)

        if (intent.getStringExtra("hoten") != null) {
            editHoten.setText(intent.getStringExtra("hoten")!!)
            editMssv.setText(intent.getStringExtra("mssv")!!)
            editBirthday.setText(intent.getStringExtra("birthday")!!)
            editEmail.setText(intent.getStringExtra("email")!!)
            buttonDelete.visibility = View.VISIBLE
            buttonOK.setText("Update student")
        }
        else {
            buttonDelete.visibility = View.GONE
            buttonOK.setText("Add student")
        }
        val db_id = intent.getIntExtra("db_id", -1)

        // TODO: Su dung setResult de thiet lap ket qua tra ve

        setResult(Activity.RESULT_CANCELED)

        editBirthday.setOnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                val year = c.get(Calendar.YEAR) - 20
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        editBirthday.setText(DateFormat(dayOfMonth, monthOfYear + 1, year))
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }

        buttonOK.setOnClickListener {
            if (editHoten.text.isNotBlank() && editMssv.text.isNotBlank() && editBirthday.text.isNotBlank() && editEmail.text.isNotBlank()) {
                intent.putExtra("hoten", editHoten.text.toString())
                intent.putExtra("mssv", editMssv.text.toString())
                intent.putExtra("birthday", editBirthday.text.toString())
                intent.putExtra("email", editEmail.text.toString())
                intent.putExtra("db_id", db_id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        buttonCancel.setOnClickListener {
            intent.putExtra("delete", false)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        buttonDelete.setOnClickListener {
            intent.putExtra("delete", true)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
    fun DateFormat(date :Int, month :Int, year :Int) :String{
        val dateStr = if (date < 10)
            "0$date"
        else
            date.toString()
        val monthStr = if (month < 10)
            "0$month"
        else
            month.toString()
        return ("$dateStr/$monthStr/$year")
    }
}