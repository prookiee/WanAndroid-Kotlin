package com.prookie.kotlinmvp.bean

import org.cryse.widget.persistentsearch.SearchItem
import org.litepal.crud.LitePalSupport

/**
 * SearchHistory
 * Created by brin on 2018-08-15.
 */
class SearchHistory(val title: String, val value: String, val icon: Int = SearchItem.TYPE_SEARCH_ITEM_HISTORY) : LitePalSupport() {

    val id: Long = 0

    override fun toString(): String {
        return "SearchHistory(title='$title', value='$value', icon=$icon, id=$id)"
    }


}