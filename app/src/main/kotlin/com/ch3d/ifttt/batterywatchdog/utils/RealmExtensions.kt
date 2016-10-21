package com.ch3d.ifttt.batterywatchdog.utils

import io.realm.Realm

/**
 * TODO add javadocs
 */

inline fun Realm.perform(func: Realm.() -> Unit) {
    beginTransaction()
    func()
    commitTransaction()
}