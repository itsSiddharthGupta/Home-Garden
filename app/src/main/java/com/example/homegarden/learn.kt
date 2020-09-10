package com.example.homegarden

import java.util.*
import kotlin.random.Random

fun main() {
    for (i in 1..10) {
//        println("Your fortune is : ${getFortune()}")
        usingLambdas(i);
    }
}

fun usingLambdas(i: Int){
    val rollDice = {sides: Int -> if(sides==0) 0 else Random.nextInt(sides) + 1}
    val rollDice2: (Int) -> Int = {sides -> if(sides==0) 0 else Random.nextInt(sides) + 1}
    fun gamePlay(sides: Int = 12, dice: Int = rollDice2(sides)){
        print(dice)
    }
    gamePlay(i)
}

fun isValid(s: String): Boolean {
    val stack: Stack<Char> = Stack()
    for(i in 0..s.length){
        when(val cc:Char = s[i]){
            '(', '[', '{' -> stack.push(cc)
            ')' -> if(stack.isEmpty() || stack.peek()!='(') return false
            '}' -> if(stack.isEmpty() || stack.peek()!='{') return false
            ']' -> if(stack.isEmpty() || stack.peek()!='[') return false
        }
    }
    return stack.isEmpty()
}

fun usingFilter() {
    val spices =
        listOf("curry", "pepper", "cayenne", "ginger", "red curry", "green curry", "red pepper")
    spices.filter { it.contains("curry") }.sortedBy { it.length }

//    Filtering by those that start with 'c' and end with 'e'

    spices.filter { it.startsWith('c') }.filter { it.endsWith('e') }
    spices.filter { it.startsWith('c') && it.endsWith('e') }

//    Filtering the first 3 items by 'c'
    spices.take(3).filter { it.startsWith('c') }

}

fun whatShouldIDoToday(mood: String, weather: String = "sunny", temperature: Int = 24): String {
    return when {
        mood == "happy" && weather == "Sunny" -> "go for a walk"
        else -> "Stay home and read."
    }
}

fun getBirthday(): Int {
    print("Enter your birth date : ")
    return readLine()?.toIntOrNull() ?: 1
}

fun getFortune(): String? {
    val fortunes: List<String> = listOf(
        "You will have a great day!",
        "Things will go well for you today.",
        "Enjoy a wonderful day of success.",
        "Be humble and all will turn out well.",
        "Today is a good day for exercising restraint.",
        "Take it easy and enjoy life!",
        "Treasure your friends because they are your greatest fortune."
    )


    val birthDate = getBirthday();
    return when {
        birthDate == 28 || birthDate == 31 -> "Nice Day"
        birthDate < 8 -> "First Come First Serve"
        else -> fortunes[birthDate % 7]
    }
}