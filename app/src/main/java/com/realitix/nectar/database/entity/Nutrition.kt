package com.realitix.nectar.database.entity

// All nutrients are for 100g
class Nutrition(
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
    var sugarAdded: Float,
    var sugarAlcohol: Float,
    var fat: Float,
    var monounsaturated: Float,
    var omega3: Float,
    var omega6: Float,
    var saturated: Float,
    var transFats: Float,
    var cholesterol: Float,
    var protein: Float,
    var alanine: Float,
    var arginine: Float,
    var asparticAcid: Float,
    var cystine: Float,
    var glutamicAcid: Float,
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
    var alphaCarotene: Float,
    var betaCarotene: Float,
    var betaCryptoxanthin: Float,
    var luteinZeaxanthin: Float,
    var lycopene: Float,
    var retinol: Float,
    var retinolActivityEquivalent: Float,
    var c: Float,
    var d: Float,
    var e: Float,
    var betaTocopherol: Float,
    var deltaTocopherol: Float,
    var gammaTocopherol: Float,
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
    var zinc: Float,
    var pral: Float
) {
    companion object {
        fun generate(n: Float = 0f): Nutrition {
            return Nutrition(n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Nutrition

        if (energy != other.energy) return false
        if (alcohol != other.alcohol) return false
        if (ash != other.ash) return false
        if (caffeine != other.caffeine) return false
        if (water != other.water) return false
        if (carbs != other.carbs) return false
        if (fiber != other.fiber) return false
        if (starch != other.starch) return false
        if (sugars != other.sugars) return false
        if (fructose != other.fructose) return false
        if (galactose != other.galactose) return false
        if (glucose != other.glucose) return false
        if (lactose != other.lactose) return false
        if (maltose != other.maltose) return false
        if (sucrose != other.sucrose) return false
        if (sugarAdded != other.sugarAdded) return false
        if (sugarAlcohol != other.sugarAlcohol) return false
        if (fat != other.fat) return false
        if (monounsaturated != other.monounsaturated) return false
        if (omega3 != other.omega3) return false
        if (omega6 != other.omega6) return false
        if (saturated != other.saturated) return false
        if (transFats != other.transFats) return false
        if (cholesterol != other.cholesterol) return false
        if (protein != other.protein) return false
        if (alanine != other.alanine) return false
        if (arginine != other.arginine) return false
        if (asparticAcid != other.asparticAcid) return false
        if (cystine != other.cystine) return false
        if (glutamicAcid != other.glutamicAcid) return false
        if (glycine != other.glycine) return false
        if (histidine != other.histidine) return false
        if (hydroxyproline != other.hydroxyproline) return false
        if (isoleucine != other.isoleucine) return false
        if (leucine != other.leucine) return false
        if (lysine != other.lysine) return false
        if (methionine != other.methionine) return false
        if (phenylalanine != other.phenylalanine) return false
        if (proline != other.proline) return false
        if (serine != other.serine) return false
        if (threonine != other.threonine) return false
        if (tryptophan != other.tryptophan) return false
        if (tyrosine != other.tyrosine) return false
        if (valine != other.valine) return false
        if (b1 != other.b1) return false
        if (b2 != other.b2) return false
        if (b3 != other.b3) return false
        if (b5 != other.b5) return false
        if (b6 != other.b6) return false
        if (b12 != other.b12) return false
        if (biotin != other.biotin) return false
        if (choline != other.choline) return false
        if (folate != other.folate) return false
        if (a != other.a) return false
        if (alphaCarotene != other.alphaCarotene) return false
        if (betaCarotene != other.betaCarotene) return false
        if (betaCryptoxanthin != other.betaCryptoxanthin) return false
        if (luteinZeaxanthin != other.luteinZeaxanthin) return false
        if (lycopene != other.lycopene) return false
        if (retinol != other.retinol) return false
        if (retinolActivityEquivalent != other.retinolActivityEquivalent) return false
        if (c != other.c) return false
        if (d != other.d) return false
        if (e != other.e) return false
        if (betaTocopherol != other.betaTocopherol) return false
        if (deltaTocopherol != other.deltaTocopherol) return false
        if (gammaTocopherol != other.gammaTocopherol) return false
        if (k != other.k) return false
        if (calcium != other.calcium) return false
        if (chromium != other.chromium) return false
        if (copper != other.copper) return false
        if (fluoride != other.fluoride) return false
        if (iodine != other.iodine) return false
        if (iron != other.iron) return false
        if (magnesium != other.magnesium) return false
        if (manganese != other.manganese) return false
        if (molybdenum != other.molybdenum) return false
        if (phosphorus != other.phosphorus) return false
        if (potassium != other.potassium) return false
        if (selenium != other.selenium) return false
        if (sodium != other.sodium) return false
        if (zinc != other.zinc) return false
        if (pral != other.pral) return false

        return true
    }

    override fun hashCode(): Int {
        var result = energy.hashCode()
        result = 31 * result + alcohol.hashCode()
        result = 31 * result + ash.hashCode()
        result = 31 * result + caffeine.hashCode()
        result = 31 * result + water.hashCode()
        result = 31 * result + carbs.hashCode()
        result = 31 * result + fiber.hashCode()
        result = 31 * result + starch.hashCode()
        result = 31 * result + sugars.hashCode()
        result = 31 * result + fructose.hashCode()
        result = 31 * result + galactose.hashCode()
        result = 31 * result + glucose.hashCode()
        result = 31 * result + lactose.hashCode()
        result = 31 * result + maltose.hashCode()
        result = 31 * result + sucrose.hashCode()
        result = 31 * result + sugarAdded.hashCode()
        result = 31 * result + sugarAlcohol.hashCode()
        result = 31 * result + fat.hashCode()
        result = 31 * result + monounsaturated.hashCode()
        result = 31 * result + omega3.hashCode()
        result = 31 * result + omega6.hashCode()
        result = 31 * result + saturated.hashCode()
        result = 31 * result + transFats.hashCode()
        result = 31 * result + cholesterol.hashCode()
        result = 31 * result + protein.hashCode()
        result = 31 * result + alanine.hashCode()
        result = 31 * result + arginine.hashCode()
        result = 31 * result + asparticAcid.hashCode()
        result = 31 * result + cystine.hashCode()
        result = 31 * result + glutamicAcid.hashCode()
        result = 31 * result + glycine.hashCode()
        result = 31 * result + histidine.hashCode()
        result = 31 * result + hydroxyproline.hashCode()
        result = 31 * result + isoleucine.hashCode()
        result = 31 * result + leucine.hashCode()
        result = 31 * result + lysine.hashCode()
        result = 31 * result + methionine.hashCode()
        result = 31 * result + phenylalanine.hashCode()
        result = 31 * result + proline.hashCode()
        result = 31 * result + serine.hashCode()
        result = 31 * result + threonine.hashCode()
        result = 31 * result + tryptophan.hashCode()
        result = 31 * result + tyrosine.hashCode()
        result = 31 * result + valine.hashCode()
        result = 31 * result + b1.hashCode()
        result = 31 * result + b2.hashCode()
        result = 31 * result + b3.hashCode()
        result = 31 * result + b5.hashCode()
        result = 31 * result + b6.hashCode()
        result = 31 * result + b12.hashCode()
        result = 31 * result + biotin.hashCode()
        result = 31 * result + choline.hashCode()
        result = 31 * result + folate.hashCode()
        result = 31 * result + a.hashCode()
        result = 31 * result + alphaCarotene.hashCode()
        result = 31 * result + betaCarotene.hashCode()
        result = 31 * result + betaCryptoxanthin.hashCode()
        result = 31 * result + luteinZeaxanthin.hashCode()
        result = 31 * result + lycopene.hashCode()
        result = 31 * result + retinol.hashCode()
        result = 31 * result + retinolActivityEquivalent.hashCode()
        result = 31 * result + c.hashCode()
        result = 31 * result + d.hashCode()
        result = 31 * result + e.hashCode()
        result = 31 * result + betaTocopherol.hashCode()
        result = 31 * result + deltaTocopherol.hashCode()
        result = 31 * result + gammaTocopherol.hashCode()
        result = 31 * result + k.hashCode()
        result = 31 * result + calcium.hashCode()
        result = 31 * result + chromium.hashCode()
        result = 31 * result + copper.hashCode()
        result = 31 * result + fluoride.hashCode()
        result = 31 * result + iodine.hashCode()
        result = 31 * result + iron.hashCode()
        result = 31 * result + magnesium.hashCode()
        result = 31 * result + manganese.hashCode()
        result = 31 * result + molybdenum.hashCode()
        result = 31 * result + phosphorus.hashCode()
        result = 31 * result + potassium.hashCode()
        result = 31 * result + selenium.hashCode()
        result = 31 * result + sodium.hashCode()
        result = 31 * result + zinc.hashCode()
        result = 31 * result + pral.hashCode()
        return result
    }
}