package com.realitix.mealassistant.database.entity

import androidx.room.*


class AlimentState(uuid: String, alimentUuid: String, nutrition: AlimentNutrition): AlimentStateRaw(uuid, alimentUuid, nutrition) {
    @Relation(parentColumn = "uuid", entityColumn = "stateUuid", entity = AlimentStateMeasureRaw::class)
    var measures: List<AlimentStateMeasure>? = null
}

@Entity(
    foreignKeys = [(ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ))],
    indices = [
        Index(value=["alimentUuid"])
    ]
)
open class AlimentStateRaw (
    @PrimaryKey
    var uuid: String,
    var alimentUuid: String,
    @Embedded
    var nutrition: AlimentNutrition
)

// All nutrients are for 100g
class AlimentNutrition(
    var energy: Float,
    var alcohol: Float,
    var ash: Float,
    var caffeine: Float,
    var water: Float,
    var carbs: Float,
    var fiber: Float,
    var starch: Float,
    var sugars: Float,
    var fructose: Float,
    var galactose: Float,
    var glucose: Float,
    var lactose: Float,
    var maltose: Float,
    var sucrose: Float,
    var sugar_added: Float,
    var sugar_alcohol: Float,
    var fat: Float,
    var monounsaturated: Float,
    var omega3: Float,
    var omega6: Float,
    var saturated: Float,
    var trans_fats: Float,
    var cholesterol: Float,
    var protein: Float,
    var alanine: Float,
    var arginine: Float,
    var aspartic_acid: Float,
    var cystine: Float,
    var glutamic_acid: Float,
    var glycine: Float,
    var histidine: Float,
    var hydroxyproline: Float,
    var isoleucine: Float,
    var leucine: Float,
    var lysine: Float,
    var methionine: Float,
    var phenylalanine: Float,
    var proline: Float,
    var serine: Float,
    var threonine: Float,
    var tryptophan: Float,
    var tyrosine: Float,
    var valine: Float,
    var b1: Float,
    var b2: Float,
    var b3: Float,
    var b5: Float,
    var b6: Float,
    var b12: Float,
    var biotin: Float,
    var choline: Float,
    var folate: Float,
    var a: Float,
    var alpha_carotene: Float,
    var beta_carotene: Float,
    var beta_cryptoxanthin: Float,
    var lutein_zeaxanthin: Float,
    var lycopene: Float,
    var retinol: Float,
    var retinol_activity_equivalent: Float,
    var c: Float,
    var d: Float,
    var e: Float,
    var beta_tocopherol: Float,
    var delta_tocopherol: Float,
    var gamma_tocopherol: Float,
    var k: Float,
    var calcium: Float,
    var chromium: Float,
    var copper: Float,
    var fluoride: Float,
    var iodine: Float,
    var iron: Float,
    var magnesium: Float,
    var manganese: Float,
    var molybdenum: Float,
    var phosphorus: Float,
    var potassium: Float,
    var selenium: Float,
    var sodium: Float,
    var zinc: Float
)