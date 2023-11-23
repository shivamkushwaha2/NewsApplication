package com.example.myapplication
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.newsapp.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PageFragment : Fragment() , NewsItemclicked {
    lateinit var mAdapter: NewsListAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view:View = inflater.inflate(R.layout.fragment_page, container, false)
        val a: RecyclerView = view.findViewById(R.id.recyclerView)
        val btn = view.findViewById<LottieAnimationView>(R.id.loadbutton)
        layoutManager = LinearLayoutManager(context)
        a.layoutManager = layoutManager

        val mainActivity = activity as? MainActivity
        mainActivity?.findViewById<ImageView>(R.id.sort_icon)?.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.menu_sort, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.sort_old_to_new -> {
                        mAdapter.sortArticlesByTime(true) // Sort old to new by time
                        true
                    }
                    R.id.sort_new_to_old -> {
                        mAdapter.sortArticlesByTime(false) // Sort new to old by time
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        GlobalScope.launch(Dispatchers.IO) {
            fetchdata(btn)
        }
        mAdapter = NewsListAdapter(this)
        a.adapter = mAdapter

        return view
    }
    private suspend fun fetchdata(btn: LottieAnimationView) {

        val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuffer()

            withContext(Dispatchers.IO) {
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                reader.close()
                val jsonResponse = response.toString()

                val jsonObject = JSONObject(jsonResponse)
                val articlesArray: JSONArray = jsonObject.getJSONArray("articles")
                val newsarray = ArrayList<News>()
                for (i in 0 until articlesArray.length()) {
                    val newsJsonObject = articlesArray.getJSONObject(i)
                    val sourceObj = newsJsonObject.getJSONObject("source")
                    val source = sourceObj.getString("name")

                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        source,
                        newsJsonObject.getString("publishedAt")
                    )
                    newsarray.add(news)
                }
                mAdapter.updatenews(newsarray)
                btn.visibility = View.GONE
            }
        } else {
            println("Failed to fetch data. Response code: $responseCode")
        }
        connection.disconnect()
    }
    override fun onitemclicked(item: News) {
        val url = item.url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }
}