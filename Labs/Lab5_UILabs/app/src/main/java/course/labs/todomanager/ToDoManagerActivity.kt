package course.labs.todomanager

import android.app.Activity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.text.ParseException
import java.util.Date

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import course.labs.todomanager.ToDoItem.Priority
import course.labs.todomanager.ToDoItem.Status

class ToDoManagerActivity : Activity() {

    private lateinit var mAdapter: ToDoListAdapter
    private lateinit var mRecyclerView: RecyclerView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        Log.i(TAG, "Entered onCreate()")

        mRecyclerView = findViewById(R.id.list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        // Create a new TodoListAdapter for this Activity's RecyclerView
        mAdapter = ToDoListAdapter(this)

        // Load saved ToDoItems
        loadItemsFromFile()

        // Attach the adapter to this Activity's RecyclerView
        mRecyclerView.adapter = mAdapter


    }

    // Waiting the result from the AddToDoActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i(TAG, "Entered onActivityResult()")

        // TODO - Check result code and request code
        // if user submitted a new ToDoItem
        if (requestCode == ADD_TODO_ITEM_REQUEST &&
                resultCode == RESULT_ITEM_CREATED) {

            // TODO: ToDoItemCreated Here
            // Create a new ToDoItem from the data Intent
            var newToDoItem = ToDoItem(data!!)

            // and then add it to the adapter
            mAdapter.add(newToDoItem)

        }

    }

    // Do not modify below here

    public override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        // Save ToDoItems to file
        saveItemsToFile()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MENU_DELETE -> {
                mAdapter.clear()
                true
            }
            MENU_DUMP -> {
                dump()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun dump() {
        for (i in 1 until mAdapter.itemCount) {
            val data = (mAdapter.getItem(i) as ToDoItem).toLog()
            Log.i(TAG,
                    "Item " + i + ": " + data.replace(ToDoItem.ITEM_SEP!!, ","))
        }
    }

    // Load stored ToDoItems
    private fun loadItemsFromFile() {
        var reader: BufferedReader? = null
        try {
            val fis = openFileInput(FILE_NAME)
            reader = BufferedReader(InputStreamReader(fis))

            var title: String?
            var priority: String?
            var status: String?
            var date: Date?

            do {
                title = reader.readLine()
                if (title == null)
                    break
                priority = reader.readLine()
                status = reader.readLine()
                date = ToDoItem.FORMAT.parse(reader.readLine())
                mAdapter.add(ToDoItem(title, Priority.valueOf(priority),
                        Status.valueOf(status), date))

            }
            while (true)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    // Save ToDoItems to file
    private fun saveItemsToFile() {
        var writer: PrintWriter? = null
        try {
            val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            writer = PrintWriter(BufferedWriter(OutputStreamWriter(
                    fos)))

            for (idx in 1 until mAdapter.itemCount) {

                writer.println(mAdapter.getItem(idx))

            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            writer?.close()
        }
    }

    companion object {

        val RESULT_ITEM_CREATED: Int = 1
        const val RESULT_TODO_CANCELED: Int = 2
        const val ADD_TODO_ITEM_REQUEST = 0
        private const val FILE_NAME = "TodoManagerActivityData.txt"
        const val TAG = "Lab-UserInterface"

        // IDs for menu items
        private const val MENU_DELETE = Menu.FIRST
        private const val MENU_DUMP = Menu.FIRST + 1
    }
}