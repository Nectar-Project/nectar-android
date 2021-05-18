package com.realitix.nectar.fragment.view

import android.content.Context
import android.util.AttributeSet

class ReceipeMultiFab: BaseFab4 {
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    override fun postInit() {
        binding.textFirst.text = "Ajouter une étape"
        binding.textSecond.text = "Ajouter une mesure"
        binding.textThird.text = "Ajouter un tag"
        binding.textFour.text = "Supprimer cette recette"
    }
}