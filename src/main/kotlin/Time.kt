import java.util.Date


class Time {
    var unixTime = Date().time / 1000
    var day = unixTime / (60*60*24) - 1

    fun update() {
        unixTime = Date().time / 1000
        day = unixTime / (60*60*24)
    }

    fun isNextDay(): Boolean {
        unixTime = Date().time / 1000
        return (unixTime / (60*60*24)) > day
    }
}
