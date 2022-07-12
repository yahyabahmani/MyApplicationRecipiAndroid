package com.example.myapplicationrecipi.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource
}