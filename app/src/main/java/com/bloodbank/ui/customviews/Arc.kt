package com.bloodbank.ui.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView

class Arc : View{
    private var paint : Paint? = null
    private var layout : ImageView? = null
    private val TAG = Arc::class.java.name

    constructor(context: Context?) : super(context){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    private fun init(){
        Log.d(TAG, "init()")
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.WHITE
        paint!!.style = Paint.Style.FILL
    }

    fun setTargetView(targetLayout : ImageView){
        Log.d(TAG, "setTargetView()")
        layout = targetLayout
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val circleRadius = 0.7 * height
        val cx = width/2
        val cy = ((layout?.height ?: 0) -200) + circleRadius
        Log.d(TAG, "onDraw" + cx + " -- " + cy +" --- " + circleRadius)
        canvas?.drawCircle(cx.toFloat(), cy.toFloat(), circleRadius.toFloat(), paint!!)
    }
}