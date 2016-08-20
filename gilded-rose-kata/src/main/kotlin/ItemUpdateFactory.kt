internal object ItemUpdateFactory {

    fun createQualityUpdateFor(name: String): (Item) -> Unit = when {
        SULFURAS.equals(name) -> { item -> noOpFor(item) }
        AGED_BRIE.equals(name) -> { item -> increaseAgedBrieQualityByExpirationDays(item) }
        BACKSTAGE_PASS.equals(name) -> { item -> increaseBackstagePassQualityByExpirationDays(item) }
        else -> { item -> decreaseItemQualityByExpirationDays(item) }
    }

    fun createSellInUpdateFor(name: String): (Item) -> Unit = when {
        SULFURAS.equals(name) -> { item -> noOpFor(item) }
        else -> { item -> decreaseSellInByOneIn(item) }
    }

    private val SULFURAS = "Sulfuras, Hand of Ragnaros"
    private val AGED_BRIE = "Aged Brie"
    private val BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert"

}


private fun noOpFor(item: Item) {

}

private fun decreaseSellInByOneIn(item: Item) {
    item.sellIn--
}

private fun decreaseItemQualityByExpirationDays(item: Item) {
    if (item.quality == 0) return

    val decrement = if (item.sellIn > 0) 1 else 2
    item.quality -= decrement
}

private fun increaseAgedBrieQualityByExpirationDays(item: Item) {
    if (item.quality == 50) return

    val increment = if (item.sellIn > 0) 1 else 2
    item.quality += increment
}

private fun increaseBackstagePassQualityByExpirationDays(item: Item) {
    if (item.quality == 50) return

    val increment = when {
        item.sellIn <= 0 -> -item.quality
        item.sellIn in 1..5 -> 3
        item.sellIn in 6..10 -> 2
        else -> 1
    }

    item.quality += increment
}