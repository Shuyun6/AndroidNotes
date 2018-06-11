package com.shuyun.androidnotes.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.data.bean.Person
import java.util.function.Predicate

/**
 * Functional Programming Note
 * @author shuyun
 * @created on 2018/6/10 0010 21:33
 * @changed on 2018/6/10 0010 21:33
 */
class FunctionalActivity: AppCompatActivity() {

    private val listOfPerson = ArrayList<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_functinal)
        getPersonList().stream().filter{ it.sex == 1 }.map {Person(it.name+"A", it.sex)}.forEach { Log.e("test", "result: $it") }
    }

    /**
     * create data for testing
     */
    private fun getPersonList(): ArrayList<Person> {
        (0..15).map { listOfPerson.add(Person(it.toString(), it%2)) }
        return listOfPerson
    }

    /**
     * find person by name
     */
    fun findPersonByName(name: String): ArrayList<Person> {
        return find(Predicate { it.name == name })
    }

    fun findPersonBySex(sex: Int): ArrayList<Person> {
        return find(Predicate { it.sex == sex })
    }

    fun finPersonByNameAndSex1(name: String, sex: Int): ArrayList<Person> {
        return find(Predicate { it.name == name && it.sex == sex })
    }

    /**
     * Use functional Predicate method and to filter two filters
     */
    fun finPersonByNameAndSex2(name: String, sex: Int): ArrayList<Person> {
        val list = ArrayList<Person>()
        val predicate = Predicate<Person> { it.name == name }.and{ it.sex == sex }
        listOfPerson.map { if(predicate.test(it)) list.add(it) }
        return list
    }

    /**
     * Use Predicate to filter person list
     */
    fun find(predicate: Predicate<Person>): ArrayList<Person> {
        val list = ArrayList<Person>()
        listOfPerson.map { if(predicate.test(it)) list.add(it) }
        return list
    }

}