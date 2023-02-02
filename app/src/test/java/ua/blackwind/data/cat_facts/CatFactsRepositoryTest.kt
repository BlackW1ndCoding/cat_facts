package ua.blackwind.data.cat_facts

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.*
import org.json.JSONArray
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ua.blackwind.data.db.CatFactsDao
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.RandomCatFactDbModel

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CatFactsRepositoryTest {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val testJSONString =
        "[{\"status\":{\"verified\":null,\"sentCount\":0},\"_id\":\"test_id_0\",\"user\":\"63c15de69dd5b79e64cfb2a7\",\"text\":\"TEST TEXT ONE\",\"type\":\"cat\",\"deleted\":false,\"createdAt\":\"2023-01-21T21:55:36.371Z\",\"updatedAt\":\"2023-01-21T21:55:36.371Z\",\"__v\":0},{\"status\":{\"verified\":false,\"sentCount\":0},\"_id\":\"test_id_1\",\"user\":\"63bbaedb305a83e75c1fc46f\",\"text\":\"TEST TEXT TWO\",\"type\":\"cat\",\"deleted\":false,\"createdAt\":\"2023-01-18T14:34:56.324Z\",\"updatedAt\":\"2023-01-18T14:34:56.324Z\",\"__v\":0},{\"status\":{\"verified\":true,\"sentCount\":0},\"_id\":\"test_id_2\",\"user\":\"61c46a7495ba44272eb975e2\",\"text\":\"TEST TEXT THREE\",\"type\":\"cat\",\"deleted\":false,\"createdAt\":\"2022-01-03T16:29:07.392Z\",\"updatedAt\":\"2022-01-03T16:29:07.392Z\",\"__v\":0},{\"status\":{\"verified\":false,\"sentCount\":0},\"_id\":\"test_id_3\",\"user\":\"6267b33bcdcef2bc79a52184\",\"text\":\"TEST TEXT FOUR\",\"type\":\"cat\",\"deleted\":false,\"createdAt\":\"2022-05-05T20:52:30.203Z\",\"updatedAt\":\"2022-05-05T20:52:30.203Z\",\"__v\":0},{\"status\":{\"verified\":true,\"sentCount\":0},\"_id\":\"test_id_4\",\"user\":\"6263383cf782346dbf665fb1\",\"text\":\"TEST TEXT FIVE\",\"type\":\"cat\",\"deleted\":false,\"createdAt\":\"2022-04-30T01:18:24.161Z\",\"updatedAt\":\"2022-04-30T01:18:24.161Z\",\"__v\":0}]"
    private val testJSONArray = mockk<JSONArray>()
    private val testRandomCatFactsList = listOf(
        RandomCatFactDbModel(null, "TEST TEXT THREE"),
        RandomCatFactDbModel(null, "TEST TEXT FIVE")
    )

    private lateinit var remoteDataSource: CatFactsFactsRemoteDataSource
    private lateinit var db: CatFactsDatabase
    private lateinit var dao: CatFactsDao

    @BeforeEach
    fun prepareTests() {
        remoteDataSource = mockk()
        db = mockk()
        dao = mockk()

        every { db.dao } answers { dao }
    }

    @Test
    fun `fetchNewRandomCatFacts with more than 20 records doesn't call api`() {
        every {
            remoteDataSource.loadNewCatFacts(any(), any(), any())
        } answers { secondArg<(JSONArray) -> Unit>().invoke(testJSONArray) }
        every { dao.getRandomFactsCount() } returns 21 andThen 25 andThen 100
        every {
            remoteDataSource.loadNewCatFacts(
                any(),
                any(),
                any()
            )
        } answers { secondArg<(JSONArray) -> Unit>().invoke(testJSONArray) }
        coEvery { dao.insertRandomCatFactsList(testRandomCatFactsList) } just Runs

        val repository = CatFactsRepository(remoteDataSource, db, moshi)
        repeat(3) { repository.fetchNewRandomCatFacts() }

        verify(atLeast = 1)
        { db.dao }
        verify(exactly = 3)
        { dao.getRandomFactsCount() }
        verify(inverse = true)
        { remoteDataSource.loadNewCatFacts(any(), any(), any()) }
        coVerify(inverse = true) { dao.insertRandomCatFactsList(any()) }
    }

    @Test
    fun `fetchNewRandomCatFacts with less than 20 records calls api and writes To Db`() {
        every { testJSONArray.toString() } answers { testJSONString }
        every {
            remoteDataSource.loadNewCatFacts(
                10,
                any(),
                any()
            )
        } answers { secondArg<(JSONArray) -> Unit>().invoke(testJSONArray) }

        every { dao.getRandomFactsCount() } returns 10
        coEvery { dao.insertRandomCatFactsList(testRandomCatFactsList) } just Runs

        val repository = CatFactsRepository(remoteDataSource, db, moshi)

        repository.fetchNewRandomCatFacts()

        verify(atLeast = 1) { db.dao }
        verify(exactly = 1) { dao.getRandomFactsCount() }
        verify(exactly = 1) { remoteDataSource.loadNewCatFacts(10, any(), any()) }
        coVerify(exactly = 1) { dao.insertRandomCatFactsList(testRandomCatFactsList) }
    }
}