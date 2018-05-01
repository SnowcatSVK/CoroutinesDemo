package com.globallogic.coroutinesdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.GONE
        btn_second.setOnClickListener { _ ->
            progressBar.visibility = View.VISIBLE
            launch(UI) {
                val time = measureTimeMillis {
                    val one = doSomethingUsefulOne()
                    val two = doSomethingUsefulTwo()
                    progressBar.visibility = View.GONE
                    tv_result_second.text = "The answer is ${one + two} \n"
                }
                val result = tv_result_second.text.toString() + "Completed in $time ms"
                tv_result_second.text = result
            }
        }

        btn_third.setOnClickListener { _ ->
            progressBar.visibility = View.VISIBLE
            launch(UI) {
                val time = measureTimeMillis {
                    val one = async { doSomethingUsefulOne() }
                    val two = async { doSomethingUsefulTwo() }
                    tv_result_third.text = "The answer is ${one.await() + two.await()} \n"
                    progressBar.visibility = View.GONE
                }
                val result = tv_result_third.text.toString() + "Completed in $time ms"
                tv_result_third.text = result
            }
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L)
        return 29
    }
}
