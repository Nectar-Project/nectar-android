package com.realitix.nectar.fragment.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.realitix.nectar.R
import com.realitix.nectar.databinding.ViewFab3Binding
import com.realitix.nectar.databinding.ViewFab4Binding
import com.realitix.nectar.util.FabAnimation


abstract class BaseFab4: LinearLayout {
    protected lateinit var binding: ViewFab4Binding
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
        binding = ViewFab4Binding.inflate(inflater, this)
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
            FabAnimation.show(binding.containerFabThird)
            FabAnimation.show(binding.containerFabFour)
        } else {
            FabAnimation.hide(binding.containerFabFirst)
            FabAnimation.hide(binding.containerFabSecond)
            FabAnimation.hide(binding.containerFabThird)
            FabAnimation.show(binding.containerFabFour)
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

    fun setCallbackThird(callback: () -> Unit) {
        binding.fabThird.setOnClickListener {
            callback()
            switchFab()
        }
    }

    fun setCallbackFour(callback: () -> Unit) {
        binding.fabFour.setOnClickListener {
            callback()
            switchFab()
        }
    }
}