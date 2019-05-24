package com.uharris.hackernews.data.base

sealed class Failure {
    object NetworkConnection: Failure()
    object ServerError: Failure()
}