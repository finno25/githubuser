package com.aura.githubuser.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aura.githubuser.R
import com.aura.githubuser.networks.NetworkAgent
import com.aura.githubuser.networks.UsersResponse
import com.aura.githubuser.views.activities.MainActivity
import com.aura.githubuser.views.adapters.UserAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter
    private var maxPage = 0
    private var currentPage = 0
    private var currentKeyword = ""
    private var isFetchingUser = false
    private val loadingFragment = LoadingFragment()
    private val emptyDataFragment = EmptyDataFragment()
    private val errorFragment = ErrorFragment(object : ErrorFragment.Listener {
        override fun onBtnTryAgainClicked() {
            findUsers(currentKeyword, currentPage)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        init(arguments)
    }

    private fun init(args: Bundle?) {
        val gridLayoutManager = GridLayoutManager(context, 2)
        rvUsers.layoutManager = gridLayoutManager

        userAdapter = UserAdapter(null)
        rvUsers.adapter = userAdapter

        ibtnSearch.setOnClickListener {
            etKeyword.clearFocus()
            it.requestFocus()
            findUsers(etKeyword.text.toString(), 1)
        }

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val countItem = gridLayoutManager.itemCount
                val lastVisiblePosition = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition

                if (isLastPosition && !isFetchingUser && currentPage < maxPage) {
                    findUsers(currentKeyword, currentPage + 1)
                }
            }
        })
    }

    private fun setLoading(isShow: Boolean) {
        if (isShow) {
            (activity as MainActivity).addFragment(loadingFragment)
        } else {
            (activity as MainActivity).removeFragment(loadingFragment)
        }
    }

    private fun setEmptyDataToShow(isShow: Boolean) {
        if (isShow) {
            (activity as MainActivity).addFragment(emptyDataFragment)
        } else {
            (activity as MainActivity).removeFragment(emptyDataFragment)
        }
    }

    private fun setError(isShow: Boolean) {
        if (isShow) {
            (activity as MainActivity).addFragment(errorFragment)
        } else {
            (activity as MainActivity).removeFragment(errorFragment)
        }
    }

    private fun findUsers(keyword: String, page: Int) {
        if (!isFetchingUser) {
            isFetchingUser = true
            if (keyword.length > 0) {
                setLoading(true)
                setEmptyDataToShow(false)
                setError(false)
                currentPage = page
                currentKeyword = keyword
                if (page == 1) {
                    userAdapter.clearUsers()
                }

                NetworkAgent.instance?.getGithubUserFinder(currentKeyword, page, object :
                    Callback<UsersResponse> {
                    override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                        setError(true)
                        setLoading(false)
                        isFetchingUser = false
                    }

                    override fun onResponse(
                        call: Call<UsersResponse>,
                        response: Response<UsersResponse>
                    ) {
                        response.body()?.let {
                            //val usersToShow = it.users.filter { it.login.startsWith(etKeyword.text.toString()) }
                            val usersToShow = it.users
                            if (currentPage == 1) {
                                if (usersToShow.size == 0) {
                                    setEmptyDataToShow(true)
                                }
                            }
                            userAdapter.addUsers(usersToShow)
                            maxPage =
                                Math.ceil(Math.abs(Math.abs(it.totalCount.toDouble() / NetworkAgent.PER_PAGE.toDouble())))
                                    .toInt()
                        }
                        isFetchingUser = false
                        setLoading(false)
                    }
                })
            }
        }
    }

}