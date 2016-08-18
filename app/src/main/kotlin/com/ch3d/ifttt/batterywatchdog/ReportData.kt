package com.ch3d.ifttt.batterywatchdog

data class ReportData(val key: String,
                      val event: String,
                      val deviceName: String,
                      val batteryPercentage: String)
