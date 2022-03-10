package course.labs.notificationslab

// Credits to DEX7RA from https://stackoverflow.com/questions/68136577/how-to-replace-asynctask-in-this-android-code

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoroutineAsyncTask<Params,Progress,Result> {


    open fun onPreExecute(){ }

    abstract fun doInBackground(vararg params: Params?): Result

    open fun onPostExecute(result: Result?){}


    protected var isCancelled= false
    //Code


    fun execute(vararg params: Params?){
        GlobalScope.launch(Dispatchers.Default) {
            val result = doInBackground(*params)
            withContext(Dispatchers.Main){
                onPostExecute(result)
            }
        }
    }

}