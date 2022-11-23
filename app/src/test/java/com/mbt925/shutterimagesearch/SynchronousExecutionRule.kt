package com.mbt925.shutterimagesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class SynchronousExecutionRule(
    val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : InstantTaskExecutorRule() {

    private val scope = TestScope(dispatcher)

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    fun runBlockingTest(block: suspend TestScope.() -> Unit) = scope.runTest {
        block()
        advanceUntilIdle() // run the remaining tasks
    }
}
