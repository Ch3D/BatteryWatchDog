package com.ch3d.ifttt.batterywatchdog.utils

import android.content.Context
import com.ch3d.ifttt.batterywatchdog.PrefrencesProvider

fun Context.isCustomNameEnabled() =
        PrefrencesProvider.isCustomNameEnabled(this)

fun Context.setCustomNameEnabled(enabled: Boolean) =
        PrefrencesProvider.setCustomNameEnabled(this, enabled)

fun Context.getCustomDeviceName() =
        PrefrencesProvider.getCustomDeviceName(this)

fun Context.saveCustomDeviceName(name: String) =
        PrefrencesProvider.saveCustomDeviceName(this, name)

fun Context.isCustomEventEnabled() =
        PrefrencesProvider.isCustomEventEnabled(this)

fun Context.setCustomEventEnabled(enabled: Boolean) =
        PrefrencesProvider.setCustomEventEnabled(this, enabled)

fun Context.getCustomEventName() =
        PrefrencesProvider.getCustomEventName(this)

fun Context.saveCustomEventName(name: String) =
        PrefrencesProvider.saveCustomEventName(this, name)

fun Context.getIftttKey() = PrefrencesProvider.getIftttKey(this)
fun Context.saveIftttKey(key: String) = PrefrencesProvider.saveIftttKey(this, key)

fun Context.isReportingEnabled() = PrefrencesProvider.isReportinEnabled(this)
fun Context.setReportinEnabled(enabled: Boolean) = PrefrencesProvider.setReportinEnabled(this, enabled)