package com.ch3d.ifttt.batterywatchdog.model

data class ReportData(val key: String,
                      val event: String,
                      val deviceName: String,
                      val batteryPercentage: String) {
    companion object {
        val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"
    }
}
