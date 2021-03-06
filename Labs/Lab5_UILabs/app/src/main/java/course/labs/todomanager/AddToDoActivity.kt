package course.labs.todomanager

import java.util.Calendar
import java.util.Date

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.TimePicker

import androidx.fragment.app.FragmentActivity
import course.labs.todomanager.ToDoItem.Priority
import course.labs.todomanager.ToDoItem.Status

class AddToDoActivity : FragmentActivity() {

    private lateinit var mDate: Date
    private lateinit var mPriorityRadioGroup: RadioGroup
    private lateinit var mStatusRadioGroup: RadioGroup
    private lateinit var mTitleText: EditText
    private lateinit var mDefaultStatusButton: RadioButton
    private lateinit var mDefaultPriorityButton: RadioButton
    private lateinit var dateView: TextView
    private lateinit var timeView: TextView

    private val priority: Priority
        get() {

            return when (mPriorityRadioGroup.checkedRadioButtonId) {
                R.id.lowPriority -> {
                    Priority.LOW
                }
                R.id.highPriority -> {
                    Priority.HIGH
                }
                else -> {
                    Priority.MED
                }
            }
        }

    private val status: Status
        get() {

            return when (mStatusRadioGroup.checkedRadioButtonId) {
                R.id.statusDone -> {
                    Status.DONE
                }
                else -> {
                    Status.NOTDONE
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_todo)

        mTitleText = findViewById<View>(R.id.title) as EditText
        mDefaultStatusButton = findViewById<View>(R.id.statusNotDone) as RadioButton
        mDefaultPriorityButton = findViewById<View>(R.id.medPriority) as RadioButton
        mPriorityRadioGroup = findViewById<View>(R.id.priorityGroup) as RadioGroup
        mStatusRadioGroup = findViewById<View>(R.id.statusGroup) as RadioGroup
        dateView = findViewById<View>(R.id.date) as TextView
        timeView = findViewById<View>(R.id.time) as TextView

        // Set the default date and time

        setDefaultDateTime()

        // OnClickListener for the Date button, calls showDatePickerDialog() to
        // show
        // the Date dialog

        val datePickerButton = findViewById<View>(R.id.date_picker_button) as Button
        datePickerButton.setOnClickListener { showDatePickerDialog() }

        // OnClickListener for the Time button, calls showTimePickerDialog() to
        // show
        // the Time Dialog

        val timePickerButton = findViewById<View>(R.id.time_picker_button) as Button
        timePickerButton.setOnClickListener { showTimePickerDialog() }

        // OnClickListener for the Cancel Button,

        val cancelButton = findViewById<View>(R.id.cancelButton) as Button
        cancelButton.setOnClickListener {
            Log.i(TAG, "Entered cancelButton.OnClickListener.onClick()")

            // Indicate result and finish
            // result is some enum that you check in the resultcode of onActivityResult
            setResult(ToDoManagerActivity.RESULT_TODO_CANCELED)
            finish()
        }

        // TODO - Set up OnClickListener for the Reset Button
        val resetButton = findViewById<View>(R.id.resetButton) as Button
        resetButton.setOnClickListener {
            Log.i(TAG, "Entered resetButton.OnClickListener.onClick()")

            // TODO - Reset data to default values
            // defaults: title: "", Status: Not Done, Priority: Med, 7 days from current date
            mTitleText.setText("")
            mPriorityRadioGroup.check(R.id.medPriority)
            mStatusRadioGroup.check(R.id.statusNotDone)
            setDefaultDateTime()
        }

        // Set up OnClickListener for the Submit Button

        val submitButton = findViewById<View>(R.id.submitButton) as Button
        submitButton.setOnClickListener {
            Log.i(TAG, "Entered submitButton.OnClickListener.onClick()")

            // TODO - gather ToDoItem data

            // Get Priority, return a Priority
            var priorityId = mPriorityRadioGroup.checkedRadioButtonId
            var priorityButtonClicked = findViewById<RadioButton>(priorityId)
            var formPriority = Priority.MED
            if (priorityButtonClicked.text.toString() == "Low") {
                formPriority = Priority.LOW
            } else {
                if (priorityButtonClicked.text.toString() == "High") {
                    formPriority = Priority.HIGH
                }
            }
            Log.i(TAG, formPriority.toString())

            // Get Status, return a Status
            var statusId = mStatusRadioGroup.checkedRadioButtonId
            var statusButtonClicked = findViewById<RadioButton>(statusId)
            var formStatus = Status.NOTDONE
            if (statusButtonClicked.text.toString() == "Done:") {
                formStatus = Status.DONE
            }
//            Log.i(TAG, "Button text: " + statusButtonClicked.text.toString())
//            Log.i(TAG, "Status retrieved: " + formStatus.toString())

            // Title
            var formTitle = mTitleText.text.toString()
            // Date
            var formDateTimeStr = dateView.text.toString() + ' ' + timeView.text.toString()
            Log.i(TAG, formDateTimeStr)

            // Package ToDoItem data into an Intent
          val toDoItemIntent = Intent()
          ToDoItem.packageIntent(toDoItemIntent,
              formTitle, formPriority, formStatus, formDateTimeStr)

            // TODO - return data Intent and finish
            setResult(ToDoManagerActivity.RESULT_ITEM_CREATED, toDoItemIntent)
            finish()
        }
    }

    private fun setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = Date()
        mDate = Date(mDate.time + SEVEN_DAYS)

        val c = Calendar.getInstance()
        c.time = mDate

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))

        dateView.text = dateString

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))

        timeView.text = timeString
    }

    // DialogFragment used to pick a ToDoItem deadline date

    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            setDateString(year, monthOfYear, dayOfMonth)
            val dateView: TextView = requireActivity().findViewById(R.id.date)
            dateView.text = dateString
        }
    }

    // DialogFragment used to pick a ToDoItem deadline time

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a new instance of TimePickerDialog and return
            return TimePickerDialog(activity, this, hour, minute, true)
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            setTimeString(hourOfDay, minute)
            val timeView: TextView = requireActivity().findViewById(R.id.time)
            timeView.text = timeString
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog() {
        val newFragment = TimePickerFragment()
        newFragment.show(supportFragmentManager, "timePicker")
    }

    companion object {

        // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
        private const val SEVEN_DAYS = 604800000

        private const val TAG = "Lab-UserInterface"

        private var timeString: String? = null
        private var dateString: String? = null

        private fun setDateString(year: Int, monthOfYear: Int, dayOfMonth: Int) {
            var localMonthOfYear = monthOfYear

            // Increment monthOfYear for Calendar/Date -> Time Format setting
            localMonthOfYear++
            var mon = "" + localMonthOfYear
            var day = "" + dayOfMonth

            if (localMonthOfYear < 10)
                mon = "0$localMonthOfYear"
            if (dayOfMonth < 10)
                day = "0$dayOfMonth"

            dateString = "$year-$mon-$day"
        }

        private fun setTimeString(hourOfDay: Int, minute: Int) {
            var hour = "" + hourOfDay
            var min = "" + minute

            if (hourOfDay < 10)
                hour = "0$hourOfDay"
            if (minute < 10)
                min = "0$minute"

            timeString = "$hour:$min:00"
        }
    }
}
