package com.example.belajarjson.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.belajarjson.R
import com.example.belajarjson.adapter.RecyclerViewAdapter
import com.example.belajarjson.model.Anime
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val JSON_URL = "https://gist.githubusercontent.com/shidiq12/3e8451833891a95d6389e81310c59e36/raw/a7bb538a076490c498d18c0100264ae2229be9d6/membuatOptionPane.json"
    private var request: JsonArrayRequest? = null
    private var requestQueue: RequestQueue? = null
    private var lstAnime: MutableList<Anime>? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lstAnime = ArrayList()
        recyclerView = findViewById(R.id.recyclerviewid)
        jsonrequest()


    }

    private fun jsonrequest() {

        request = JsonArrayRequest(JSON_URL, Response.Listener { response ->
            var jsonObject: JSONObject? = null

            for (i in 0 until response.length()) {


                try {
                    jsonObject = response.getJSONObject(i)
                    val anime = Anime()
                    anime.name = jsonObject!!.getString("name")
                    anime.description = jsonObject.getString("description")
                    anime.rating = jsonObject.getString("Rating")
                    anime.categorie = jsonObject.getString("categorie")
                    anime.nb_episode = jsonObject.getInt("episode")
                    anime.studio = jsonObject.getString("studio")
                    anime.image_url = jsonObject.getString("img")
                    lstAnime!!.add(anime)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }


            }

            setuprecyclerview(lstAnime)
        }, Response.ErrorListener { })


        requestQueue = Volley.newRequestQueue(this@MainActivity)
        requestQueue!!.add(request!!)


    }

    private fun setuprecyclerview(lstAnime: List<Anime>?) {


        val myadapter = lstAnime?.let { RecyclerViewAdapter(this, it) }
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = myadapter

    }
}
