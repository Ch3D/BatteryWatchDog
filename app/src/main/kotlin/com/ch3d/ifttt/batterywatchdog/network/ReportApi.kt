package com.ch3d.ifttt.batterywatchdog.network

import com.ch3d.ifttt.batterywatchdog.model.ReportData

interface ReportApi {
    fun report(data: ReportData)
}