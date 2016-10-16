package com.ch3d.ifttt.batterywatchdog.utils

import android.content.Context
import com.ch3d.ifttt.batterywatchdog.PreferencesProvider

fun Context.isCustomNameEnabled() =
        PreferencesProvider.isCustomNameEnabled(this)

fun Context.setCustomNameEnabled(enabled: Boolean) =
        PreferencesProvider.setCustomNameEnabled(this, enabled)

fun Context.getCustomDeviceName() =
        PreferencesProvider.getCustomDeviceName(this)

fun Context.saveCustomDeviceName(name: String) =
        PreferencesProvider.saveCustomDeviceName(this, name)

fun Context.isCustomEventEnabled() =
        PreferencesProvider.isCustomEventEnabled(this)

fun Context.setCustomEventEnabled(enabled: Boolean) =
        PreferencesProvider.setCustomEventEnabled(this, enabled)

fun Context.getCustomEventName() =
        PreferencesProvider.getCustomEventName(this)

fun Context.saveCustomEventName(name: String) =
        PreferencesProvider.saveCustomEventName(this, name)

fun Context.getIftttKey() = PreferencesProvider.getIftttKey(this)
fun Context.saveIftttKey(key: String) = PreferencesProvider.saveIftttKey(this, key)

fun Context.isReportingEnabled() = PreferencesProvider.isReportinEnabled(this)
fun Context.setReportinEnabled(enabled: Boolean) =
        PreferencesProvider.setReportinEnabled(this, enabled)