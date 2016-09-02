package com.ch3d.ifttt.batterywatchdog.utils

import java.util.*

class SingletonListCursor<T>(item: T) : ListCursor<T>(Collections.singletonList(item))
