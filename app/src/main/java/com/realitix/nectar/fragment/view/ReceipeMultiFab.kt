package com.realitix.nectar.fragment.view

import android.content.Context
import android.util.AttributeSet
import kotlinx.android.synthetic.main.view_fab3.view.*

class ReceipeMultiFab: BaseFab3 {
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    override fun postInit() {
        textFirst.text = "Ajouter une étape"
        textSecond.text = "Ajouter une mesure"
        textThird.text = "Ajouter un tag"
    }
}