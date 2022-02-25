package course.labs.lab5_lifecycle_aware

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO: define your own view model that would be used to store the data when activity is destroyed during rotation

class CounterViewModel : ViewModel(), LifecycleObserver {

    // storing two values, orientation and counter
    // refer to slides and example (LifecycleAwareTicker)

    companion object {
        private const val TAG = "TickerViewModel"
    }

    private val orientation = MutableLiveData<String>()
    private val counter = MutableLiveData<Int>()

    // Access methods for the activity, best practice to pass non-mutable data
    internal val iOrientation: LiveData<String>
        get() = orientation
    internal val iCounter: LiveData<Int>
        get() = counter

    init {
        orientation.value = "Portrait"
        counter.value = 0
    }

    internal fun bindToActivityLifecycle(lifecycleMainActivity: LifecycleMainActivity) {
        lifecycleMainActivity.lifecycle.addObserver(this)
    }

}