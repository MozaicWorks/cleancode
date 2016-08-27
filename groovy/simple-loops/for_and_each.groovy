#!/usr/bin/env groovy

def typicalLoop(){

    def aCollection = ["a", "b", "c", "d", "e"]
    for(item in aCollection) doSomethingWithItem(item)

}

def loopUsingEach(){
    def aCollection = ["a", "b", "c", "d", "e"]
    aCollection.each{doSomethingWithItem(it)}
}

def doSomethingWithItem(item){
    println item
} 


println "Typical loop"
typicalLoop()

println "Each() function"
typicalLoop()

