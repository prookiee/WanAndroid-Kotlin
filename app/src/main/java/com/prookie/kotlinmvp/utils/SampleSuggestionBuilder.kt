package com.prookie.kotlinmvp.utils

import com.prookie.kotlinmvp.bean.SearchHistory
import org.cryse.widget.persistentsearch.SearchItem
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder
import org.litepal.LitePal


/**
 * SampleSuggestionBuilder
 * Created by brin on 2018-08-15.
 */
class SampleSuggestionBuilder : SearchSuggestionsBuilder {

    private var searchList: MutableList<SearchHistory> = mutableListOf()

    init {
        createHistory()
    }


    private fun createHistory() {
        searchList = LitePal.findAll(SearchHistory::class.java)
        for (it in searchList) {
            LogUtil.d("litepal", it.toString())
        }
    }

    override fun buildSearchSuggestion(maxCount: Int, query: String?): MutableCollection<SearchItem> {
        val items = mutableListOf<SearchItem>()
        for (it in searchList) {
            LogUtil.d("litepal1", it.toString())
            if (!query.isNullOrBlank()) {
                if (it.value.startsWith(query!!)) {
                    val item = SearchItem(it.title, query, SearchItem.TYPE_SEARCH_ITEM_SUGGESTION)
                    items.add(item)
                }
            }
        }
        return items
    }

    override fun buildEmptySearchSuggestion(maxCount: Int): MutableCollection<SearchItem> {
        val items = mutableListOf<SearchItem>()
        for (it in searchList) {
            LogUtil.d("litepal2", it.toString())
            items.add(SearchItem(it.title, it.value, SearchItem.TYPE_SEARCH_ITEM_HISTORY))
        }
        for (it in items) {
            LogUtil.d("litepal3", it.toString())
        }
        return items
    }


}