package com.raka.repolistkoin.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raka.repolistkoin.base.BaseViewModel.State.LOADING
import com.raka.repolistkoin.base.BaseViewModel.State.SUCCESS
import com.raka.repolistkoin.data.model.compact.RepoResponseCompact
import com.raka.repolistkoin.databinding.FragmentRepoListBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_repo_list.*

class RepoListFragment : Fragment() {
    private lateinit var dataBinding: FragmentRepoListBinding
    private lateinit var adapter: RepoListAdapter
    private val compositeDisposable = CompositeDisposable()
    private lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentRepoListBinding.inflate(inflater,container,false).apply {
            viewmodel = ViewModelProviders.of(this@RepoListFragment).get(RepoListViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!dataBinding.viewmodel!!.isLoaded.value!!) {
            dataBinding.viewmodel!!.loadRepoDataCA()
        }
        setupAdapter()
        setupObserver()
    }
    private fun setupAdapter() {
        val viewModel = dataBinding.viewmodel
        if (viewModel != null) {
            adapter = RepoListAdapter()
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager
            repo_list_rv.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )
            repo_list_rv.adapter = adapter
        }
    }
    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun showList() {
        repo_list_rv.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pb_repo_list.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pb_repo_list.visibility = View.GONE
    }

    private fun showNoData() {
        tv_repo_list_no_data.visibility = View.VISIBLE
        repo_list_rv.visibility = View.GONE
        showToast("Data Gagal diambil")
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun loadResponse(data: RepoResponseCompact) {
        when (data.state) {
            SUCCESS -> {
                showList()
                adapter.updateList(data.data!!)
                hideLoading()
            }
            LOADING -> showLoading()
            else -> {
                showNoData()
                hideLoading()
            }
        }
    }
    private fun setupObserver() {
        dataBinding.viewmodel!!.toastMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfnotHandled()?.let {
                showToast(it)
            }
        })
        dataBinding.viewmodel!!.repoCompactData.observe(viewLifecycleOwner, Observer {
            loadResponse(it)
        })
        dataBinding.viewmodel!!.disposable.observe(viewLifecycleOwner, Observer {
            compositeDisposable.add(it)
        })
    }
}
