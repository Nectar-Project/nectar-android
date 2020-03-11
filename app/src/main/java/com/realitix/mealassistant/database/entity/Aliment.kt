package com.realitix.mealassistant.database.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

class Aliment(name: String, nameSearch: String, categoryId: Long, nutrition: AlimentNutrition): AlimentRaw(name, nameSearch, categoryId, nutrition)

@Entity(
    foreignKeys = [(ForeignKey(
        entity = AlimentCategoryRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId"),
        onDelete = ForeignKey.CASCADE
    ))],
    indices = [
        Index(value=["name"], unique=true),
        Index(value=["categoryId"])
    ]
)
open class AlimentRaw (
    var name: String,
    var nameSearch: String,
    var categoryId: Long,
    nutrition: AlimentNutrition
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @Embedded
    var nutrition: AlimentNutrition = nutrition
}

// All nutrients are for 100g
class AlimentNutrition(
    var energy: Float, // kcal
    var alcohol: Float, // g
    var protein: Int, var glucid: Int, var lipid: Int)



"Alcohol",
"Ash",
"Beta-Hydroxybutyrate",
"Caffeine",
"Water",
"Carbs",
"Fiber",
"Starch",
"Sugars",
"Fructose",
"Galactose",
"Glucose",
"Lactose",
"Maltose",
"Sucrose",
"Added Sugars",
"Sugar Alcohol",
"Fat",
"Monounsaturated",
"Polyunsaturated",
"Omega-3",
"Omega-6",
"Saturated",
"Trans-Fats",
"Cholesterol",
"Phytosterol",
"Protein",
"Alanine",
"Arginine",
"Aspartic acid",
"Cystine",
"Glutamic acid",
"Glycine",
"Histidine",
"Hydroxyproline",
"Isoleucine",
"Leucine",
"Lysine",
"Methionine",
"Phenylalanine",
"Proline",
"Serine",
"Threonine",
"Tryptophan",
"Tyrosine",
"Valine",
"B1 (Thiamine)",
"B2 (Riboflavin)",
"B3 (Niacin)",
"B5 (Pantothenic Acid)",
"B6 (Pyridoxine)",
"B12 (Cobalamin)",
"Biotin",
"Choline",
"Folate",
"Vitamin A",
"Alpha-carotene",
"Beta-carotene",
"Beta-cryptoxanthin",
"Lutein+Zeaxanthin",
"Lycopene",
"Retinol",
"Retinol Activity Equivalent",
"Vitamin C",
"Vitamin D",
"Vitamin E",
"Beta Tocopherol",
"Delta Tocopherol",
"Gamma Tocopherol",
"Vitamin K",
"Calcium",
"Chromium",
"Copper",
"Fluoride",
"Iodine",
"Iron",
"Magnesium",
"Manganese",
"Molybdenum",
"Phosphorus",
"Potassium",
"Selenium",
"Sodium",
"Zinc"