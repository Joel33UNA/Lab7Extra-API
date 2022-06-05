package com.example.background

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.background.Constant.Status
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    var task: MyAsyncTask? = null
    lateinit var textViewId : TextView
    lateinit var textViewName : TextView
    lateinit var textViewUsername : TextView
    lateinit var textViewPhone : TextView
    lateinit var textViewEmail : TextView
    lateinit var textViewWebsite : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewId = findViewById(R.id.textViewId)
        textViewName = findViewById(R.id.textViewName)
        textViewUsername = findViewById(R.id.textViewUsername)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewWebsite = findViewById(R.id.textViewWebsite)
    }

    fun startService(view: View) {
        if (task?.status == Status.RUNNING){
            task?.cancel(true)
        }
        task = MyAsyncTask(this)
        task?.execute(10)
    }

    class MyAsyncTask(private var activity: MainActivity?) : CoroutinesAsyncTask<Int, Int, String>("MysAsyncTask") {
        private val gson = Gson()
        private var apiUrl: String = "https://jsonplaceholder.typicode.com/users/1"

        override fun doInBackground(vararg params: Int?): String {
            var current = ""
            try {
                val url: URL
                var urlConnection: HttpURLConnection? = null
                try {
                    url = URL(apiUrl)
                    urlConnection = url
                        .openConnection() as HttpURLConnection
                    val `in` = urlConnection.inputStream
                    val isw = InputStreamReader(`in`)
                    var data = isw.read()
                    while (data != -1) {
                        current += data.toChar()
                        data = isw.read()
                        print(current)
                    }
                    return current
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return "Exception: " + e.message
            }
            Log.d("Current", current)
            return current
        }

        override fun onPreExecute() {
            activity?.progressBar?.visibility = View.VISIBLE
            activity?.progressBar?.max = 10
            activity?.progressBar?.progress = 0

        }

        override fun onPostExecute(result: String?) {
            val user = this.gson.fromJson(result, User::class.java)
            activity?.progressBar?.visibility = View.GONE
            activity?.textViewId?.text = user.id.toString()
            activity?.textViewName?.text = user.name.toString()
            activity?.textViewUsername?.text = user.username.toString()
            activity?.textViewEmail?.text = user.email.toString()
            activity?.textViewPhone?.text = user.phone.toString()
            activity?.textViewWebsite?.text = user.website.toString()
        }

        override fun onProgressUpdate(vararg values: Int?) {

        }
    }
}
