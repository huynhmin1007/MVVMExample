package com.example.androidmvvm.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidmvvm.data.model.User
import com.example.androidmvvm.data.repository.UserRepository

/**
 * Dùng để phân trang (ko học)
 */
//class UserPagingSource(
//    private val userRepository: UserRepository,
//    val orderBy: String,
//    val orderDir: String
//): PagingSource<Int, User>() {
//    private val STARTING_PAGE_INDEX = 0
//
//    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.let { page ->
//                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
//            } ?: STARTING_PAGE_INDEX
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
//        return try {
//            val currentPage = params.key ?: STARTING_PAGE_INDEX
//            val response = userRepository.findAll(
//                page = currentPage,
//                limit = params.loadSize,
//                orderBy = orderBy,
//                orderDir = orderDir
//            )
//
//            response.fold(
//                onSuccess = { users ->
//                    LoadResult.Page(
//                        data = users,
//                        prevKey = if(currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
//                        nextKey = if(users.isEmpty()) null else currentPage + 1
//                    )
//                },
//                onFailure = { LoadResult.Error(it) }
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}