package com.awesome.kotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goTo(v: View) {
        GlobalScope.launch(Dispatchers.IO) {
            val doc: Document = Jsoup.connect("https://github.com/trending").get()
            val elements: Elements = doc.getElementsByTag("article")
            println("----------------------------Fount ${elements.size} repositories--------------------------------")
            elements.forEach {
                // Repo
                println("Repo: ${it.getElementsByTag("a")[1].attr("href").substring(1)}")
                // Description
                println("Description: ${it.getElementsByTag("p")[0].text()}")
                // Language
                val lang: Elements = it.getElementsByClass("repo-language-color")
                if (lang.isNotEmpty()) {
                    println("Language: ${lang[0].attr("style").substringAfter(" ") + "-" + lang[0].siblingElements()[0].text()}")
                }
                // Start & Fork
                val startFork: Elements = it.getElementsByClass("muted-link d-inline-block mr-3")
                println("Start: ${startFork[0].text()}")
                println("Fork: ${startFork[1].text()}")
                // Trending
                println(it.getElementsByClass("d-inline-block float-sm-right")[0].text())
                println("------------------------------------------------------------")
            }
        }
    }
}