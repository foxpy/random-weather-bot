import java.util.Date


class Time {
    var unixTime = Date().time / 1000
    var day = unixTime / (60*60*24) - 1

    fun isNextDay(): Boolean {
        unixTime = Date().time / 1000
        val reply = unixTime / (60*60*24) > day
        day = unixTime / (60*60*24)
        return reply
    }
}
