package com.example.carlosyang.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import org.jetbrains.anko.*

class MainActivity : Activity() {
    private var rl_main: RelativeLayout? = null
    private var btn_reset: Button? = null
    private var customView: CustomView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            backgroundResource = android.R.color.white
            rl_main=this
            customView=CustomView(this@MainActivity).apply {
                addView(this)
            }
            button("重置") {
                btn_reset=this
            }.lparams(width = wrapContent, height = wrapContent) {
                alignParentBottom()
                centerHorizontally()
                bottomMargin = dip(20)
            }.setOnClickListener {
                customView?.resetView()
            }
        }
    }
}
