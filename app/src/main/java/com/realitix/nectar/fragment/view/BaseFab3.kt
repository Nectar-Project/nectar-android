package com.realitix.nectar.fragment.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.realitix.nectar.R
import com.realitix.nectar.util.FabAnimation
import kotlinx.android.synthetic.main.view_fab3.view.*


abstract class BaseFab3: LinearLayout {
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    abstract fun postInit()

    private var isFabRotated = false

    private fun init() {
        this.orientation = VERTICAL
        inflate(context, R.layout.view_fab3,this)
        postInit()

        fabMain.setOnClickListener {
            switchFab()
        }
    }

    private fun switchFab() {
        isFabRotated = FabAnimation.rotate(fabMain, !isFabRotated)
        if (isFabRotated) {
            FabAnimation.show(containerFabFirst)
            FabAnimation.show(containerFabSecond)
            FabAnimation.show(containerFabThird)
        } else {
            FabAnimation.hide(containerFabFirst)
            FabAnimation.hide(containerFabSecond)
            FabAnimation.hide(containerFabThird)
        }
    }

    fun setCallbackFirst(callback: () -> Unit) {
        fabFirst.setOnClickListener {
            callback()
            switchFab()
        }
    }

    fun setCallbackSecond(callback: () -> Unit) {
        fabSecond.setOnClickListener {
            callback()
            switchFab()
        }
    }

    fun setCallbackThird(callback: () -> Unit) {
        fabThird.setOnClickListener {
            callback()
            switchFab()
        }
    }
}