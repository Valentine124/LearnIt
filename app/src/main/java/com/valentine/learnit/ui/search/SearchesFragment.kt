package com.valentine.learnit.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentine.learnit.R
import com.valentine.learnit.dashboard.CourseDashboardActivity
import com.valentine.learnit.databinding.FragmentSearchesBinding
import com.valentine.learnit.internet.Result
import com.valentine.learnit.loadstate.PagedListLoadStateAdapter
import com.valentine.learnit.main.BASE_URL
import com.valentine.learnit.main.COURSE_URL_STRING
import com.valentine.learnit.main.LearnItApplication
import com.valentine.learnit.main.ResultComparator
import com.valentine.learnit.ui.OnItemClickedListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchesFragment : Fragment() {

    private var _binding: FragmentSearchesBinding? = null
    private val binding get() = _binding!!

    private val model: SearchViewModel by viewModels {
        SearchFactory((activity?.application as LearnItApplication).repository)
    }

    private val adapters = SearchListAdapter(ResultComparator)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchesBinding.inflate(inflater, container, false)

        binding.searchToolbar.apply {
            title = "Search"
            addMenuProvider(object : MenuProvider{
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.search_menu, menu)
                    val searchManager: SearchManager? = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
                    val searchView = menu.findItem(R.id.app_bar_search)
                    searchView.expandActionView()

                    //Associate searchable configuration with the searchView
                    (searchView.actionView as SearchView).apply {
                        setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
                        this.queryHint = "Search Courses"
                        this.requestFocus()
                        this.isIconified = true
                        this.isIconified = false
                        this.showSoftKeyboard()
                        this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {

                                SearchRecentSuggestions(
                                    requireContext(),
                                    SearchSuggestionContentProvider.AUTHORITY,
                                    SearchSuggestionContentProvider.MODE).saveRecentQuery(query, null)

                                lifecycleScope.launch {
                                    model.getSearchCourses(query).collectLatest {
                                        adapters.submitData(it)
                                    }
                                }
                                hideSoftKeyboard()
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                return false
                            }

                        })
                    }
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return false
                }
            })
        }
        binding.searchLists.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapters
            this.adapter = adapters.withLoadStateHeaderAndFooter(
                footer = PagedListLoadStateAdapter(),
                header = PagedListLoadStateAdapter()
            )
        }

        adapters.setOnItemClickListener(object : OnItemClickedListener {
            override fun onItemClickedListener(result: Result) {
                val intent = Intent(requireContext(), CourseDashboardActivity::class.java)
                intent.putExtra(COURSE_URL_STRING, BASE_URL + result.url)
                startActivity(intent)
            }
        })

        return binding.root
    }

    private fun SearchView.showSoftKeyboard() {
        this.postDelayed({
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, 0)
        }, 200)
    }

    private fun SearchView.hideSoftKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}