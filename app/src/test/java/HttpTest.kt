import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

@OptIn(ExperimentalCoroutinesApi::class)
abstract class HttpTest {
    protected val server = MockWebServer()
    protected val testDispatcher = StandardTestDispatcher()
    protected val okHttpClient = OkHttpClient.Builder().build();

    @BeforeEach
    open fun setUp() {
        server.start(8000)
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    protected fun enqueueMockResponse(fileName: String, statusCode: Int = 200) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.addHeader("Content-Type", "application/json; charset=utf-8")
            mockResponse.setResponseCode(statusCode)
            mockResponse.setBody(source.readString(Charsets.UTF_8))

            server.enqueue(mockResponse)
        }
    }
}