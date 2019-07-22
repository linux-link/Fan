package com.link.component_search.app.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.link.component_search.R
import com.link.component_search.app.SearchViewModelFactory
import com.link.librarymodule.base.mvvm.view.BaseMvvmFragment
import com.link.librarymodule.widgets.recyclerview.DividerItemDecoration
import kotlinx.android.synthetic.main.search_fragment_search.*

/**
 * @author WJ
 * @date 2019-07-16
 *
 * 描述：搜索界面
 */
class SearchFragment(override var layoutId: Int = R.layout.search_fragment_search) : BaseMvvmFragment<SearchViewModel>() {

    override fun initViewModel(): SearchViewModel {
        return ViewModelProviders.of(activity!!, SearchViewModelFactory.getInstance()).get(SearchViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                SearchFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    private lateinit var mAdapter: SearchAdapter

    override fun initView() {
        super.initView()

        val list= arrayListOf("Staggered","Staggered","StaggeredGridLayoutManager","StaggeredGridLayoutManager","StaggeredGridLayoutManager")

        mAdapter = SearchAdapter(android.R.layout.simple_list_item_activated_1, list)
        rvList.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rvList.adapter = mAdapter

        initHeaderView()

        btn_search.setOnClickListener {
            val menu: String = et_search.text.toString()
            if (menu.isNotEmpty()) {
                mViewModel.searchWord.value = menu
            }
        }


    }

    private fun initHeaderView() {
        val header = LayoutInflater.from(context).inflate(R.layout.search_head_view, null)
        mAdapter.addHeaderView(header)
    }

    override fun getData() {
        super.getData()

    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.searchWord.observe(this, Observer {
            mViewModel.search(it, 0, 20)
        })

        mViewModel.searchData.observe(this, Observer {
            findNavController().navigate(R.id.search_searchdetailfragment)
        })
    }

}