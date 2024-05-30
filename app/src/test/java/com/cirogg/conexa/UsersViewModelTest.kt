package com.cirogg.conexa

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cirogg.conexa.data.model.Address
import com.cirogg.conexa.data.model.Geo
import com.cirogg.conexa.data.model.Users
import com.cirogg.conexa.data.model.UsersDao
import com.cirogg.conexa.data.model.toEntity
import com.cirogg.conexa.data.remote.api.UsersApiService
import com.cirogg.conexa.data.repository.UsersRepository
import com.cirogg.conexa.viewmodel.UsersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class UsersViewModelTest {

    private lateinit var viewModel: UsersViewModel
    private lateinit var usersRepository: UsersRepository
    private lateinit var usersDao: UsersDao
    private lateinit var usersApiService: UsersApiService

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        usersApiService = mockk()
        usersDao = mockk()
        usersRepository = mockk()
        viewModel = UsersViewModel(usersRepository)

        coEvery { usersDao.insertAll(any()) } returns Unit
        coEvery { usersDao.getAllUsers() } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNews calls usersRepository and updates userList`() = runTest {
        val users = listOf(
            Users(
                id = "1",
                firstname = "John",
                lastname = "Doe",
                email = "john.doe@example.com",
                address = Address(
                    street = "123 Main St",
                    suite = "Apt 4",
                    city = "Anytown",
                    zipcode = "12345",
                    geo = Geo(
                        lat = "40.7128",
                        lng = "-74.0060"
                    )
                )
            )
        )

        coEvery { usersApiService.getUsers() } returns users

        coEvery { usersRepository.fetchUsers() } coAnswers {
            usersApiService.getUsers().also {
                usersDao.insertAll(it.map { user -> user.toEntity() })
            }
        }

        viewModel.fetchUsers()

        coVerify { usersRepository.fetchUsers() }

        coVerify { usersApiService.getUsers() }

        coVerify { usersDao.insertAll(any()) }

        val actualNewsList = viewModel.usersList.first()
        TestCase.assertEquals(users, actualNewsList)
    }

    @Test
    fun `getUserById calls usersRepository and updates userDetail`() = runTest {
        val user = Users(
            id = "1",
            firstname = "John",
            lastname = "Doe",
            email = "john.doe@example.com",
            address = Address(
                street = "123 Main St",
                suite = "Apt 4",
                city = "Anytown",
                zipcode = "12345",
                geo = Geo(
                    lat = "40.7128",
                    lng = "-74.0060"
                )
            )
        )

        coEvery { usersRepository.getUserById("1") } returns user

        viewModel.getUserById("1")

        coVerify { usersRepository.getUserById("1") }

        val actualUserDetail = viewModel.userDetail.first()
        assertEquals(user, actualUserDetail)
    }

}