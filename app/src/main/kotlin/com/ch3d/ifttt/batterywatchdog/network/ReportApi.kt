package com.ch3d.ifttt.batterywatchdog.network

import com.ch3d.ifttt.batterywatchdog.model.Rule

internal interface ReportApi {
    fun report(data: Rule)
}