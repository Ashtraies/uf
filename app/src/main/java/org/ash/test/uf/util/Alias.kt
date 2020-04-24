package org.ash.test.uf.util

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import org.ash.test.uf.model.User

typealias PagedUsers = LiveData<PagedList<User>>

typealias Users = List<User>

