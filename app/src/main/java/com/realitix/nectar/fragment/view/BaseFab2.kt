package com.realitix.nectar.fragment.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.realitix.nectar.R
import com.realitix.nectar.databinding.ViewFab2Binding
import com.realitix.nectar.util.FabAnimation


abstract class BaseFab2: LinearLayout {
    protected lateinit var binding: ViewFab2Binding
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
        val inflater = LayoutInflater.from(context)
        binding = ViewFab2Binding.inflate(inflater, this)
        postInit()

        binding.fabMain.setOnClickListener {
            switchFab()
        }
    }

    private fun switchFab() {
        isFabRotated = FabAnimation.rotate(binding.fabMain, !isFabRotated)
        if (isFabRotated) {
            FabAnimation.show(binding.containerFabFirst)
            FabAnimation.show(binding.containerFabSecond)
        } else {
            FabAnimation.hide(binding.containerFabFirst)
            FabAnimation.hide(binding.containerFabSecond)
        }
    }

    fun setCallbackFirst(callback: () -> Unit) {
        binding.fabFirst.setOnClickListener {
            callback()
            switchFab()
        }
    }

    fun setCallbackSecond(callback: () -> Unit) {
        binding.fabSecond.setOnClickListener {
            callback()
            switchFab()
        }
    }
}