package com.valentine.learnit.ui.search

import android.content.SearchRecentSuggestionsProvider

class SearchSuggestionContentProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.valentine.learnit.ui.search.SearchSuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES or DATABASE_MODE_2LINES
    }
}