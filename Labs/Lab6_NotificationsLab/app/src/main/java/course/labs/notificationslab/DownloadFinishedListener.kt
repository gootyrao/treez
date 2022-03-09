package course.labs.notificationslab

internal interface DownloadFinishedListener {
    fun notifyDataRefreshed(feeds: Array<String?>?)
}